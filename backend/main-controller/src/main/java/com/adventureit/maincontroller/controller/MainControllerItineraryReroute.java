package com.adventureit.maincontroller.controller;


import com.adventureit.itinerary.requests.AddItineraryEntryRequest;
import com.adventureit.itinerary.requests.CreateItineraryRequest;
import com.adventureit.itinerary.requests.EditItineraryEntryRequest;
import com.adventureit.itinerary.responses.ItineraryEntryResponseDTO;
import com.adventureit.itinerary.responses.ItineraryResponseDTO;
import com.adventureit.locationservice.responses.LocationResponseDTO;
import com.adventureit.maincontroller.responses.MainItineraryEntryResponseDTO;
import com.adventureit.timelineservice.entity.TimelineType;
import com.adventureit.timelineservice.requests.CreateTimelineRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/itinerary")
public class MainControllerItineraryReroute {

    private final RestTemplate restTemplate = new RestTemplate();

    private final String IP = "http://localhost";
    private final String locationPort = "9006";
    private final String itineraryPort = "9009";
    private final String timelinePort = "9012";

    @GetMapping("/test")
    public String itineraryTest(){
        return restTemplate.getForObject(IP + ":" + itineraryPort + "/itinerary/test", String.class);
    }

    @PostMapping(value = "/addEntry")
    public UUID addItineraryEntry(@RequestBody AddItineraryEntryRequest req) {
        UUID locationId = restTemplate.getForObject(IP + ":" + locationPort + "/location/create/"+req.getLocation(),UUID.class);
        UUID itineraryID = restTemplate.postForObject(IP + ":" + itineraryPort + "/itinerary/addEntry",req, UUID.class);
        restTemplate.getForObject(IP + ":" + itineraryPort + "/itinerary/setLocation/" + itineraryID +"/"+ locationId ,String.class);

        ItineraryResponseDTO itinerary = restTemplate.getForObject(IP + ":" + itineraryPort + "/itinerary/getItineraryById/"+req.getEntryContainerID(), ItineraryResponseDTO.class);
        assert itinerary != null;
        UUID adventureId = itinerary.getAdventureID();
        CreateTimelineRequest req2 = new CreateTimelineRequest(adventureId, TimelineType.BUDGET,"Itinerary "+req.getTitle()+": has been updated" );
        restTemplate.postForObject(IP + ":" + timelinePort + "/timeline/createTimeline", req2, String.class);
        return itineraryID;
    }


    @GetMapping("/viewItinerariesByAdventure/{id}")
    public List<ItineraryResponseDTO> viewItinerariesByAdventure(@PathVariable UUID id){
        return restTemplate.getForObject(IP + ":" + itineraryPort + "/itinerary/viewItinerariesByAdventure/"+id, List.class);
    }

    @GetMapping("/viewItinerary/{id}")
    public List<MainItineraryEntryResponseDTO> viewItinerary(@PathVariable UUID id){
        List<LinkedHashMap<String,String>> entries = restTemplate.getForObject(IP + ":" + itineraryPort + "/itinerary/viewItinerary/"+id, List.class);
        List<MainItineraryEntryResponseDTO> list = new ArrayList<>();


        assert entries != null;
        for (LinkedHashMap<String,String> entry :
                entries) {
            try {
                LocationResponseDTO itineraryLocation = restTemplate.getForObject(IP+":9006/location/getLocation/"+entry.get("location"), LocationResponseDTO.class);
                MainItineraryEntryResponseDTO responseObject = new MainItineraryEntryResponseDTO(entry.get("title"), entry.get("description"), UUID.fromString(entry.get("id")), UUID.fromString(entry.get("entryContainerID")), Boolean.parseBoolean(entry.get("completed")),itineraryLocation, LocalDateTime.parse(entry.get("timestamp")));

               list.add(responseObject);
            }
            catch(Exception e){
                e.printStackTrace();
            }

        }

        return list;
    }

    @GetMapping("/softDelete/{id}/{userID}")
    public String softDelete(@PathVariable UUID id,@PathVariable UUID userID){
        return restTemplate.getForObject(IP + ":" + itineraryPort + "/itinerary/softDelete/"+id+"/"+userID, String.class);
    }
    //
    @GetMapping("/viewTrash/{id}")
    public List<ItineraryResponseDTO> viewTrash(@PathVariable UUID id){
        return restTemplate.getForObject( IP + ":" + itineraryPort + "/itinerary/viewTrash/"+id, List.class);
    }

    @GetMapping("/restoreItinerary/{id}/{userID}")
    public String restoreItinerary(@PathVariable UUID id,@PathVariable UUID userID){
        return restTemplate.getForObject(IP + ":" + itineraryPort + "/itinerary/restoreItinerary/"+id+"/"+userID, String.class);
    }

    @GetMapping("/hardDelete/{id}/{userID}")
    public String hardDelete(@PathVariable UUID id, @PathVariable UUID userID){
        ItineraryResponseDTO response = restTemplate.getForObject(IP + ":" + itineraryPort + "/itinerary/getItineraryById/"+id, ItineraryResponseDTO.class);
        String returnString = restTemplate.getForObject(IP + ":" + itineraryPort + "/itinerary/hardDelete/"+id+"/"+userID, String.class);
        assert response != null;
        CreateTimelineRequest req2 = new CreateTimelineRequest(response.getAdventureID(), TimelineType.ITINERARY,"Itinerary: "+response.getTitle()+" has been deleted" );
        restTemplate.postForObject( IP + ":" + timelinePort + "/timeline/createTimeline", req2, String.class);
        return returnString;
    }

    @PostMapping("/create")
    public String createItinerary(@RequestBody CreateItineraryRequest req){
        String returnString = restTemplate.postForObject(IP + ":" + itineraryPort + "/itinerary/create/", req, String.class);
        CreateTimelineRequest req2 = new CreateTimelineRequest(req.getAdvID(), TimelineType.BUDGET,"Itinerary: "+req.getTitle()+" has been created" );
        restTemplate.postForObject(IP + ":" + timelinePort + "/timeline/createTimeline", req2, String.class);
        return returnString;

    }


    @PostMapping("/editEntry")
    public String editItineraryEntry(@RequestBody EditItineraryEntryRequest req){
        UUID locationId = restTemplate.getForObject(IP + ":" + locationPort + "/location/create/"+req.getLocation(),UUID.class);
        req.setLocationId(locationId);
        String returnString = restTemplate.postForObject(IP + ":" + itineraryPort + "/itinerary/editEntry/", req, String.class);
        ItineraryResponseDTO itinerary = restTemplate.getForObject(IP + ":" + itineraryPort + "/itinerary/getItineraryById/"+req.getEntryContainerID(), ItineraryResponseDTO.class);
        assert itinerary != null;
        UUID adventureId = itinerary.getAdventureID();
        CreateTimelineRequest req2 = new CreateTimelineRequest(adventureId, TimelineType.ITINERARY,"Itinerary: "+req.getTitle()+" has been edited" );
        restTemplate.postForObject(IP + ":" + timelinePort + "/timeline/createTimeline", req2, String.class);
        return returnString;
    }

    @GetMapping("/removeEntry/{id}")
    public String removeItineraryEntry(@PathVariable UUID id){
        return restTemplate.getForObject(IP + ":" + itineraryPort + "/itinerary/removeEntry/"+id, String.class);

    }

    @GetMapping("/markEntry/{id}")
    public void markItineraryEntry(@PathVariable UUID id){
        restTemplate.getForObject(IP + ":" + itineraryPort + "/itinerary/markEntry/"+id, String.class);
    }

    @GetMapping("/getNextEntry/{id}")
    public MainItineraryEntryResponseDTO getNextEntry(@PathVariable UUID id){
        ItineraryEntryResponseDTO entry = restTemplate.getForObject(IP + ":" + itineraryPort + "/itinerary/getNextEntry/"+id, ItineraryEntryResponseDTO.class);
        assert entry != null;
        LocationResponseDTO location = restTemplate.getForObject(IP + ":" + locationPort + "/location/getLocation/" + entry.getLocation(), LocationResponseDTO.class);
        return new MainItineraryEntryResponseDTO(entry.getTitle(),entry.getDescription(),entry.getId(),entry.getEntryContainerID(),entry.isCompleted(),location,entry.getTimestamp());
    }

    @GetMapping("/setLocation/{itineraryId}/{locationID}")
    public void setLocation(@PathVariable UUID itineraryId,@PathVariable UUID locationID){
        restTemplate.getForObject(IP + ":" + itineraryPort + "/itinerary/setLocation/"+itineraryId+"/"+locationID, String.class);
    }
}