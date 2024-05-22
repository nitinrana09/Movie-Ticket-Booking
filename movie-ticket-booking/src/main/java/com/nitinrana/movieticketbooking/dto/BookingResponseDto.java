package com.nitinrana.movieticketbooking.dto;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nitinrana.movieticketbooking.model.Seat;
import com.nitinrana.movieticketbooking.model.Show;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponseDto {
	private Integer bookingId;

	@JsonProperty("show")
	private ShowResponseDto showResponseDto;

	private Integer bookingSeatCount;

	@JsonProperty("bookedSeats")
	private List<SeatDto> bookedSeatsDto;

	private Date bookingDate;

	private String status;
}
