package com.nitinrana.movieticketbooking.service;

import java.sql.Date;
import java.util.List;

import com.nitinrana.movieticketbooking.dto.ShowRequestDto;
import com.nitinrana.movieticketbooking.dto.ShowResponseDto;
import com.nitinrana.movieticketbooking.model.Show;

public interface ShowService {
	List<ShowResponseDto> getShows();

	List<ShowResponseDto> getAllShows();

	ShowResponseDto getShow(Integer showId);

	List<ShowResponseDto> getShowsByMovieId(Integer movieId);

	List<ShowResponseDto> getShowsByTheatreId(Integer theatreId);

	List<ShowResponseDto> getShowsByMovieIdAndCity(Integer movieId, String city);

	List<ShowResponseDto> getShowsByMovieIdAndCityAndDate(Integer movieId, String city, Date date);

	ShowResponseDto addShow(ShowRequestDto showRequestDto);

	ShowResponseDto updateShow(ShowRequestDto showRequestDto);

	void deleteShow(Integer showId);

	ShowResponseDto showToShowResponseDto(Show show);
}
