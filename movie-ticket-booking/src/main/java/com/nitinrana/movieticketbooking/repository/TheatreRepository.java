package com.nitinrana.movieticketbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nitinrana.movieticketbooking.model.Theatre;

public interface TheatreRepository extends JpaRepository<Theatre, Integer> {
}
