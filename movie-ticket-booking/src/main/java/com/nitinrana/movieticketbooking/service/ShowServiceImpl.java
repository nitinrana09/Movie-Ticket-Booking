package com.nitinrana.movieticketbooking.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.sql.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nitinrana.movieticketbooking.constant.MtbConstants;
import com.nitinrana.movieticketbooking.dto.MovieDto;
import com.nitinrana.movieticketbooking.dto.ShowRequestDto;
import com.nitinrana.movieticketbooking.dto.ShowResponseDto;
import com.nitinrana.movieticketbooking.dto.TheatreDto;
import com.nitinrana.movieticketbooking.exception.MtbException;
import com.nitinrana.movieticketbooking.model.Movie;
import com.nitinrana.movieticketbooking.model.Seat;
import com.nitinrana.movieticketbooking.model.Show;
import com.nitinrana.movieticketbooking.model.Theatre;
import com.nitinrana.movieticketbooking.repository.MovieRepository;
import com.nitinrana.movieticketbooking.repository.SeatRepository;
import com.nitinrana.movieticketbooking.repository.ShowRepository;
import com.nitinrana.movieticketbooking.repository.TheatreRepository;

@Service
public class ShowServiceImpl implements ShowService {

	@Autowired
	private ShowRepository showRepository;

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private TheatreRepository theatreRepository;

	@Autowired
	private SeatRepository seatRepository;

	@Autowired
	private ModelMapper modelMapper;

	/* gives upcoming shows */
	public List<ShowResponseDto> getShows() {
		return showRepository.findByStartTimeAfterOrderByStartTimeAsc(LocalDateTime.now()).stream()
				.map(this::showToShowResponseDto).collect(Collectors.toList());
	}

	/* gives all shows */
	public List<ShowResponseDto> getAllShows() {
		return showRepository.findAll(Sort.by(Sort.Direction.ASC, "startTime")).stream()
				.map(this::showToShowResponseDto).collect(Collectors.toList());
	}

	/* gives upcoming show by show id */
	public ShowResponseDto getShow(Integer showId) {
		Show show = showRepository.findByShowIdAndStartTimeAfter(showId, LocalDateTime.now())
				.orElseThrow(() -> new MtbException(String.format(MtbException.SHOW_NOT_EXIST, showId)));

		return this.showToShowResponseDto(show);
	}

	/* gives upcoming shows by movie id */
	public List<ShowResponseDto> getShowsByMovieId(Integer movieId) {
		return showRepository.findByMovieId(movieId).stream().map(this::showToShowResponseDto)
				.collect(Collectors.toList());
	}

	/* gives upcoming shows by theatre id */
	public List<ShowResponseDto> getShowsByTheatreId(Integer theatreId) {
		return showRepository.findByTheatreId(theatreId).stream().map(this::showToShowResponseDto)
				.collect(Collectors.toList());
	}

	/* gives upcoming shows by movie id and city */
	public List<ShowResponseDto> getShowsByMovieIdAndCity(Integer movieId, String city) {
		return showRepository.findByMovieIdAndCity(movieId, city).stream().map(this::showToShowResponseDto)
				.collect(Collectors.toList());
	}

	/* gives upcoming shows by movie id, city and date */
	public List<ShowResponseDto> getShowsByMovieIdAndCityAndDate(Integer movieId, String city, Date date) {
		return showRepository.findByMovieIdAndCityAndDate(movieId, city, date).stream().map(this::showToShowResponseDto)
				.collect(Collectors.toList());
	}

	/* adds a show */
	public ShowResponseDto addShow(ShowRequestDto showRequestDto) {

		/* retrieve if movie and theatre exists */
		Movie movie = movieRepository.findById(showRequestDto.getMovieId()).orElseThrow(
				() -> new MtbException(String.format(MtbException.MOVIE_NOT_EXIST, showRequestDto.getMovieId())));

		Theatre theatre = theatreRepository.findById(showRequestDto.getTheatreId()).orElseThrow(
				() -> new MtbException(String.format(MtbException.THEATRE_NOT_EXIST, showRequestDto.getTheatreId())));

		Show show = modelMapper.map(showRequestDto, Show.class);

		show.setMovie(movie);
		show.setTheatre(theatre);

		/* calculate end time of show */
		show.setEndTime(show.getStartTime()
				.plusMinutes(show.getMovie().getDurationInMins() + MtbConstants.INTERVAL_TIME_IN_MINS));

		/* check if slot is overlapping with another show */
		if (showRepository.checkSlotOverlap(show.getTheatre(), show.getStartTime(), show.getEndTime()) > 0)
			throw new MtbException(MtbException.SHOW_OVERLAP);

		/* create seats for show */
		show.setSeats(this.createSeats(show));

		Show addedShow = showRepository.save(show);

		ShowResponseDto showResponseDto = this.showToShowResponseDto(addedShow);
		return showResponseDto;
	}

	/* updates a show */
	@Transactional
	public ShowResponseDto updateShow(ShowRequestDto showRequestDto) {

		/* check if show id exists */
		Show existingShow = showRepository.findById(showRequestDto.getShowId()).orElseThrow(
				() -> new MtbException(String.format(MtbException.SHOW_NOT_EXIST, showRequestDto.getShowId())));

		/* retrieve if movie and theatre exists */
		Movie movie = movieRepository.findById(showRequestDto.getMovieId()).orElseThrow(
				() -> new MtbException(String.format(MtbException.MOVIE_NOT_EXIST, showRequestDto.getMovieId())));

		Theatre theatre = theatreRepository.findById(showRequestDto.getTheatreId()).orElseThrow(
				() -> new MtbException(String.format(MtbException.THEATRE_NOT_EXIST, showRequestDto.getTheatreId())));

		Show show = modelMapper.map(showRequestDto, Show.class);

		show.setMovie(movie);
		show.setTheatre(theatre);

		/* calculate end time of show */
		show.setEndTime(show.getStartTime()
				.plusMinutes(show.getMovie().getDurationInMins() + MtbConstants.INTERVAL_TIME_IN_MINS));

		/* check if slot is overlapping with another show */
		if (showRepository.checkSlotOverlapUpdate(show.getTheatre(), show.getStartTime(), show.getEndTime(),
				show.getShowId()) > 0)
			throw new MtbException(MtbException.SHOW_OVERLAP);

		/* check if any of the existing show seat is already booked */
		if (seatRepository.existsByShowAndBookingNotNull(existingShow))
			throw new MtbException(String.format(MtbException.SEAT_BOOKED, "update"));

		/* delete current seats and create new ones */
		seatRepository.deleteByShow(existingShow);
		show.setSeats(this.createSeats(show));

		Show addedShow = showRepository.save(show);

		ShowResponseDto showResponseDto = this.showToShowResponseDto(addedShow);
		return showResponseDto;

	}

	/* deletes a show */
	public void deleteShow(Integer showId) {
		/* check if show id exists */
		Show existingShow = showRepository.findById(showId)
				.orElseThrow(() -> new MtbException(String.format(MtbException.SHOW_NOT_EXIST, showId)));

		/* check if any of the existing show seat is already booked */
		if (seatRepository.existsByShowAndBookingNotNull(existingShow))
			throw new MtbException(String.format(MtbException.SEAT_BOOKED, "delete"));

		showRepository.deleteById(showId);
	}

	/* maps Show to ShowResponseDto */
	public ShowResponseDto showToShowResponseDto(Show show) {
		ShowResponseDto showResponseDto = new ShowResponseDto();
		showResponseDto.setShowId(show.getShowId());
		showResponseDto.setMovieDto(modelMapper.map(show.getMovie(), MovieDto.class));
		showResponseDto.setTheatreDto(modelMapper.map(show.getTheatre(), TheatreDto.class));
		showResponseDto.setStartTime(show.getStartTime());
		showResponseDto.setEndTime(show.getEndTime());
		showResponseDto.setPrice(show.getPrice());

		return showResponseDto;
	}

	/* create and add seats for show */
	private List<Seat> createSeats(Show show) {
		List<Seat> seatList = new ArrayList<>();

		for (int i = 1; i <= show.getTheatre().getSeatCount(); i++) {
			seatList.add(new Seat(i, show));
		}

		return seatList;
	}

}
