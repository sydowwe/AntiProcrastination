package com.timeOrganizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@ConfigurationPropertiesScan
@CrossOrigin(origins = "http://localhost:5173")
public class TimeOrganizerApplication {
	public static void main(String[] args) {
		SpringApplication.run(TimeOrganizerApplication.class, args);
	}
}
