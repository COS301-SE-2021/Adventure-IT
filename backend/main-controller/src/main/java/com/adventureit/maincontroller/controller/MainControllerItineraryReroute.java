package com.adventureit.maincontroller.controller;


import com.adventureit.maincontroller.responses.RegisteredUsersDTO;
import com.adventureit.maincontroller.service.MainControllerServiceImplementation;
import com.adventureit.shareddtos.adventure.AdventureDTO;
import com.adventureit.shareddtos.adventure.responses.GetAdventureByUUIDResponse;
import com.adventureit.shareddtos.itinerary.requests.AddItineraryEntryRequest;
import com.adventureit.shareddtos.itinerary.requests.CreateItineraryRequest;
import com.adventureit.shareddtos.itinerary.requests.EditItineraryEntryRequest;
import com.adventureit.shareddtos.itinerary.responses.ItineraryEntryResponseDTO;
import com.adventureit.shareddtos.itinerary.responses.ItineraryResponseDTO;
import com.adventureit.shareddtos.itinerary.responses.StartDateEndDateResponseDTO;
import com.adventureit.shareddtos.location.responses.LocationResponseDTO;
import com.adventureit.maincontroller.exceptions.CurrentLocationException;
import com.adventureit.maincontroller.exceptions.InvalidItineraryEntryException;
import com.adventureit.maincontroller.responses.MainItineraryEntryResponseDTO;
import com.adventureit.shareddtos.recommendation.request.CreateLocationRequest;
import com.adventureit.shareddtos.timeline.TimelineType;
import com.adventureit.shareddtos.timeline.requests.CreateTimelineRequest;
import com.adventureit.shareddtos.user.responses.GetUserByUUIDDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/itinerary")
public class MainControllerItineraryReroute {

    private final RestTemplate restTemplate = new RestTemplate();
    private MainControllerServiceImplementation service;

    private final String IP = "http://localhost";
    private final String locationPort = "9006";
    private final String itineraryPort = "9009";
    private final String timelinePort = "9012";
    private final String adventurePort = "9001";
    private final String userPort = "9002";
    private final String recommendationPort = "9013";

    @Autowired
    public MainControllerItineraryReroute(MainControllerServiceImplementation service) {
        this.service = service;
    }

    @GetMapping("/test")
    public String itineraryTest(){
        return restTemplate.getForObject(IP + ":" + itineraryPort + "/itinerary/test", String.class);
    }

    @PostMapping(value = "/addEntry")
    public UUID addItineraryEntry(@RequestBody AddItineraryEntryRequest req) throws Exception {
        String[] ports = {itineraryPort,userPort,adventurePort,timelinePort};
        service.pingCheck(ports,restTemplate);

        GetUserByUUIDDTO user = restTemplate.getForObject(IP + ":" + userPort + "/user/getUser/"+req.getUserId(), GetUserByUUIDDTO.class);
        ItineraryResponseDTO itinerary = restTemplate.getForObject(IP + ":" + itineraryPort + "/itinerary/getItineraryById/"+req.getEntryContainerID(), ItineraryResponseDTO.class);
        assert itinerary != null;
        UUID adventureId = itinerary.getAdventureID();
        AdventureDTO adventureResponse = restTemplate.getForObject(IP + ":" + adventurePort + "/adventure/getAdventureByUUID/" + adventureId, GetAdventureByUUIDResponse.class).getAdventure();
        LocalDateTime timestamp = LocalDateTime.parse(req.getTimestamp());
        if((timestamp.toLocalDate().compareTo(adventureResponse.getEndDate()) > 0) || (timestamp.toLocalDate().compareTo(adventureResponse.getStartDate()) < 0)){
            throw new InvalidItineraryEntryException("Itinerary Entry does not fit within Adventure");
        }

        UUID locationId = restTemplate.getForObject(IP + ":" + locationPort + "/location/create/" + req.getLocation(), UUID.class);

        CreateLocationRequest req3 = new CreateLocationRequest(locationId);
        String code=restTemplate.postForObject(IP + ":" + recommendationPort + "/recommendation/add/location", req3, String.class);

        UUID itineraryID = restTemplate.postForObject(IP + ":" + itineraryPort + "/itinerary/addEntry", req, UUID.class);
        restTemplate.getForObject(IP + ":" + itineraryPort + "/itinerary/setLocation/" + itineraryID + "/" + locationId, String.class);

        CreateTimelineRequest req2 = new CreateTimelineRequest(adventureId, TimelineType.ITINERARY, user.getUsername() + " added an entry to the " + req.getTitle() + " itinerary.");
        restTemplate.postForObject(IP + ":" + timelinePort + "/timeline/createTimeline", req2, String.class);
        return itineraryID;
    }

    @GetMapping("/viewItinerariesByAdventure/{id}")
    public List<ItineraryResponseDTO> viewItinerariesByAdventure(@PathVariable UUID id) throws Exception {
        String[] ports = {itineraryPort};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(IP + ":" + itineraryPort + "/itinerary/viewItinerariesByAdventure/"+id, List.class);
    }

    @GetMapping("/viewItinerary/{id}")
    public List<MainItineraryEntryResponseDTO> viewItinerary(@PathVariable UUID id) throws Exception {
        String[] ports = {itineraryPort,locationPort};
        service.pingCheck(ports,restTemplate);
        List<LinkedHashMap<String,String>> entries = restTemplate.getForObject(IP + ":" + itineraryPort + "/itinerary/viewItinerary/"+id, List.class);
        List<MainItineraryEntryResponseDTO> list = new ArrayList<>();

        assert entries != null;
        for (LinkedHashMap entry :
                entries) {
            try {
                LocationResponseDTO itineraryLocation = restTemplate.getForObject(IP+":"+locationPort+"/location/getLocation/"+entry.get("location"), LocationResponseDTO.class);
                MainItineraryEntryResponseDTO responseObject = new MainItineraryEntryResponseDTO((String)entry.get("title"), (String)entry.get("description"), UUID.fromString((String)entry.get("id")), UUID.fromString((String)entry.get("entryContainerID")),(boolean)entry.get("completed"),itineraryLocation, LocalDateTime.parse((String)entry.get("timestamp")),(Map<UUID,Boolean>)entry.get("registeredUsers"));

                list.add(responseObject);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return list;
    }

    @GetMapping("/softDelete/{id}/{userID}")
    public String softDelete(@PathVariable UUID id,@PathVariable UUID userID) throws Exception {
        String[] ports = {itineraryPort};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(IP + ":" + itineraryPort + "/itinerary/softDelete/"+id+"/"+userID, String.class);
    }

    @GetMapping("/viewTrash/{id}")
    public List<ItineraryResponseDTO> viewTrash(@PathVariable UUID id) throws Exception {
        String[] ports = {itineraryPort};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject( IP + ":" + itineraryPort + "/itinerary/viewTrash/"+id, List.class);
    }

    @GetMapping("/restoreItinerary/{id}/{userID}")
    public String restoreItinerary(@PathVariable UUID id,@PathVariable UUID userID) throws Exception {
        String[] ports = {itineraryPort};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(IP + ":" + itineraryPort + "/itinerary/restoreItinerary/"+id+"/"+userID, String.class);
    }

    @GetMapping("/hardDelete/{id}/{userID}")
    public String hardDelete(@PathVariable UUID id, @PathVariable UUID userID) throws Exception {
        String[] ports = {itineraryPort,userPort,timelinePort};
        service.pingCheck(ports,restTemplate);
        GetUserByUUIDDTO user = restTemplate.getForObject(IP + ":" + userPort + "/user/getUser/"+userID, GetUserByUUIDDTO.class);

        ItineraryResponseDTO response = restTemplate.getForObject(IP + ":" + itineraryPort + "/itinerary/getItineraryById/" + id, ItineraryResponseDTO.class);
        String returnString = restTemplate.getForObject(IP + ":" + itineraryPort + "/itinerary/hardDelete/" + id + "/" + userID, String.class);
        assert response != null;
        CreateTimelineRequest req2 = new CreateTimelineRequest(response.getAdventureID(), TimelineType.ITINERARY, user.getUsername() + " deleted the " + response.getTitle() + " itinerary.");
        restTemplate.postForObject(IP + ":" + timelinePort + "/timeline/createTimeline", req2, String.class);
        return returnString;
    }

    @PostMapping("/create")
    public String createItinerary(@RequestBody CreateItineraryRequest req) throws Exception {
        String[] ports = {itineraryPort,userPort,timelinePort};
        service.pingCheck(ports,restTemplate);
        GetUserByUUIDDTO user = restTemplate.getForObject(IP + ":" + userPort + "/user/getUser/"+req.getUserID(), GetUserByUUIDDTO.class);
        String returnString = restTemplate.postForObject(IP + ":" + itineraryPort + "/itinerary/create/", req, String.class);
        CreateTimelineRequest req2 = new CreateTimelineRequest(req.getAdvID(), TimelineType.ITINERARY, user.getUsername() + " created a new Itinerary for " + req.getTitle() + ".");
        restTemplate.postForObject(IP + ":" + timelinePort + "/timeline/createTimeline", req2, String.class);
        return returnString;

    }


    @PostMapping("/editEntry")
    public String editItineraryEntry(@RequestBody EditItineraryEntryRequest req) throws Exception {
        String[] ports = {itineraryPort,userPort,timelinePort,locationPort};
        service.pingCheck(ports,restTemplate);
        UUID locationId = restTemplate.getForObject(IP + ":" + locationPort + "/location/create/"+req.getLocation(),UUID.class);
        GetUserByUUIDDTO user = restTemplate.getForObject(IP + ":" + userPort + "/user/getUser/"+req.getUserId(), GetUserByUUIDDTO.class);
        req.setLocationId(locationId);
        String returnString = restTemplate.postForObject(IP + ":" + itineraryPort + "/itinerary/editEntry/", req, String.class);
        ItineraryResponseDTO itinerary = restTemplate.getForObject(IP + ":" + itineraryPort + "/itinerary/getItineraryById/" + req.getEntryContainerID(), ItineraryResponseDTO.class);
        assert itinerary != null;
        UUID adventureId = itinerary.getAdventureID();
        CreateTimelineRequest req2 = new CreateTimelineRequest(adventureId, TimelineType.ITINERARY, user.getUsername() + " edited the " + itinerary.getTitle() + " itinerary.");
        restTemplate.postForObject(IP + ":" + timelinePort + "/timeline/createTimeline", req2, String.class);
        return returnString;
    }

    @GetMapping("/removeEntry/{id}/{userId}")
    public String removeItineraryEntry(@PathVariable UUID id,@PathVariable UUID userId) throws Exception {
        String[] ports = {itineraryPort,userPort,timelinePort};
        service.pingCheck(ports,restTemplate);
        GetUserByUUIDDTO user = restTemplate.getForObject(IP + ":" + userPort + "/user/getUser/"+userId, GetUserByUUIDDTO.class);
        ItineraryResponseDTO response = restTemplate.getForObject(IP + ":" + itineraryPort + "/itinerary/getItineraryByEntryId/"+id, ItineraryResponseDTO.class);
        assert response != null;
        CreateTimelineRequest req2 = new CreateTimelineRequest(response.getAdventureID(), TimelineType.ITINERARY,user.getUsername()+" deleted an entry from the "+response.getTitle()+" itinerary." );
        restTemplate.postForObject( IP + ":" + timelinePort + "/timeline/createTimeline", req2, String.class);
        return restTemplate.getForObject(IP + ":" + itineraryPort + "/itinerary/removeEntry/"+id, String.class);
    }

    @GetMapping("/markEntry/{id}")
    public void markItineraryEntry(@PathVariable UUID id) throws Exception {
        String[] ports = {itineraryPort};
        service.pingCheck(ports,restTemplate);
        restTemplate.getForObject(IP + ":" + itineraryPort + "/itinerary/markEntry/"+id, String.class);
    }

    @GetMapping("/getNextEntry/{id}//{userID}")
    public MainItineraryEntryResponseDTO getNextEntry(@PathVariable UUID id,@PathVariable UUID userID) throws Exception {
        String[] ports = {itineraryPort,locationPort};
        service.pingCheck(ports,restTemplate);
        ItineraryEntryResponseDTO entry = restTemplate.getForObject(IP + ":" + itineraryPort + "/itinerary/getNextEntry/"+id+"/"+userID, ItineraryEntryResponseDTO.class);
        assert entry != null;
        LocationResponseDTO location = restTemplate.getForObject(IP + ":" + locationPort + "/location/getLocation/" + entry.getLocation(), LocationResponseDTO.class);
        return new MainItineraryEntryResponseDTO(entry.getTitle(), entry.getDescription(), entry.getId(), entry.getEntryContainerID(), entry.isCompleted(), location, entry.getTimestamp(), entry.getRegisteredUsers());
    }

    @GetMapping("/setLocation/{itineraryId}/{locationID}")
    public void setLocation(@PathVariable UUID itineraryId,@PathVariable UUID locationID) throws Exception {
        String[] ports = {itineraryPort};
        service.pingCheck(ports,restTemplate);
        restTemplate.getForObject(IP + ":" + itineraryPort + "/itinerary/setLocation/"+itineraryId+"/"+locationID, String.class);
    }

    @GetMapping("/checkUserOff/{userID}/{entryID}")
    public void checkUserOff(@PathVariable UUID userID,@PathVariable UUID entryID) throws Exception {
        String[] ports = {itineraryPort,locationPort,recommendationPort};
        service.pingCheck(ports,restTemplate);
        ItineraryEntryResponseDTO entry = restTemplate.getForObject(IP + ":" + itineraryPort + "/itinerary/getItineraryEntry/"+ entryID, ItineraryEntryResponseDTO.class);
        assert entry != null;

        Boolean flag = restTemplate.getForObject(IP + ":" + locationPort + "/location/compareGeography/"+ entry.getLocation() + "/" + userID, Boolean.class);
        assert flag != null;

        if(flag){
            restTemplate.getForObject(IP + ":" + itineraryPort + "/itinerary/checkUserOff/"+ entryID + "/" + userID, String.class);
            restTemplate.getForObject(IP + ":" + locationPort + "/location/addFlagLocation/"+ entry.getLocation() + "/" + userID, String.class);
            restTemplate.getForObject(IP + ":" + recommendationPort + "/recommendation/visit/" + userID + "/" + entry.getLocation(), String.class);
        }
        else{
            throw new CurrentLocationException("Check User Off: User is in the incorrect location");
        }
    }

    @GetMapping("/getStartDateEndDate/{id}")
    public StartDateEndDateResponseDTO getStartDateEndDate(@PathVariable UUID id) throws Exception {
        String[] ports = {itineraryPort};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(IP + ":" + itineraryPort + "/itinerary/getStartDateEndDate/"+id, StartDateEndDateResponseDTO.class);
    }

    @GetMapping("/registerUser/{userID}/{entryID}")
    public String registerUser(@PathVariable UUID userID,@PathVariable UUID entryID) throws Exception {
        String[] ports = {itineraryPort};
        service.pingCheck(ports,restTemplate);
       return restTemplate.getForObject(IP + ":" + itineraryPort + "/itinerary/registerUser/"+ entryID + "/" + userID, String.class);
    }

    @GetMapping("/deregisterUser/{userID}/{entryID}")
    public String deregisterUser(@PathVariable UUID userID,@PathVariable UUID entryID) throws Exception {
        String[] ports = {itineraryPort};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(IP + ":" + itineraryPort + "/itinerary/deregisterUser/"+ entryID + "/" + userID, String.class);
    }

    @GetMapping("/getRegisteredUsers/{id}")
    public List<RegisteredUsersDTO> getRegisteredUsers(@PathVariable UUID id) throws Exception {
        String[] ports = {itineraryPort,userPort};
        service.pingCheck(ports,restTemplate);
        Map<UUID, Boolean> list = restTemplate.getForObject(IP + ":" + itineraryPort + "/itinerary/getRegisteredUsers/" + id, Map.class);
        assert list != null;
        List<RegisteredUsersDTO> users = new ArrayList<>();
        GetUserByUUIDDTO user;

        if (list.size() == 0) {
            return users;
        }

        for (Map.Entry<UUID, Boolean> entry : list.entrySet()) {
            user = restTemplate.getForObject(IP + ":" + userPort + "/user/getUser/" + entry.getKey(), GetUserByUUIDDTO.class);
            assert user != null;
            users.add(new RegisteredUsersDTO(user, entry.getValue()));
        }

        return users;
    }

    @GetMapping("/isRegisteredUser/{id}/{userId}")
    public boolean isRegisteredUser(@PathVariable UUID id, @PathVariable UUID userId) throws Exception {
        String[] ports = {itineraryPort};
        service.pingCheck(ports,restTemplate);
        Map<String, Boolean> list = restTemplate.getForObject(IP + ":" + itineraryPort + "/itinerary/getRegisteredUsers/" + id, Map.class);
        assert list != null;

        for (Map.Entry<String, Boolean> entry : list.entrySet()) {
            if (entry.getKey().equals(userId.toString())) {
                return true;
            }
        }
        return false;
    }

}
