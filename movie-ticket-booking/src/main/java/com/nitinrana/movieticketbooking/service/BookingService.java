package com.nitinrana.movieticketbooking.service;

import java.util.List;

import com.nitinrana.movieticketbooking.dto.BookingRequestDto;
import com.nitinrana.movieticketbooking.dto.BookingResponseDto;

public interface BookingService {
	List<BookingResponseDto> getBookings();
	BookingResponseDto getBooking(Integer bookingId);
	List<BookingResponseDto> getBookingsUpcomingConfirmed();
	BookingResponseDto addBooking(BookingRequestDto bookingRequestDto);
	BookingResponseDto updateBooking(BookingRequestDto bookingRequestDto);
	void cancelBooking(Integer bookingId);
}
