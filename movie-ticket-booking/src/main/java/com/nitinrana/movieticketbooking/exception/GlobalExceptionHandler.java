package com.nitinrana.movieticketbooking.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.nitinrana.movieticketbooking.response.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

	/* Business logic Exceptions Handlers */

	/* Handles Validation Exceptions */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
		Map<String, String> map = new HashMap<>();

		ex.getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			map.put(fieldName, message);
		});

		return new ResponseEntity<>(new ApiResponse(false, map), HttpStatus.BAD_REQUEST);
	}

	/* Handles Custom Exceptions */
	@ExceptionHandler(MtbException.class)
	public ResponseEntity<ApiResponse> mtbExceptionHandler(MtbException ex) {

		return new ResponseEntity<>(new ApiResponse(false, ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

	/*
	 * Handles when Request's structure is invalid or JSON can't be parsed into Java
	 * Object
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiResponse> httpMessageNotReadableExceptionHandler() {
		return new ResponseEntity<>(new ApiResponse(false, "Invalid Request Payload"), HttpStatus.BAD_REQUEST);
	}

	/* Security Exceptions Handler */
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ApiResponse> badCredentialsExceptionHandler(BadCredentialsException ex) {

		return new ResponseEntity<>(new ApiResponse(false, "Email or Password isn't correct"), HttpStatus.UNAUTHORIZED);
	}

}
