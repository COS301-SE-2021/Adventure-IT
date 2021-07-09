package com.adventureit.adventureservice;

import com.adventureit.adventureservice.Controllers.AdventureController;
import com.adventureit.adventureservice.Entity.Adventure;
import com.adventureit.adventureservice.Requests.CreateAdventureRequest;
import com.adventureit.adventureservice.Responses.CreateAdventureResponse;
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

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    final UUID mockAdventureID = UUID.randomUUID();
    final UUID mockUserID = UUID.randomUUID();

    @Test
    @Order(1)
    @Description("Ensure that the Adventure Controller loads")
    public void adventureControllerLoads() throws Exception {
        Assertions.assertNotNull(adventureController);
    }

    @Test
    @Order(2)
    @Description("Ensure that the controller is accepting traffic and responding")
    public void httpTest_returnResponse(){
        Assertions.assertEquals(this.restTemplate.getForObject("http://localhost:" + port + "/adventure/test", String.class),"Adventure Controller is functional \n");
    }

    @Test
    @Order(3)
    @Description("Ensure controller can populate the repository with mock data")
    public void httpPopulate_returnPopulated(){
        Assertions.assertEquals(this.restTemplate.getForObject("http://localhost:" + port + "/adventure/populate", String.class),"Mock adventures populated \n");
    }

    @Test
    @Order(4)
    @Description("Ensure controller can retrieve all existing adventures")
    public void httpGetAllAdventures_returnAllAdventures(){
        Adventure[] adventures = this.restTemplate.getForEntity("http://localhost:" + port + "/adventure/all", Adventure[].class).getBody();
        Assertions.assertEquals(adventures.length, 3);
    }

    @Test
    @Order(5)
    @Description("Ensure controller returns adventures when provided a valid owner of adventures")
    public void httpGetExistingOwnerAdventures_ReturnAdventures(){
        Adventure[] adventures = this.restTemplate.getForEntity("http://localhost:" + port + "/adventure/owner/1660bd85-1c13-42c0-955c-63b1eda4e90b", Adventure[].class).getBody();
        Assertions.assertEquals(adventures.length, 2);
    }



    @Test
    @Order(6)
    @Description("Ensure controller returns adventures when provided a valid attendee of adventures")
    public void httpGetExistingAttendeeAdventures_ReturnAdventures(){
        Adventure[] adventures = this.restTemplate.getForEntity("http://localhost:" + port + "/adventure/attendee/7a984756-16a5-422e-a377-89e1772dd71e", Adventure[].class).getBody();
        Assertions.assertEquals(adventures.length, 2);
    }

    @Test
    @Order(7)
    @Description("Ensure controller can create a new adventure")
    public void httpCreateAdventure_ReturnCreatedAdventure(){

        CreateAdventureRequest req = new CreateAdventureRequest("Test Adventure","Test Adventure Description", mockAdventureID, mockUserID, LocalDate.of(2021, 1, 1),LocalDate.of(2021, 1, 1));
        CreateAdventureResponse res = this.restTemplate.postForEntity("http://localhost:" + port + "/adventure/create", req, CreateAdventureResponse.class).getBody();
        Assertions.assertEquals(mockAdventureID, res.getAdventure().getAdventureId());
        Assertions.assertEquals(mockUserID, res.getAdventure().getOwnerId());
    }

    @Test
    @Order(8)
    @Description("Ensure controller can successfully remove an existing adventure")
    public void httpRemoveValidAdventure_ReturnSuccessfulRemoval(){
        this.restTemplate.delete("http://localhost:" + port + "/adventure/remove/"+mockAdventureID);
        Adventure[] adventures = this.restTemplate.getForEntity("http://localhost:" + port + "/adventure/all", Adventure[].class).getBody();
        Assertions.assertEquals(4, adventures.length);
    }
}
