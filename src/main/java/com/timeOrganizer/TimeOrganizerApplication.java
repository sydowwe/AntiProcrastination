package com.timeOrganizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class TimeOrganizerApplication {
	public static void main(String[] args) {
		SpringApplication.run(TimeOrganizerApplication.class, args);
	}
}
