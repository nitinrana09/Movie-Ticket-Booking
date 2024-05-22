package com.nitinrana.movieticketbooking.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nitinrana.movieticketbooking.dto.SeatDto;
import com.nitinrana.movieticketbooking.exception.MtbException;
import com.nitinrana.movieticketbooking.model.Seat;
import com.nitinrana.movieticketbooking.repository.SeatRepository;
import com.nitinrana.movieticketbooking.repository.ShowRepository;

@Service
public class SeatServiceImpl implements SeatService {

	@Autowired
	private ShowRepository showRepository;

	@Autowired
	private SeatRepository seatRepository;

	/* gets available seats of upcoming show */
	public List<SeatDto> getAvailableSeatsByShow(Integer showId) {
		if (!showRepository.existsByShowIdAndStartTimeAfter(showId, LocalDateTime.now()))
			throw new MtbException(String.format(MtbException.SHOW_NOT_EXIST, showId));

		return seatRepository.findAvailableSeatsByShowId(showId).stream().map(this::seatToSeatDto)
				.collect(Collectors.toList());

	}

	/* maps seat to seatDto */
	public SeatDto seatToSeatDto(Seat seat) {
		return new SeatDto(seat.getSeatNumber(), seat.getBooking() == null ? false : true);
	}
}
