package com.nitinrana.movieticketbooking.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nitinrana.movieticketbooking.dto.TheatreDto;
import com.nitinrana.movieticketbooking.exception.MtbException;
import com.nitinrana.movieticketbooking.model.Theatre;
import com.nitinrana.movieticketbooking.repository.ShowRepository;
import com.nitinrana.movieticketbooking.repository.TheatreRepository;

@Service
public class TheatreServiceImpl implements TheatreService {

	@Autowired
	private TheatreRepository theatreRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ShowRepository showRepository;

	
	/* retrieves all theatres */
	@Override
	public List<TheatreDto> getTheatres() {
		return theatreRepository.findAll().stream().map(theatre -> modelMapper.map(theatre, TheatreDto.class))
				.collect(Collectors.toList());
	}

	/* retrieves a theatre */
	@Override
	public TheatreDto getTheatre(int theatreId) {
		Theatre theatre = theatreRepository.findById(theatreId)
				.orElseThrow(() -> new MtbException(String.format(MtbException.THEATRE_NOT_EXIST, theatreId)));

		return modelMapper.map(theatre, TheatreDto.class);
	}

	/* adds a theatre */
	@Override
	public TheatreDto addTheatre(TheatreDto theatreDto) {
		Theatre addedTheatre = theatreRepository.save(modelMapper.map(theatreDto, Theatre.class));

		return modelMapper.map(addedTheatre, TheatreDto.class);
	}

	/* updates a theatre */
	@Override
	public TheatreDto updateTheatre(TheatreDto theatreDto) {
		if (!theatreRepository.existsById(theatreDto.getTheatreId()))
			throw new MtbException((String.format(MtbException.THEATRE_NOT_EXIST, theatreDto.getTheatreId())));

		if (showRepository.countByTheatreId(theatreDto.getTheatreId()) > 0)
			throw new MtbException(String.format(MtbException.NOT_WITH_PENDING_SHOWS, "update", "theatre"));

		Theatre updatedTheatre = theatreRepository.save(modelMapper.map(theatreDto, Theatre.class));
		return modelMapper.map(updatedTheatre, TheatreDto.class);
	}

	/* deletes a theatre */
	@Override
	public void deleteTheatre(int theatreId) {
		if (!theatreRepository.existsById(theatreId))
			throw new MtbException((String.format(MtbException.THEATRE_NOT_EXIST, theatreId)));

		if (showRepository.countByTheatreId(theatreId) > 0)
			throw new MtbException(String.format(MtbException.NOT_WITH_PENDING_SHOWS, "delete", "theatre"));

		theatreRepository.deleteById(theatreId);
	}

}
