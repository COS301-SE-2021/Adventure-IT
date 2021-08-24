package com.adventureit.maincontroller.Controller;


import com.adventureit.itinerary.requests.AddItineraryEntryRequest;
import com.adventureit.itinerary.requests.CreateItineraryRequest;
import com.adventureit.itinerary.requests.EditItineraryEntryRequest;
import com.adventureit.itinerary.responses.ItineraryEntryResponseDTO;
import com.adventureit.itinerary.responses.ItineraryResponseDTO;
import com.adventureit.locationservice.responses.LocationResponseDTO;
import com.adventureit.maincontroller.Responses.MainItineraryEntryResponseDTO;
import com.adventureit.timelineservice.Entity.TimelineType;
import com.adventureit.timelineservice.Requests.CreateTimelineRequest;
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

    private RestTemplate restTemplate = new RestTemplate();

    private final String IP = "localhost";
    private final String userPort = "9002";
    private final String locationPort = "9006";
    private final String itineraryPort = "9009";
    private final String timelinePort = "9012";

    @GetMapping("/test")
    public String itineraryTest(){
        return restTemplate.getForObject("http://"+ IP + ":" + itineraryPort + "/itinerary/test", String.class);
    }

    @PostMapping(value = "/addEntry")
    public UUID addItineraryEntry(@RequestBody AddItineraryEntryRequest req) {
        UUID locationId = restTemplate.getForObject("http://"+ IP + ":" + locationPort + "/location/create/"+req.getLocation(),UUID.class);
        UUID itineraryID = restTemplate.postForObject("http://"+ IP + ":" + itineraryPort + "/itinerary/addEntry",req, UUID.class);
        restTemplate.getForObject("http://"+ IP + ":" + itineraryPort + "/itinerary/setLocation/" + itineraryID +"/"+ locationId ,String.class);

        UUID adventureId = restTemplate.getForObject("http://"+ IP + ":" + itineraryPort + "/itinerary/getItineraryById/"+req.getEntryContainerID(), ItineraryResponseDTO.class).getAdventureID();
        CreateTimelineRequest req2 = new CreateTimelineRequest(adventureId, TimelineType.BUDGET,"Itinerary: "+req.getTitle()+" has been updated" );
        restTemplate.postForObject("http://"+ IP + ":" + timelinePort + "/timeline/createTimeline", req2, String.class);
        return itineraryID;
    }


    @GetMapping("/viewItinerariesByAdventure/{id}")
    public List<ItineraryResponseDTO> viewItinerariesByAdventure(@PathVariable UUID id){
        return restTemplate.getForObject("http://"+ IP + ":" + itineraryPort + "/itinerary/viewItinerariesByAdventure/"+id, List.class);
    }

    @GetMapping("/viewItinerary/{id}")
    public List<MainItineraryEntryResponseDTO> viewItinerary(@PathVariable UUID id){
        List<LinkedHashMap> entries = restTemplate.getForObject("http://"+ IP + ":" + itineraryPort + "/itinerary/viewItinerary/"+id, List.class);
        List<MainItineraryEntryResponseDTO> list = new ArrayList<>();
        LocationResponseDTO location;

        for (LinkedHashMap entry :
                entries) {
            try {
                LocationResponseDTO itineraryLocation = restTemplate.getForObject("http://localhost:9006/location/getLocation/"+(String)entry.get("location"), LocationResponseDTO.class);
                MainItineraryEntryResponseDTO responseObject = new MainItineraryEntryResponseDTO((String)entry.get("title"), (String)entry.get("description"), UUID.fromString((String)entry.get("id")), UUID.fromString((String)entry.get("entryContainerID")), (Boolean)entry.get("completed"),itineraryLocation, LocalDateTime.parse((String)entry.get("timestamp")));

               list.add(responseObject);
            }
            catch(Exception e){
                e.printStackTrace();
            }

        }

//        for (ItineraryEntryResponseDTO entry:entries) {
//            location = restTemplate.getForObject("http://"+ IP + ":" + locationPort + "/location/getLocation/" + entry.getLocation(), LocationResponseDTO.class);
//            list.add(new MainItineraryEntryResponseDTO(entry.getTitle(),entry.getDescription(),entry.getId(),entry.getEntryContainerID(),entry.isCompleted(),location,entry.getTimestamp()));
//        }

        return list;
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
        return restTemplate.getForObject("http://"+ IP + ":" + itineraryPort + "/itinerary/restoreItinerary/"+id+"/"+userID, String.class);
    }

    @GetMapping("/hardDelete/{id}/{userID}")
    public String hardDelete(@PathVariable UUID id, @PathVariable UUID userID){
        ItineraryResponseDTO response = restTemplate.getForObject("http://"+ IP + ":" + itineraryPort + "/itinerary/getItineraryById/"+id, ItineraryResponseDTO.class);
        String returnString = restTemplate.getForObject("http://"+ IP + ":" + itineraryPort + "/itinerary/hardDelete/"+id+"/"+userID, String.class);
        CreateTimelineRequest req2 = new CreateTimelineRequest(response.getAdventureID(), TimelineType.ITINERARY,"Itinerary: "+response.getTitle()+" has been deleted" );
        restTemplate.postForObject("http://"+ IP + ":" + timelinePort + "/timeline/createTimeline", req2, String.class);
        return returnString;
    }

    @PostMapping("/create")
    public String createItinerary(@RequestBody CreateItineraryRequest req){
        String returnString = restTemplate.postForObject("http://"+ IP + ":" + itineraryPort + "/itinerary/create/", req, String.class);
        CreateTimelineRequest req2 = new CreateTimelineRequest(req.getAdvID(), TimelineType.BUDGET,"Itinerary: "+req.getTitle()+" has been created" );
        restTemplate.postForObject("http://"+ IP + ":" + timelinePort + "/timeline/createTimeline", req2, String.class);
        return returnString;

    }


    @PostMapping("/editEntry")
    public String editItineraryEntry(@RequestBody EditItineraryEntryRequest req){
        UUID locationId = restTemplate.getForObject("http://"+ IP + ":" + locationPort + "/location/create/"+req.getLocation(),UUID.class);
        req.setLocationId(locationId);
        String returnString = restTemplate.postForObject("http://"+ IP + ":" + itineraryPort + "/itinerary/editEntry/", req, String.class);
        UUID adventureId = restTemplate.getForObject("http://"+ IP + ":" + itineraryPort + "/itinerary/getItineraryById/"+req.getEntryContainerID(), ItineraryResponseDTO.class).getAdventureID();





        CreateTimelineRequest req2 = new CreateTimelineRequest(adventureId, TimelineType.ITINERARY,"Itinerary: "+req.getTitle()+" has been edited" );
        restTemplate.postForObject("http://"+ IP + ":" + timelinePort + "/timeline/createTimeline", req2, String.class);
        return returnString;
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
    public MainItineraryEntryResponseDTO getNextEntry(@PathVariable UUID id){
        ItineraryEntryResponseDTO entry = restTemplate.getForObject("http://"+ IP + ":" + itineraryPort + "/itinerary/getNextEntry/"+id, ItineraryEntryResponseDTO.class);
        LocationResponseDTO location = restTemplate.getForObject("http://"+ IP + ":" + locationPort + "/location/getLocation/" + entry.getLocation(), LocationResponseDTO.class);
        return new MainItineraryEntryResponseDTO(entry.getTitle(),entry.getDescription(),entry.getId(),entry.getEntryContainerID(),entry.isCompleted(),location,entry.getTimestamp());
    }

    @GetMapping("/setLocation/{itineraryId}/{locationID}")
    public void setLocation(@PathVariable UUID itineraryId,@PathVariable UUID locationID){
        restTemplate.getForObject("http://"+ IP + ":" + itineraryPort + "/itinerary/setLocation/"+itineraryId+"/"+locationID, String.class);
    }
}
