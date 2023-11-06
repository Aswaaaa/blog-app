package com.edstem.blogapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BlogAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration()
				.setFieldMatchingEnabled(true)
				.setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
		return mapper;
	}


}
