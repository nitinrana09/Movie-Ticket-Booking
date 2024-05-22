package com.nitinrana.movieticketbooking.service;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

import com.nitinrana.movieticketbooking.dto.UserRequestDto;
import com.nitinrana.movieticketbooking.dto.UserResponseDto;

public interface UserService {

	List<UserResponseDto> getUsers();

	UserResponseDto getUser(String email);

	UserResponseDto createUser(UserRequestDto userRequestDto);

	UserResponseDto updateUser(UserRequestDto userRequestDto);

	void disableUser(String password);

	Map<String, String> loginUser(UserRequestDto userRequestDto);

	UserDetails getUserDetails();
}
