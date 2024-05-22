package com.nitinrana.movieticketbooking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nitinrana.movieticketbooking.constant.MtbConstants;
import com.nitinrana.movieticketbooking.dto.BookingRequestDto;
import com.nitinrana.movieticketbooking.dto.BookingResponseDto;
import com.nitinrana.movieticketbooking.response.ApiResponse;
import com.nitinrana.movieticketbooking.service.BookingService;
import com.nitinrana.movieticketbooking.validation.Create;
import com.nitinrana.movieticketbooking.validation.Update;

@RestController
@RequestMapping("/booking")
public class BookingController {

	@Autowired
	BookingService bookingService;

	/* handles request of retrieving all the bookings for the user */
	@PreAuthorize(MtbConstants.AUTH_USER)
	@GetMapping("/getBookings")
	public ResponseEntity<ApiResponse> getBookings() {
		List<BookingResponseDto> list = bookingService.getBookings();
		return new ResponseEntity<>(new ApiResponse(true, list), HttpStatus.OK);
	}

	/*
	 * handles request of retrieving booking if that booking exists and belongs to
	 * user
	 */ 
	@PreAuthorize(MtbConstants.AUTH_USER)
	@GetMapping("/getBooking/{bookingId}")
	public ResponseEntity<ApiResponse> getBooking(@PathVariable("bookingId") Integer bookingId) {
		BookingResponseDto bookingResponseDto = bookingService.getBooking(bookingId);
		return new ResponseEntity<>(new ApiResponse(true, bookingResponseDto), HttpStatus.OK);
	}

	/* handles request of retrieving upcoming confirmed bookings for user */
	@PreAuthorize(MtbConstants.AUTH_USER)
	@GetMapping("/getBookingsUpcomingConfirmed")
	public ResponseEntity<ApiResponse> getBookingUpcomingConfirmed() {
		List<BookingResponseDto> list = bookingService.getBookingsUpcomingConfirmed();
		return new ResponseEntity<>(new ApiResponse(true, list), HttpStatus.OK);
	}

	/* handles request of booking tickets for a show */
	@PreAuthorize(MtbConstants.AUTH_USER)
	@PostMapping("/bookShow")
	public ResponseEntity<ApiResponse> addBooking(
			@Validated(Create.class) @RequestBody BookingRequestDto bookingReqestDto) {
		BookingResponseDto bookingResponseDto = bookingService.addBooking(bookingReqestDto);
		return new ResponseEntity<>(new ApiResponse(true, bookingResponseDto), HttpStatus.CREATED);
	}

	/* handles request of updating a booking */
	@PreAuthorize(MtbConstants.AUTH_USER)
	@PutMapping("/updateBooking")
	public ResponseEntity<ApiResponse> updateBooking(
			@Validated(Update.class) @RequestBody BookingRequestDto bookingRequestDto) {
		BookingResponseDto bookingResponseDto = bookingService.updateBooking(bookingRequestDto);
		return new ResponseEntity<>(new ApiResponse(true, bookingResponseDto), HttpStatus.OK);
	}

	/* handles request of canceling a booking */
	@PreAuthorize(MtbConstants.AUTH_USER)
	@DeleteMapping("/cancelBooking/{bookingId}")
	public ResponseEntity<ApiResponse> cancelBooking(@PathVariable("bookingId") Integer bookingId) {
		bookingService.cancelBooking(bookingId);
		return new ResponseEntity<>(new ApiResponse(true, null), HttpStatus.OK);
	}
}
