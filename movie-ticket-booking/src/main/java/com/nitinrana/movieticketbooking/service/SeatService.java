package com.nitinrana.movieticketbooking.service;

import java.util.List;

import com.nitinrana.movieticketbooking.dto.SeatDto;
import com.nitinrana.movieticketbooking.model.Seat;

public interface SeatService {
	List<SeatDto> getAvailableSeatsByShow(Integer showId);

	SeatDto seatToSeatDto(Seat seat);
}
