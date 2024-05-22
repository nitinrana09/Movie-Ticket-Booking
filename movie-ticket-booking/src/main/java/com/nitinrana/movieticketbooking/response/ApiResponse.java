package com.nitinrana.movieticketbooking.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
	Boolean success;
	Object message;
}
