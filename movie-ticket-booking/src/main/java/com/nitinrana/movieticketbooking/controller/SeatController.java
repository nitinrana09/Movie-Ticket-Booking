package com.nitinrana.movieticketbooking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nitinrana.movieticketbooking.constant.MtbConstants;
import com.nitinrana.movieticketbooking.dto.SeatDto;
import com.nitinrana.movieticketbooking.response.ApiResponse;
import com.nitinrana.movieticketbooking.service.SeatService;

@RestController
@RequestMapping("/seat")
public class SeatController {

	@Autowired
	SeatService seatService;

	/* retrieves available seats of upcoming show */
	@PreAuthorize(MtbConstants.AUTH_ADMIN_USER)
	@GetMapping("/getAvailableSeatsByShowId/{showId}")
	public ResponseEntity<ApiResponse> getAvailableSeatsByShowId(@PathVariable("showId") Integer showId) {
		List<SeatDto> list = seatService.getAvailableSeatsByShow(showId);
		return new ResponseEntity<>(new ApiResponse(true, list), HttpStatus.OK);
	}
}
