package com.adventureit.adventureservice;

import com.adventureit.adventureservice.controllers.AdventureController;
import com.adventureit.adventureservice.entity.Adventure;
import com.adventureit.adventureservice.repository.AdventureRepository;
import jdk.jfr.Description;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(properties = {"service-registry-client.instance.hostname=localhost","service-registry-client.client.service-url.defaultZone=http://localhost:8761/eureka/","service-registry-client.client.register-with-eureka=true", "service-registry-client.client.fetch-registry=true","adventure-microservice.application-name=ADVENTURE-MICROSERVICE", "adventure-microservice.datasource.url=jdbc:postgresql://adventure-it-db.c9gozrkqo8dv.us-east-2.rds.amazonaws.com/adventureit?socketTimeout=5","adventure-microservice.datasource.username=postgres","adventure-microservice.datasource.password=310PB!Gq%f&J","adventure-microservice.datasource.hikari.maximum-pool-size=2","adventure-microservice.jpa.hibernate.ddl-auto=update","adventure-microservice.jpa.show-sql=false","adventure-microservice.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect","adventure-microservice.jpa.properties.hibernate.format_sql=true" })
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
