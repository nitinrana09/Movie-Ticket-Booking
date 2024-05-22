package com.nitinrana.movieticketbooking.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

	/* defines bean for model mapper */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
