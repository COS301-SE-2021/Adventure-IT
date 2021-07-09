package com.adventureit.adventureservice;

import com.adventureit.adventureservice.Controllers.AdventureController;
import com.adventureit.adventureservice.Entity.Adventure;
import com.adventureit.adventureservice.Repository.AdventureRepository;
import com.adventureit.adventureservice.Requests.CreateAdventureRequest;
import com.adventureit.adventureservice.Responses.CreateAdventureResponse;
import com.adventureit.adventureservice.Service.AdventureServiceImplementation;
import jdk.jfr.Description;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest
public class LocalTest {
@Autowired
    AdventureRepository adventureRepo;
@Autowired
    AdventureServiceImplementation adventureService;
@Test
@Transactional
    public void populate()
    {
        final UUID mockOwnerID = UUID.fromString("1660bd85-1c13-42c0-955c-63b1eda4e90b");
        final UUID mockAttendeeID = UUID.fromString("7a984756-16a5-422e-a377-89e1772dd71e");
        CreateAdventureRequest req=new CreateAdventureRequest("Mock Adventure 1","Mock Description 1", UUID.randomUUID(), mockOwnerID, LocalDate.of(2021, 7, 5),LocalDate.of(2021, 7, 9));
        adventureService.createAdventure(req);
        req=new CreateAdventureRequest("Mock Adventure 2","Mock Description 2", UUID.randomUUID(), mockOwnerID, LocalDate.of(2021, 1, 3),LocalDate.of(2022, 1, 2));
        adventureService.createAdventure(req);
        req=new CreateAdventureRequest("Mock Adventure 3", "Mock Description 3",UUID.randomUUID(), mockAttendeeID, LocalDate.of(2022, 1, 4),LocalDate.of(2022, 1, 22));
        adventureService.createAdventure(req);

    }

}
