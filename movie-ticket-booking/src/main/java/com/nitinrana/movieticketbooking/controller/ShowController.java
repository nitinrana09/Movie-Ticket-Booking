package com.nitinrana.movieticketbooking.controller;

import java.sql.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nitinrana.movieticketbooking.constant.MtbConstants;
import com.nitinrana.movieticketbooking.dto.ShowRequestDto;
import com.nitinrana.movieticketbooking.dto.ShowResponseDto;
import com.nitinrana.movieticketbooking.response.ApiResponse;
import com.nitinrana.movieticketbooking.service.ShowService;
import com.nitinrana.movieticketbooking.validation.Create;
import com.nitinrana.movieticketbooking.validation.Update;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/show")
public class ShowController {

	@Autowired
	ShowService showService;

	/* handles request for retrieving upcoming shows */
	@PreAuthorize(MtbConstants.AUTH_ADMIN_USER)
	@GetMapping("/getShows")
	public ResponseEntity<ApiResponse> getShows() {
		List<ShowResponseDto> list = showService.getShows();
		return new ResponseEntity<>(new ApiResponse(true, list), HttpStatus.OK);
	}

	/* handles request for retrieving all shows */
	@PreAuthorize(MtbConstants.AUTH_ADMIN)
	@GetMapping("/getAllShows")
	public ResponseEntity<ApiResponse> getAllShows() {
		List<ShowResponseDto> list = showService.getAllShows();
		return new ResponseEntity<>(new ApiResponse(true, list), HttpStatus.OK);
	}

	/* handles request for retrieving show by show id */
	@PreAuthorize(MtbConstants.AUTH_ADMIN_USER)
	@GetMapping("/getShow/{showId}")
	public ResponseEntity<ApiResponse> getShow(@PathVariable("showId") Integer showId) {
		ShowResponseDto showResponseDto = showService.getShow(showId);
		return new ResponseEntity<>(new ApiResponse(true, showResponseDto), HttpStatus.OK);
	}

	/* handles request for retrieving upcoming shows by movie id */
	@PreAuthorize(MtbConstants.AUTH_ADMIN_USER)
	@GetMapping("/getShowsByMovieId")
	public ResponseEntity<ApiResponse> getShowsByMovieId(@RequestParam("movieId") Integer movieId) {
		List<ShowResponseDto> list = showService.getShowsByMovieId(movieId);
		return new ResponseEntity<>(new ApiResponse(true, list), HttpStatus.OK);
	}

	/* handles request for retrieving upcoming shows by theatre id */
	@PreAuthorize(MtbConstants.AUTH_ADMIN_USER)
	@GetMapping("/getShowsByTheatreId")
	public ResponseEntity<ApiResponse> getShowsByTheatreId(@RequestParam("theatreId") Integer theatreId) {
		List<ShowResponseDto> list = showService.getShowsByTheatreId(theatreId);
		return new ResponseEntity<>(new ApiResponse(true, list), HttpStatus.OK);
	}

	/* handles request for retrieving upcoming shows by movie id and city */
	@PreAuthorize(MtbConstants.AUTH_ADMIN_USER)
	@GetMapping("/getShowsByMovieIdCity")
	public ResponseEntity<ApiResponse> getShowsByMovieIdAndCity(@RequestParam("movieId") Integer movieId,
			@RequestParam("city") String city) {
		List<ShowResponseDto> list = showService.getShowsByMovieIdAndCity(movieId, city);
		return new ResponseEntity<>(new ApiResponse(true, list), HttpStatus.OK);
	}

	/* handles request for retrieving upcoming shows by movie id, city and date */
	@PreAuthorize(MtbConstants.AUTH_ADMIN_USER)
	@GetMapping("/getShowsByMovieIdCityDate")
	public ResponseEntity<ApiResponse> getShowsByMovieIdAndCityAndDate(@RequestParam("movieId") Integer movieId,
			@RequestParam("city") String city, @RequestParam("date") Date date) {
		List<ShowResponseDto> list = showService.getShowsByMovieIdAndCityAndDate(movieId, city, date);
		return new ResponseEntity<>(new ApiResponse(true, list), HttpStatus.OK);
	}

	/* handles request for adding a show */
	@PreAuthorize(MtbConstants.AUTH_ADMIN)
	@PostMapping("/addShow")
	public ResponseEntity<ApiResponse> addShow(@Validated(Create.class) @RequestBody ShowRequestDto showRequestDto) {
		ShowResponseDto showResponseDto = showService.addShow(showRequestDto);
		return new ResponseEntity<>(new ApiResponse(true, showResponseDto), HttpStatus.CREATED);
	}

	/* handles request for updating a show */
	@PreAuthorize(MtbConstants.AUTH_ADMIN)
	@PutMapping("/updateShow")
	public ResponseEntity<ApiResponse> updateShow(@Validated(Update.class) @RequestBody ShowRequestDto showReqestDto) {
		ShowResponseDto showResponseDto = showService.updateShow(showReqestDto);
		return new ResponseEntity<>(new ApiResponse(true, showResponseDto), HttpStatus.OK);
	}

	/* handles request for deleting a show */
	@PreAuthorize(MtbConstants.AUTH_ADMIN)
	@DeleteMapping("/deleteShow/{showId}")
	public ResponseEntity<ApiResponse> deleteShow(@PathVariable("showId") Integer showId) {
		showService.deleteShow(showId);
		return new ResponseEntity<>(new ApiResponse(true, null), HttpStatus.OK);
	}
}
