package com.nitinrana.movieticketbooking.service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.nitinrana.movieticketbooking.constant.MtbConstants;
import com.nitinrana.movieticketbooking.constant.MtbConstants.BOOKING_STATUS;
import com.nitinrana.movieticketbooking.dto.BookingRequestDto;
import com.nitinrana.movieticketbooking.dto.BookingResponseDto;
import com.nitinrana.movieticketbooking.exception.MtbException;
import com.nitinrana.movieticketbooking.model.Booking;
import com.nitinrana.movieticketbooking.model.Seat;
import com.nitinrana.movieticketbooking.model.Show;
import com.nitinrana.movieticketbooking.model.User;
import com.nitinrana.movieticketbooking.repository.BookingRepository;
import com.nitinrana.movieticketbooking.repository.SeatRepository;
import com.nitinrana.movieticketbooking.repository.ShowRepository;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private ShowRepository showRepository;

	@Autowired
	private SeatRepository seatRepository;

	@Autowired
	private ShowService showService;

	@Autowired
	private SeatService seatService;

	@Autowired
	private UserService userService;

	@Autowired
	private EmailService emailService;

	/* retrieves all bookings for user */
	public List<BookingResponseDto> getBookings() {

		return bookingRepository.findByUser((User) userService.getUserDetails()).stream()
				.map(this::bookingToBookingResponseDto).collect(Collectors.toList());
	}

	/* retrieves booking if exists and belongs to user */
	public BookingResponseDto getBooking(Integer bookingId) {
		Booking booking = bookingRepository.findByBookingIdAndUser(bookingId, (User) userService.getUserDetails())
				.orElseThrow(() -> new MtbException(String.format(MtbException.BOOKING_NOT_EXIST, bookingId)));

		return this.bookingToBookingResponseDto(booking);
	}

	/* retrieves upcoming confirmed bookings for user */
	public List<BookingResponseDto> getBookingsUpcomingConfirmed() {
		return bookingRepository
				.findUpcomingByShowDateAfterAndStatusAndUser(BOOKING_STATUS.CONFIRMED.toString(),
						(User) userService.getUserDetails())
				.stream().map(this::bookingToBookingResponseDto).collect(Collectors.toList());
	}

	/* books tickets for show */
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public BookingResponseDto addBooking(BookingRequestDto bookingRequestDto) {

		/* check if show exists */
		Show show = showRepository.findByShowIdAndStartTimeAfter(bookingRequestDto.getShowId(), LocalDateTime.now())
				.orElseThrow(() -> new MtbException(
						String.format(MtbException.SHOW_NOT_EXIST, bookingRequestDto.getShowId())));

		/* check if seats exist */
		bookingRequestDto.getSeats().forEach((seatId) -> {
			if (seatId <= 0 || seatId > show.getTheatre().getSeatCount())
				throw new MtbException(String.format(MtbException.SEAT_NOT_EXIST, show.getTheatre().getTheatreId(),
						show.getTheatre().getSeatCount()));
		});

		/* check if seats are not booked */
		List<Integer> alreadyBookedList = seatRepository.findAllSeatNumberBookedByShowIdAndSeatNumbers(
				bookingRequestDto.getShowId(), bookingRequestDto.getSeats());
		if (!alreadyBookedList.isEmpty())
			throw new MtbException(String.format(MtbException.SEATS_ALREADY_BOOKED, alreadyBookedList));

		/* get seats to be booked */
		List<Seat> seats = seatRepository.findByShowAndSeatNumberIn(show, bookingRequestDto.getSeats());

		/* get user */
		User user = (User) userService.getUserDetails();

		/* create new booking */
		Booking booking = new Booking();
		booking.setShow(show);
		booking.setBookingSeatCount(seats.size());
		booking.setBookedSeats(seats);
		booking.setBookingDate(new Date(System.currentTimeMillis()));
		booking.setStatus(BOOKING_STATUS.CONFIRMED.toString());
		booking.setUser(user);

		/* set booking in seats */
		seats.forEach((seat) -> seat.setBooking(booking));

		Booking newBooking = bookingRepository.save(booking);

		BookingResponseDto bookingResponseDto = this.bookingToBookingResponseDto(newBooking);

		/* send email */
		emailService.sendMail(String.format(MtbConstants.BOOKING_SUBJECT, "Added"),
				String.format(MtbConstants.BOOKING_BODY, user.getName(), "Added", bookingResponseDto.getBookingId(),
						bookingResponseDto.getBookingDate(), bookingResponseDto.getBookingSeatCount(),
						bookingRequestDto.getSeats(),
						bookingResponseDto.getShowResponseDto().getStartTime()
								.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
						bookingResponseDto.getShowResponseDto().getMovieDto().getMovieName(),
						bookingResponseDto.getShowResponseDto().getTheatreDto().getTheatreName()));

		return bookingResponseDto;

	}

	/* updates a booking */
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public BookingResponseDto updateBooking(BookingRequestDto bookingRequestDto) {

		/* get user */
		User user = (User) userService.getUserDetails();

		/* get existing booking */
		Booking existingBooking = bookingRepository.findByBookingIdAndUser(bookingRequestDto.getBookingId(), user)
				.orElseThrow(() -> new MtbException(
						String.format(MtbException.BOOKING_NOT_EXIST, bookingRequestDto.getBookingId())));

		/* check if booking is confirmed */
		if (!existingBooking.getStatus().equals(BOOKING_STATUS.CONFIRMED.toString()))
			throw new MtbException(String.format(MtbException.STATUS_NOT_COMFIRMED, bookingRequestDto.getBookingId()));

		/* check if booking's show isn't of past time */
		if (existingBooking.getShow().getStartTime().isBefore(LocalDateTime.now()))
			throw new MtbException(MtbException.BOOKING_SHOW_PAST_TIME);

		Show show = existingBooking.getShow();

		/* check if show exists */
		if (existingBooking.getShow().getShowId() != bookingRequestDto.getShowId())
			throw new MtbException(MtbException.EXISTING_NEW_SHOW);

		/* check if seats exist */
		bookingRequestDto.getSeats().forEach((seatId) -> {
			if (seatId <= 0 || seatId > show.getTheatre().getSeatCount())
				throw new MtbException(String.format(MtbException.SEAT_NOT_EXIST, show.getTheatre().getTheatreId(),
						show.getTheatre().getSeatCount()));
		});

		/* check if seats are not booked */
		List<Integer> alreadyBookedList = seatRepository.findAllSeatNumberBookedByShowIdAndSeatNumbersUpdate(
				bookingRequestDto.getShowId(), bookingRequestDto.getSeats(), existingBooking.getBookedSeats().stream()
						.map((seat) -> seat.getSeatNumber()).collect(Collectors.toList()));
		if (!alreadyBookedList.isEmpty())
			throw new MtbException(String.format(MtbException.SEATS_ALREADY_BOOKED, alreadyBookedList));

		/* remove booking from existing seats */
		existingBooking.getBookedSeats().forEach(seat -> seat.setBooking(null));

		/* get seats to be booked */
		List<Seat> seats = seatRepository.findByShowAndSeatNumberIn(show, bookingRequestDto.getSeats());

		/* create new booking */
		existingBooking.setBookingSeatCount(seats.size());
		existingBooking.setBookedSeats(seats);
		existingBooking.setBookingDate(new Date(System.currentTimeMillis()));

		/* set booking in seats */
		seats.forEach((seat) -> seat.setBooking(existingBooking));

		Booking updatedBooking = bookingRepository.save(existingBooking);

		BookingResponseDto bookingResponseDto = this.bookingToBookingResponseDto(updatedBooking);

		/* send email */
		emailService.sendMail(String.format(MtbConstants.BOOKING_SUBJECT, "Updated"),
				String.format(MtbConstants.BOOKING_BODY, user.getName(), "Updated", bookingResponseDto.getBookingId(),
						bookingResponseDto.getBookingDate(), bookingResponseDto.getBookingSeatCount(),
						bookingRequestDto.getSeats(),
						bookingResponseDto.getShowResponseDto().getStartTime()
								.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
						bookingResponseDto.getShowResponseDto().getMovieDto().getMovieName(),
						bookingResponseDto.getShowResponseDto().getTheatreDto().getTheatreName()));

		return bookingResponseDto;

	}

	/* cancels a booking */
	public void cancelBooking(Integer bookingId) {
		
		/* get user */
		User user = (User) userService.getUserDetails();
		
		/* get existing booking */
		Booking existingBooking = bookingRepository
				.findByBookingIdAndUser(bookingId,user)
				.orElseThrow(() -> new MtbException(String.format(MtbException.BOOKING_NOT_EXIST, bookingId)));

		/* check if booking isn't canceled already */
		if (existingBooking.getStatus().equals(BOOKING_STATUS.CANCELED.toString()))
			throw new MtbException(String.format(MtbException.STATUS_ALREADY_CANCELED, bookingId));

		/* check if booking's show isn't of past time */
		if (existingBooking.getShow().getStartTime().isBefore(LocalDateTime.now()))
			throw new MtbException(MtbException.BOOKING_SHOW_PAST_TIME);

		/* remove booking from seats */
		existingBooking.getBookedSeats().forEach((seat) -> seat.setBooking(null));

		existingBooking.setStatus(BOOKING_STATUS.CANCELED.toString());

		Booking canceledBooking = bookingRepository.save(existingBooking);

		BookingResponseDto bookingResponseDto = this.bookingToBookingResponseDto(canceledBooking);


		/* send email */
		emailService.sendMail(String.format(MtbConstants.BOOKING_SUBJECT, "Deleted"),
				String.format(MtbConstants.BOOKING_BODY, user.getName(), "Deleted", bookingResponseDto.getBookingId(),
						bookingResponseDto.getBookingDate(), bookingResponseDto.getBookingSeatCount(),
						"NA",
						bookingResponseDto.getShowResponseDto().getStartTime()
								.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
						bookingResponseDto.getShowResponseDto().getMovieDto().getMovieName(),
						bookingResponseDto.getShowResponseDto().getTheatreDto().getTheatreName()));
	}

	/* maps Booking to BookingResponseDto */
	private BookingResponseDto bookingToBookingResponseDto(Booking booking) {
		BookingResponseDto bookingResponseDto = new BookingResponseDto();
		bookingResponseDto.setBookingId(booking.getBookingId());
		bookingResponseDto.setShowResponseDto(showService.showToShowResponseDto(booking.getShow()));
		bookingResponseDto.setBookingSeatCount(booking.getBookingSeatCount());
		bookingResponseDto.setBookedSeatsDto(
				booking.getBookedSeats().stream().map(seatService::seatToSeatDto).collect(Collectors.toList()));
		bookingResponseDto.setBookingDate(booking.getBookingDate());
		bookingResponseDto.setStatus(booking.getStatus());

		return bookingResponseDto;
	}

}
