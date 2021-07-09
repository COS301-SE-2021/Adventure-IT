package com.adventureit.adventureservice;

import com.adventureit.adventureservice.Requests.CreateAdventureRequest;
import com.adventureit.adventureservice.Service.AdventureServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import java.time.LocalDate;
import java.util.UUID;

@SpringBootApplication
@EnableEurekaClient
public class AdventureServiceApplication {

//	@Autowired
//	public AdventureServiceImplementation adventureService;
	public static void main(String[] args) {
		SpringApplication.run(AdventureServiceApplication.class, args);
//		final UUID mockOwnerID = UUID.fromString("1660bd85-1c13-42c0-955c-63b1eda4e90b");
//		final UUID mockAttendeeID = UUID.fromString("7a984756-16a5-422e-a377-89e1772dd71e");
//		CreateAdventureRequest req=new CreateAdventureRequest("Mock Adventure 1","Mock Description 1", UUID.randomUUID(), mockOwnerID, LocalDate.of(2021, 7, 5),LocalDate.of(2021, 7, 9));
//		adventureService.createAdventure(req);
//		req=new CreateAdventureRequest("Mock Adventure 2","Mock Description 2", UUID.randomUUID(), mockOwnerID, LocalDate.of(2021, 1, 3),LocalDate.of(2022, 1, 2));
//		adventureService.createAdventure(req);
//		req=new CreateAdventureRequest("Mock Adventure 3", "Mock Description 3",UUID.randomUUID(), mockAttendeeID, LocalDate.of(2022, 1, 4),LocalDate.of(2022, 1, 22));
//		adventureService.createAdventure(req);
	}

}
