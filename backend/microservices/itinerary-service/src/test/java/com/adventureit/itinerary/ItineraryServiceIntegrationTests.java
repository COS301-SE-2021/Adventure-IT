package com.adventureit.itinerary;

import com.adventureit.itinerary.Controller.ItineraryController;
import com.adventureit.itinerary.Entity.Itinerary;
import com.adventureit.itinerary.Entity.ItineraryEntry;
import com.adventureit.itinerary.Repository.ItineraryEntryRepository;
import com.adventureit.itinerary.Repository.ItineraryRepository;
import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ItineraryServiceIntegrationTests {
    @Autowired
    private ItineraryController itineraryController;

    @Autowired
    ItineraryRepository itineraryRepository;

    @Autowired
    ItineraryEntryRepository itineraryEntryRepository;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Description("Ensure that the Itinerary Controller loads")
    void itineraryControllerLoads() throws Exception {
        Assertions.assertNotNull(itineraryController);
    }

    @Test
    @Description("Ensure that the controller is accepting traffic and responding")
    void httpTest_returnResponse(){
        Assertions.assertEquals("Itinerary Controller is functional", this.restTemplate.getForObject("http://localhost:" + port + "/itinerary/test", String.class));
    }

    @Test
    @Description("Ensure that the view function works")
    void httpViewByAdventure_returnResponse(){
        UUID id = UUID.randomUUID();
        UUID adventureID = UUID.randomUUID();
        Itinerary Itinerary = new Itinerary("Test Itinerary","Mock",id,UUID.randomUUID(),adventureID);
        itineraryRepository.saveAndFlush(Itinerary);
        this.restTemplate.getForObject("http://localhost:" + port + "/itinerary/viewItinerariesByAdventure/{id}", String.class, id);
    }

    @Test
    @Description("Ensure that the softDelete function works")
    void httpSoftDelete_returnResponse(){
        UUID id = UUID.randomUUID();
        UUID adventureID = UUID.randomUUID();
        UUID ownerID = UUID.randomUUID();
        Itinerary itinerary = new Itinerary("Test Itinerary","Mock",id,adventureID,ownerID);
        itineraryRepository.saveAndFlush(itinerary);
        Assertions.assertEquals("Itinerary moved to bin", this.restTemplate.getForObject("http://localhost:" + port + "/itinerary/softDelete/{id}/{userID}", String.class, id,ownerID));
    }

    @Test
    @Description("Ensure that the hardDelete function works")
    void httpHardDelete_returnResponse(){
        UUID id = UUID.randomUUID();
        UUID adventureID = UUID.randomUUID();
        UUID ownerID = UUID.randomUUID();
        Itinerary itinerary = new Itinerary("Test Itinerary","Mock",id,adventureID,ownerID);
        itinerary.setDeleted(true);
        itineraryRepository.saveAndFlush(itinerary);
        Assertions.assertEquals("Itinerary deleted", this.restTemplate.getForObject("http://localhost:" + port + "/itinerary/hardDelete/{id}/{userID}", String.class, id,ownerID));
    }

    @Test
    @Description("Ensure that the viewTrash function works")
    void httpViewTrash_returnResponse(){
        UUID id = UUID.randomUUID();
        UUID adventureID = UUID.randomUUID();
        UUID ownerID = UUID.randomUUID();
        Itinerary itinerary = new Itinerary("Test Itinerary","Mock",id,adventureID,ownerID);
        itinerary.setDeleted(true);
        itineraryRepository.saveAndFlush(itinerary);
        this.restTemplate.getForObject("http://localhost:" + port + "/itinerary/viewTrash/{id}", List.class, adventureID);
    }

    @Test
    @Description("Ensure that the restoreItinerary function works")
    void httpRestoreItinerary_returnResponse(){
        UUID id = UUID.randomUUID();
        UUID adventureID = UUID.randomUUID();
        UUID ownerID = UUID.randomUUID();
        Itinerary itinerary = new Itinerary("Test Itinerary","Mock",id,adventureID,ownerID);
        itinerary.setDeleted(true);
        itineraryRepository.saveAndFlush(itinerary);
        Assertions.assertEquals("Itinerary was restored", this.restTemplate.getForObject("http://localhost:" + port + "/itinerary/restoreItinerary/{id}/{userID}", String.class, id,ownerID));
    }

    @Test
    @Description("Ensure that the removeEntry function works")
    void httpRemoveEntry_returnResponse(){
        UUID id = UUID.randomUUID();
        UUID itineraryID = UUID.randomUUID();
        ItineraryEntry entry = new ItineraryEntry("Mock", "Mock",id,itineraryID, UUID.randomUUID(), LocalDateTime.now());
        itineraryEntryRepository.saveAndFlush(entry);
        Assertions.assertEquals("Itinerary Entry successfully removed", this.restTemplate.getForObject("http://localhost:" + port + "/itinerary/removeEntry/{id}", String.class, id));
    }

    @Test
    @Description("Ensure that the getItineraryByID function works")
    void httpGetItineraryByID_returnResponse(){
        UUID id = UUID.randomUUID();
        UUID adventureID = UUID.randomUUID();
        UUID ownerID = UUID.randomUUID();
        Itinerary itinerary = new Itinerary("Test Itinerary","Mock",id,adventureID,ownerID);
        itineraryRepository.saveAndFlush(itinerary);
        this.restTemplate.getForObject("http://localhost:" + port + "/itinerary/getItinerary/{id}", String.class, id);
    }
}
