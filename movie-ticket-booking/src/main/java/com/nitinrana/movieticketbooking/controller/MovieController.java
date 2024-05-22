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
import com.nitinrana.movieticketbooking.dto.MovieDto;
import com.nitinrana.movieticketbooking.response.ApiResponse;
import com.nitinrana.movieticketbooking.service.MovieService;
import com.nitinrana.movieticketbooking.validation.Create;
import com.nitinrana.movieticketbooking.validation.Update;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/movie")
public class MovieController {

	@Autowired
	MovieService movieService;

	/* handles request of retrieving all movies */
	@PreAuthorize(MtbConstants.AUTH_ADMIN_USER)
	@GetMapping("/getMovies")
	public ResponseEntity<ApiResponse> getMovies() {
		List<MovieDto> list = movieService.getMovies();

		return new ResponseEntity<>(new ApiResponse(true, list), HttpStatus.OK);
	}

	/* handles request of retrieving a movie */
	@PreAuthorize(MtbConstants.AUTH_ADMIN_USER)
	@GetMapping("/getMovie/{movieId}")
	public ResponseEntity<ApiResponse> getMovie(@PathVariable Integer movieId) {
		MovieDto movieDto = movieService.getMovie(movieId);

		return new ResponseEntity<>(new ApiResponse(true, movieDto), HttpStatus.OK);
	}

	/* handles request of adding a movie */
	@PreAuthorize(MtbConstants.AUTH_ADMIN)
	@PostMapping("/addMovie")
	public ResponseEntity<ApiResponse> addMovie(@Validated(Create.class) @RequestBody MovieDto movieDto) {
		MovieDto addedMovieDto = movieService.addMovie(movieDto);

		return new ResponseEntity<>(new ApiResponse(true, addedMovieDto), HttpStatus.CREATED);
	}

	/* handles request of updating a movie */
	@PreAuthorize(MtbConstants.AUTH_ADMIN)
	@PutMapping("/updateMovie")
	public ResponseEntity<ApiResponse> updateMovie(@Validated(Update.class) @RequestBody MovieDto movieDto) {
		MovieDto updatedMovieDto = movieService.updateMovie(movieDto);

		return new ResponseEntity<>(new ApiResponse(true, updatedMovieDto), HttpStatus.OK);
	}

	/* handles request of deleting a movie */
	@PreAuthorize(MtbConstants.AUTH_ADMIN)
	@DeleteMapping("/deleteMovie/{movieId}")
	public ResponseEntity<ApiResponse> deleteMovie(@PathVariable Integer movieId) {
		movieService.deleteMovie(movieId);

		return new ResponseEntity<>(new ApiResponse(true, null), HttpStatus.OK);
	}

}
