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
import com.nitinrana.movieticketbooking.dto.TheatreDto;
import com.nitinrana.movieticketbooking.response.ApiResponse;
import com.nitinrana.movieticketbooking.service.TheatreService;
import com.nitinrana.movieticketbooking.validation.Create;
import com.nitinrana.movieticketbooking.validation.Update;

@RestController
@RequestMapping("/theatre")
public class TheatreController {

	@Autowired
	TheatreService theatreService;

	/* handles request of retrieving all theatres */
	@PreAuthorize(MtbConstants.AUTH_ADMIN_USER)
	@GetMapping("/getTheatres")
	public ResponseEntity<ApiResponse> getTheatres() {
		List<TheatreDto> list = theatreService.getTheatres();

		return new ResponseEntity<>(new ApiResponse(true, list), HttpStatus.OK);
	}

	/* handles request of retrieving a theatre */
	@PreAuthorize(MtbConstants.AUTH_ADMIN_USER)
	@GetMapping("/getTheatre/{theatreId}")
	public ResponseEntity<ApiResponse> getTheatre(@PathVariable Integer theatreId) {
		TheatreDto theatreDto = theatreService.getTheatre(theatreId);

		return new ResponseEntity<>(new ApiResponse(true, theatreDto), HttpStatus.OK);
	}

	/* handles request of adding a theatre */
	@PreAuthorize(MtbConstants.AUTH_ADMIN)
	@PostMapping("/addTheatre")
	public ResponseEntity<ApiResponse> addTheatre(@Validated(Create.class) @RequestBody TheatreDto theatreDto) {
		TheatreDto addedTheatreDto = theatreService.addTheatre(theatreDto);

		return new ResponseEntity<>(new ApiResponse(true, addedTheatreDto), HttpStatus.CREATED);
	}

	/* handles request of updating a theatre */
	@PreAuthorize(MtbConstants.AUTH_ADMIN)
	@PutMapping("/updateTheatre")
	public ResponseEntity<ApiResponse> updateTheatre(@Validated(Update.class) @RequestBody TheatreDto theatreDto) {
		TheatreDto updatedTheatreDto = theatreService.updateTheatre(theatreDto);

		return new ResponseEntity<>(new ApiResponse(true, updatedTheatreDto), HttpStatus.OK);
	}

	/* handles request of deleting a theatre */
	@PreAuthorize(MtbConstants.AUTH_ADMIN)
	@DeleteMapping("/deleteTheatre/{theatreId}")
	public ResponseEntity<ApiResponse> deleteTheatre(@PathVariable Integer theatreId) {
		theatreService.deleteTheatre(theatreId);

		return new ResponseEntity<>(new ApiResponse(true, null), HttpStatus.OK);
	}
}
