package com.adventureit.adventureservice;

import com.adventureit.adventureservice.controllers.AdventureController;
import com.adventureit.adventureservice.entity.Adventure;
import com.adventureit.adventureservice.Repository.AdventureRepository;
import jdk.jfr.Description;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AdventureServiceIntegrationTests {

    @Autowired
    private AdventureController adventureController;

    @Autowired
    private AdventureRepository adventureRepository;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    final UUID mockAdventureID = UUID.randomUUID();
    final UUID mockUserID = UUID.randomUUID();

    @Test
    @Description("Ensure that the Adventure Controller loads")
    public void adventureControllerLoads() throws Exception {
        Assertions.assertNotNull(adventureController);
    }

    @Test
    @Description("Ensure that the controller is accepting traffic and responding")
    public void httpTest_returnResponse(){
        Assertions.assertEquals(this.restTemplate.getForObject("http://localhost:" + port + "/adventure/test", String.class),"Adventure Controller is functional");
    }

    @Test
    @Description("Ensure controller can retrieve all existing adventures")
    public void httpGetAllAdventures_returnAllAdventures(){
        Adventure[] adventures = this.restTemplate.getForEntity("http://localhost:" + port + "/adventure/all", Adventure[].class).getBody();
        Assertions.assertTrue(adventures.length != 0);
    }

    @Test
    @Description("Ensure controller returns adventures when provided a valid owner of adventures")
    public void httpGetExistingOwnerAdventures_ReturnAdventures(){
        UUID adventureId = UUID.randomUUID();
        UUID ownerId = UUID.randomUUID();
        UUID location = UUID.randomUUID();
        Adventure adventure = new Adventure("Mock", "Mock", adventureId, ownerId, LocalDate.now(), LocalDate.now(), location);
        adventureRepository.saveAndFlush(adventure);
        Adventure[] adventures = this.restTemplate.getForEntity("http://localhost:" + port + "/adventure/owner/" + ownerId, Adventure[].class).getBody();
        Assertions.assertTrue(adventures.length != 0);
    }



    @Test
    @Description("Ensure controller returns adventures when provided a valid attendee of adventures")
    public void httpGetExistingAttendeeAdventures_ReturnAdventures(){
        UUID adventureId = UUID.randomUUID();
        UUID userID = UUID.randomUUID();
        UUID location = UUID.randomUUID();
        Adventure adventure = new Adventure("Mock", "Mock", adventureId, userID, LocalDate.now(), LocalDate.now(), location);
        adventureRepository.saveAndFlush(adventure);
        Adventure[] adventures = this.restTemplate.getForEntity("http://localhost:" + port + "/adventure/attendee/" + userID, Adventure[].class).getBody();
        Assertions.assertTrue(adventures.length != 0);
    }

    @Test
    @Description("Ensure controller can successfully remove an existing adventure")
    public void httpRemoveValidAdventure_ReturnSuccessfulRemoval(){
        this.restTemplate.delete("http://localhost:" + port + "/adventure/remove/"+mockAdventureID);
        Adventure[] adventures = this.restTemplate.getForEntity("http://localhost:" + port + "/adventure/all", Adventure[].class).getBody();
        Assertions.assertTrue(adventures.length != 0);
    }
}
