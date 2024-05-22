package com.nitinrana.movieticketbooking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nitinrana.movieticketbooking.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	/* get user by email */
	Optional<User> findByEmail(String email);

	/* check if user exists by email */
	Boolean existsByEmail(String email);
}
