package com.nitinrana.movieticketbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nitinrana.movieticketbooking.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

}
