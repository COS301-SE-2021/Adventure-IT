package com.adventureit.maincontroller.Controller;


import com.adventureit.adventureservice.Requests.CreateAdventureRequest;
import com.adventureit.adventureservice.Responses.CreateAdventureResponse;
import com.adventureit.itinerary.Entity.Itinerary;
import com.adventureit.itinerary.Requests.AddItineraryEntryRequest;
import com.adventureit.itinerary.Requests.CreateItineraryRequest;
import com.adventureit.itinerary.Requests.EditItineraryEntryRequest;
import com.adventureit.itinerary.Responses.ItineraryEntryResponseDTO;
import com.adventureit.itinerary.Responses.ItineraryResponseDTO;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
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


    @GetMapping("/viewItinerariesByAdventure/{id}")
    public List<ItineraryResponseDTO> viewItinerariesByAdventure(@PathVariable UUID id){
        return restTemplate.getForObject("http://"+ IP + ":" + itineraryPort + "/itinerary/viewItinerariesByAdventure/"+id, List.class);
    }

    @GetMapping("/viewItinerary/{id}")
    public List<ItineraryEntryResponseDTO> viewItinerary(@PathVariable UUID id){
        return restTemplate.getForObject("http://"+ IP + ":" + itineraryPort + "/itinerary/viewItinerary/"+id, List.class);
    }

    @GetMapping("/softDelete/{id}/{userID}")
    public String softDelete(@PathVariable UUID id,@PathVariable UUID userID){
        return restTemplate.getForObject("http://"+ IP + ":" + itineraryPort + "/itinerary/softDelete/"+id+"/"+userID, String.class);
    }
    //
    @GetMapping("/viewTrash/{id}")
    public List<ItineraryResponseDTO> viewTrash(@PathVariable UUID id){
        return restTemplate.getForObject("http://"+ IP + ":" + itineraryPort + "/itinerary/viewTrash/"+id, List.class);
    }

    @GetMapping("/restoreItinerary/{id}/{userID}")
    public String restoreItinerary(@PathVariable UUID id,@PathVariable UUID userID){
        return restTemplate.getForObject("http://"+ IP + ":" + itineraryPort + "/itinerary/restoreItinerary/"+id, String.class);
    }

    @GetMapping("/hardDelete/{id}/{userID}")
    public String hardDelete(@PathVariable UUID id, @PathVariable UUID userID){
        return restTemplate.getForObject("http://"+ IP + ":" + itineraryPort + "/itinerary/hardDelete/"+id+"/"+userID, String.class);
    }

    @PostMapping("/create")
    public String createItinerary(@RequestBody CreateItineraryRequest req){
        return restTemplate.postForObject("http://"+ IP + ":" + itineraryPort + "/itinerary/create/", req, String.class);
    }


    @PostMapping("/editEntry")
    public String editItineraryEntry(@RequestBody EditItineraryEntryRequest req){
        return restTemplate.postForObject("http://"+ IP + ":" + itineraryPort + "/itinerary/editEntry/", req, String.class);
    }

    @GetMapping("/removeEntry/{id}")
    public String removeItineraryEntry(@PathVariable UUID id){
        return restTemplate.getForObject("http://"+ IP + ":" + itineraryPort + "/itinerary/removeEntry/"+id, String.class);
    }

    @GetMapping("/markEntry/{id}")
    public void markItineraryEntry(@PathVariable UUID id){
        restTemplate.getForObject("http://"+ IP + ":" + itineraryPort + "/itinerary/markEntry/"+id, String.class);
    }

    @GetMapping("/getNextEntry/{id}")
    public ItineraryEntryResponseDTO getNextEntry(@PathVariable UUID id){
        return restTemplate.getForObject("http://"+ IP + ":" + itineraryPort + "/itinerary/getNextEntry/"+id, ItineraryEntryResponseDTO.class);
    }

    @GetMapping("/setLocation/{itineraryId}/{locationID}")
    public void setLocation(@PathVariable UUID itineraryId,@PathVariable UUID locationID){
        restTemplate.getForObject("http://"+ IP + ":" + itineraryPort + "/itinerary/setLocation/"+itineraryId+"/"+locationID, String.class);
    }
}
