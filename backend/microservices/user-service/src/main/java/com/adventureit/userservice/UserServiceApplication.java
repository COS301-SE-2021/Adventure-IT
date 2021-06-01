package com.adventureit.userservice;

import com.adventureit.userservice.Service.UserServiceImplementation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserServiceApplication {
	private UserServiceImplementation user;
	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);

	}
}
