package com.adventureit.itinerary;

import com.adventureit.itinerary.Controller.ItineraryController;
import com.adventureit.itinerary.Repository.ItineraryRepository;
import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItineraryServiceIntegrationTests {
    @Autowired
    private ItineraryController itineraryController;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Description("Ensure that the Itinerary Controller loads")
    public void itineraryControllerLoads() throws Exception {
        Assertions.assertNotNull(itineraryController);
    }

    @Test
    @Description("Ensure that the controller is accepting traffic and responding")
    public void httpTest_returnResponse(){
        Assertions.assertEquals(this.restTemplate.getForObject("http://localhost:" + port + "/itinerary/test", String.class),"Itinerary Controller is functional");
    }
}
