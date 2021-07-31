package com.adventureit.chat;

import com.adventureit.chat.Entity.Chat;
import com.adventureit.chat.Entity.ColorPair;
import com.adventureit.chat.Entity.GroupChat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
@EnableEurekaClient
public class ChatServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatServiceApplication.class, args);
	}

}
