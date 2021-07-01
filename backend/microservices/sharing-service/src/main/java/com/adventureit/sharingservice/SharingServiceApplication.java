package com.adventureit.sharingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SharingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SharingServiceApplication.class, args);
	}

}
