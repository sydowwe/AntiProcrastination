package com.timeOrganizer;

import com.timeOrganizer.config.ApplicationProperties;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class TimeOrganizerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimeOrganizerApplication.class, args);
	}
	/*@Autowired
	private ApplicationProperties applicationProperties;
	@PostConstruct
	public void init(){
		final String appName = applicationProperties.getName();
		System.out.println("Application name is : " + appName);
		System.out.println("Application name is : " + applicationProperties.getVersion());
	}*/

}
