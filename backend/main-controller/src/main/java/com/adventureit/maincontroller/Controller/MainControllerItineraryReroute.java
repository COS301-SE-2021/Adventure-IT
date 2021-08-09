package com.adventureit.maincontroller.Controller;


import com.adventureit.adventureservice.Requests.CreateAdventureRequest;
import com.adventureit.adventureservice.Responses.CreateAdventureResponse;
import com.adventureit.itinerary.Requests.AddItineraryEntryRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@RestController
@RequestMapping("/itinerary")
public class MainControllerItineraryReroute {

    private RestTemplate restTemplate = new RestTemplate();

    private final String IP = "localhost";
    private final String userPort = "9002";
    private final String locationPort = "9006";
    private final String itineraryPort = "9009";


    @GetMapping("/test")
    public String itineraryTest(){
        return restTemplate.getForObject("http://"+ IP + ":" + itineraryPort + "/itinerary/test", String.class);
    }

    @PostMapping(value = "/addEntry")
    public UUID addItineraryEntry(@RequestBody AddItineraryEntryRequest req) {
        UUID locationId = restTemplate.getForObject("http://"+ IP + ":" + locationPort + "/location/create/"+req.getLocation(),UUID.class);

        UUID itineraryID = restTemplate.postForObject("http://"+ IP + ":" + itineraryPort + "/itinerary/addEntry",req, UUID.class);

        restTemplate.getForObject("http://"+ IP + ":" + itineraryPort + "/itinerary/setLocation/" + itineraryID +"/"+ locationId ,String.class);

        return itineraryID;
    }
}
