package com.nitinrana.movieticketbooking.service;

import java.util.List;

import com.nitinrana.movieticketbooking.dto.MovieDto;

public interface MovieService {
	List<MovieDto> getMovies();

	MovieDto getMovie(int movieId);

	MovieDto addMovie(MovieDto movieDto);

	MovieDto updateMovie(MovieDto movieDto);

	void deleteMovie(int movieId);
}
