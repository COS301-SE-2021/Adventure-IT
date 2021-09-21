package com.adventureit.notificationservice;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

@SpringBootApplication
@EnableEurekaClient
public class NotificationServiceApplication {

	@Value("${firebase-messaging-type}")
	String type;
	@Value("${firebase-messaging-project_id}")
	String projectId;
	@Value("${firebase-messaging-private_key_id}")
	String privateKeyId;
	@Value("${firebase-messaging-private_key}")
	String privateKey;
	@Value("${firebase-messaging-client_email}")
	String clientEmail;
	@Value("${firebase-messaging-client_id}")
	String clientId;
	@Value("${firebase-messaging-auth_uri}")
	String authUri;
	@Value("${firebase-messaging-token_uri}")
	String tokenUri;
	@Value("${firebase-messaging-auth_provider_x509_cert_url}")
	String authProvider;
	@Value("${firebase-messaging-client_x509_cert_url}")
	String clientx509;

	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*");
			}
		};
	}

	@Bean
	public FirebaseMessaging firebaseMessaging() throws IOException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("type",type);
		jsonObject.put("project_id",projectId);
		jsonObject.put("private_key_id",privateKeyId);
		jsonObject.put("private_key",privateKey);
		jsonObject.put("client_email",clientEmail);
		jsonObject.put("client_id",clientId);
		jsonObject.put("auth_uri",authUri);
		jsonObject.put("token_uri",tokenUri);
		jsonObject.put("auth_provider_x509_cert_url",authProvider);
		jsonObject.put("client_x509_cert_url",clientx509);
		try (FileWriter file = new FileWriter("notification.json")) {
			file.write(jsonObject.toJSONString());
		}

		FileInputStream serviceAccount = new FileInputStream("notification.json");
		GoogleCredentials googleCredentials = GoogleCredentials
				.fromStream(serviceAccount);
		FirebaseOptions firebaseOptions = FirebaseOptions
				.builder()
				.setCredentials(googleCredentials)
				.build();
		FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions, "adventureit");

		new File("notification.json").delete();
		return FirebaseMessaging.getInstance(app);
	}
}
