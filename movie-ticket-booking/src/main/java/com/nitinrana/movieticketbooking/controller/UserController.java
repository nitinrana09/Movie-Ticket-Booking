package com.nitinrana.movieticketbooking.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
import com.nitinrana.movieticketbooking.dto.UserRequestDto;
import com.nitinrana.movieticketbooking.dto.UserResponseDto;
import com.nitinrana.movieticketbooking.response.ApiResponse;
import com.nitinrana.movieticketbooking.service.UserService;
import com.nitinrana.movieticketbooking.validation.Create;
import com.nitinrana.movieticketbooking.validation.Update;
import com.nitinrana.movieticketbooking.validation.UserCredentials;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	@Lazy
	UserService userService;

	/* handles request of retrieving all users */
	@PreAuthorize(MtbConstants.AUTH_ADMIN)
	@GetMapping("/getUsers")
	public ResponseEntity<ApiResponse> getUsers() {
		List<UserResponseDto> list = userService.getUsers();

		return new ResponseEntity<>(new ApiResponse(true, list), HttpStatus.OK);
	}

	/* handles request of retrieving a user */
	@GetMapping("/getUser/{email}")
	public ResponseEntity<ApiResponse> getUser(@PathVariable("email") String email) {
		UserResponseDto userResponseDto = userService.getUser(email);

		return new ResponseEntity<>(new ApiResponse(true, userResponseDto), HttpStatus.OK);
	}

	/* handles request of creating a user */
	@PostMapping("/createUser")
	public ResponseEntity<ApiResponse> createUser(@Validated(Create.class) @RequestBody UserRequestDto userRequestDto) {
		UserResponseDto userResponseDto = userService.createUser(userRequestDto);

		return new ResponseEntity<>(new ApiResponse(true, userResponseDto), HttpStatus.CREATED);
	}

	/* handles request of updating a user */
	@PreAuthorize(MtbConstants.AUTH_ADMIN_USER)
	@PutMapping("/updateUser")
	public ResponseEntity<ApiResponse> updateUser(@Validated(Update.class) @RequestBody UserRequestDto userRequestDto) {
		UserResponseDto userResponseDto = userService.updateUser(userRequestDto);

		return new ResponseEntity<>(new ApiResponse(true, userResponseDto), HttpStatus.OK);
	}

	/* handles request of disabling a user */
	@PreAuthorize(MtbConstants.AUTH_ADMIN_USER)
	@DeleteMapping("/disableUser/{password}")
	public ResponseEntity<ApiResponse> disableUser(@PathVariable("password") String password) {
		userService.disableUser(password);

		return new ResponseEntity<>(new ApiResponse(true, null), HttpStatus.OK);
	}

	/* handles request of login a user */
	@PostMapping("/login")
	public ResponseEntity<ApiResponse> loginUser(
			@Validated(UserCredentials.class) @RequestBody UserRequestDto userRequestDto) {
		Map<String, String> map = userService.loginUser(userRequestDto);

		return new ResponseEntity<>(new ApiResponse(true, map), HttpStatus.OK);
	}

}
