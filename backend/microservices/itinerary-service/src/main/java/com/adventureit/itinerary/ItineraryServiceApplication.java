package com.adventureit.itinerary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ItineraryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItineraryServiceApplication.class, args);
	}

}
