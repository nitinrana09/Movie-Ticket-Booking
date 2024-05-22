package com.nitinrana.movieticketbooking.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nitinrana.movieticketbooking.dto.MovieDto;
import com.nitinrana.movieticketbooking.exception.MtbException;
import com.nitinrana.movieticketbooking.model.Movie;
import com.nitinrana.movieticketbooking.repository.MovieRepository;
import com.nitinrana.movieticketbooking.repository.ShowRepository;

@Service
public class MovieServiceImpl implements MovieService {

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ShowRepository showRepository;

	/* retrieves all movies */
	public List<MovieDto> getMovies() {
		return movieRepository.findAll().stream().map(movie -> modelMapper.map(movie, MovieDto.class))
				.collect(Collectors.toList());
	}

	/* retrieves a movie */
	public MovieDto getMovie(int movieId) {
		Movie movie = movieRepository.findById(movieId)
				.orElseThrow(() -> new MtbException(String.format(MtbException.MOVIE_NOT_EXIST, movieId)));

		return modelMapper.map(movie, MovieDto.class);
	}

	/* adds a movie */
	public MovieDto addMovie(MovieDto movieDto) {
		Movie addedMovie = movieRepository.save(modelMapper.map(movieDto, Movie.class));

		return modelMapper.map(addedMovie, MovieDto.class);
	}

	/* updates a movie */
	public MovieDto updateMovie(MovieDto movieDto) {
		Movie movie = modelMapper.map(movieDto, Movie.class);

		if (!movieRepository.existsById(movie.getMovieId()))
			throw new MtbException(String.format(MtbException.MOVIE_NOT_EXIST, movie.getMovieId()));

		if (showRepository.countByMovieId(movie.getMovieId()) > 0)
			throw new MtbException(String.format(MtbException.NOT_WITH_PENDING_SHOWS, "update", "movie"));

		Movie updatedMovie = movieRepository.save(movie);

		return modelMapper.map(updatedMovie, MovieDto.class);
	}

	/* deletes a movie */
	public void deleteMovie(int movieId) {

		if (!movieRepository.existsById(movieId))
			throw new MtbException(String.format(MtbException.MOVIE_NOT_EXIST, movieId));

		if (showRepository.countByMovieId(movieId) > 0)
			throw new MtbException(String.format(MtbException.NOT_WITH_PENDING_SHOWS, "delete", "movie"));

		movieRepository.deleteById(movieId);
	}

}
