package com.nitinrana.movieticketbooking.service;

import java.util.List;

import com.nitinrana.movieticketbooking.dto.TheatreDto;

public interface TheatreService {

	List<TheatreDto> getTheatres();

	TheatreDto getTheatre(int theareId);

	TheatreDto addTheatre(TheatreDto theatreDto);

	TheatreDto updateTheatre(TheatreDto theatreDto);

	void deleteTheatre(int theatreId);

}
