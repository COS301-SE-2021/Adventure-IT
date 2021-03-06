package com.adventureit.maincontroller.controller;

import com.adventureit.maincontroller.Threads.CascadingDeleteThread;
import com.adventureit.maincontroller.exceptions.ControllerNotAvailable;
import com.adventureit.maincontroller.service.MainControllerServiceImplementation;
import com.adventureit.shareddtos.adventure.requests.CreateAdventureRequest;
import com.adventureit.shareddtos.adventure.requests.EditAdventureRequest;
import com.adventureit.shareddtos.adventure.responses.*;
import com.adventureit.shareddtos.chat.requests.CreateGroupChatRequest;
import com.adventureit.shareddtos.location.responses.LocationResponseDTO;
import com.adventureit.shareddtos.timeline.TimelineType;
import com.adventureit.shareddtos.timeline.requests.CreateTimelineRequest;
import com.adventureit.shareddtos.user.responses.GetUserByUUIDDTO;
import com.adventureit.maincontroller.responses.AdventureResponseDTO;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/adventure")
public class MainControllerAdventureReroute {

    private final RestTemplate restTemplate = new RestTemplate();
    private final MainControllerServiceImplementation service;

    private static final String INTERNET_PORT = "http://internal-microservice-load-balancer-1572194202.us-east-2.elb.amazonaws.com";
    private static final String ADVENTURE_PORT = "9001";
    private static final String USER_PORT = "9002";
    private static final String CHAT_PORT = "9010";
    private static final String TIMELINE_PORT = "9012";
    private static final String LOCATION_PORT = "9006";
    private static final String BUDGET_PORT = "9007";
    private static final String CHECKLIST_PORT = "9008";
    private static final String ITINERARY_PORT = "9009";
    private static final String MEDIA_PORT = "9005";
    private static final String GET_USER = "/user/getUser/";
    private static final String CREATE_TIMELINE = "/timeline/createTimeline";
    private static final String ERROR = "Empty Error";

    @Autowired
    public MainControllerAdventureReroute(MainControllerServiceImplementation service) {
        this.service = service;
    }

    @GetMapping("/test")
    public String adventureTest(){
        return restTemplate.getForObject(INTERNET_PORT + ":" + ADVENTURE_PORT + "/adventure/test", String.class);
    }

    @GetMapping("/getAttendees/{id}")
    public List<GetUserByUUIDDTO> getAttendees(@PathVariable UUID id) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {ADVENTURE_PORT, USER_PORT};
        service.pingCheck(ports,restTemplate);

        List<UUID> users = restTemplate.getForObject(INTERNET_PORT + ":" + ADVENTURE_PORT + "/adventure/getAttendees/" + id, List.class);

        List<GetUserByUUIDDTO> list = new ArrayList<>();
        GetUserByUUIDDTO user;
        assert users != null;
        for (int i = 0; i<users.size();i++) {
            user = restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + GET_USER + users.get(i), GetUserByUUIDDTO.class);
            assert user != null;
           list.add(user);
        }
        return list;
    }

    @PostMapping(value = "/create")
    public CreateAdventureResponse createAdventure(@RequestBody CreateAdventureRequest req) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {ADVENTURE_PORT, LOCATION_PORT, CHAT_PORT};
        service.pingCheck(ports,restTemplate);
        String location = req.getLocation();
        if(location.equals("")) {
            throw new ControllerNotAvailable(ERROR);
        }
        UUID locationId = restTemplate.getForObject(INTERNET_PORT + ":" + LOCATION_PORT + "/location/create/"+location,UUID.class);
        CreateAdventureResponse response = restTemplate.postForObject(INTERNET_PORT + ":" + ADVENTURE_PORT + "/adventure/create/",req, CreateAdventureResponse.class);
        assert response != null;
        assert locationId != null;
        UUID adventureId = response.getAdventure().getAdventureId();
        restTemplate.getForObject(INTERNET_PORT + ":" + ADVENTURE_PORT + "/adventure/setLocation/"+adventureId.toString()+"/"+locationId,String.class);
        CreateGroupChatRequest req2 = new CreateGroupChatRequest(adventureId,response.getAdventure().getAttendees(),"General");
        restTemplate.postForObject(INTERNET_PORT + ":" + CHAT_PORT + "/chat/createGroupChat/", req2, String.class);
        return response;
    }

    @GetMapping("/all")
    public List<GetAllAdventuresResponse> getAllAdventures() throws ControllerNotAvailable, InterruptedException {
        String[] ports = {ADVENTURE_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(INTERNET_PORT + ":" + ADVENTURE_PORT + "/adventure/all", List.class);
    }

    @GetMapping("/setLocation/{adventureId}/{locationId}")
    public String setLocationAdventures(@PathVariable UUID adventureId,@PathVariable UUID locationId) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {ADVENTURE_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(INTERNET_PORT + ":" + ADVENTURE_PORT + "/adventure/setLocation/"+adventureId+"/"+locationId, String.class);
    }

    @GetMapping("/all/{id}")
    public List<AdventureResponseDTO> getAllAdventuresByUserUUID(@PathVariable UUID id) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {ADVENTURE_PORT, LOCATION_PORT};
        service.pingCheck(ports,restTemplate);
        List<LinkedHashMap<String,String>> adventures = restTemplate.getForObject(INTERNET_PORT + ":" + ADVENTURE_PORT + "/adventure/all/"+id, List.class);
        List<AdventureResponseDTO> returnList = new ArrayList<>();
        assert adventures != null;
        for (LinkedHashMap<String,String> adventure : adventures) {
            AdventureResponseDTO responseObject = new AdventureResponseDTO(adventure.get("name"), adventure.get("description"), UUID.fromString(adventure.get("adventureId")), UUID.fromString(adventure.get("ownerId")), LocalDate.parse(adventure.get("startDate")), LocalDate.parse(adventure.get("endDate")));
            LocationResponseDTO adventureLocation = restTemplate.getForObject(INTERNET_PORT +":"+ LOCATION_PORT +"/location/getLocation/"+adventure.get("location"), LocationResponseDTO.class);
            responseObject.setLocation(adventureLocation);
            returnList.add(responseObject);

        }
        return returnList;
    }

    @GetMapping("/owner/{id}")
    public List<GetAdventuresByUserUUIDResponse> getAdventuresByOwnerUUID(@PathVariable UUID id) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {ADVENTURE_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(INTERNET_PORT + ":" + ADVENTURE_PORT + "/adventure/owner/"+id, List.class);
    }

    @GetMapping("/attendee/{id}")
    public List<GetAdventuresByUserUUIDResponse> getAdventuresByAttendeeUUID(@PathVariable UUID id) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {ADVENTURE_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(INTERNET_PORT + ":" + ADVENTURE_PORT + "/adventure/attendee/"+id, List.class);
    }

    @GetMapping("/remove/{id}/{userID}")
    public void removeAdventure(@PathVariable UUID id, @PathVariable UUID userID) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {ADVENTURE_PORT};
        service.pingCheck(ports,restTemplate);

        RemoveAdventureResponse response = restTemplate.getForObject(INTERNET_PORT + ":" + ADVENTURE_PORT + "/adventure/remove/"+id+"/"+userID, RemoveAdventureResponse.class);
        assert response != null;
        if(response.isDeleteAdventure()){
            new CascadingDeleteThread(INTERNET_PORT, BUDGET_PORT, "/budget/deleteAllByAdventure/" ,id);
            new CascadingDeleteThread(INTERNET_PORT, CHECKLIST_PORT, "/checklist/deleteAllByAdventure/" ,id);
            new CascadingDeleteThread(INTERNET_PORT, CHAT_PORT, "/chat/deleteByAdventure/" ,id);
            new CascadingDeleteThread(INTERNET_PORT, ITINERARY_PORT, "/itinerary/deleteAllByAdventure/" ,id);
            new CascadingDeleteThread(INTERNET_PORT, MEDIA_PORT, "/media/deleteAllByAdventure/" ,id);
            new CascadingDeleteThread(INTERNET_PORT, TIMELINE_PORT, "/timeline/deleteTimelineByAdventureID/" ,id);
        }
    }

    @GetMapping("/addAttendees/{adventureID}/{userID}")
    public String addAttendees(@PathVariable UUID adventureID,@PathVariable UUID userID) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {ADVENTURE_PORT, LOCATION_PORT, CHAT_PORT, USER_PORT, TIMELINE_PORT};
        service.pingCheck(ports,restTemplate);
        restTemplate.getForObject(INTERNET_PORT + ":" + ADVENTURE_PORT + "/adventure/addAttendees/"+adventureID+"/"+userID, String.class);
        GetUserByUUIDDTO response = restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + GET_USER + userID, GetUserByUUIDDTO.class);
        assert response != null;
        CreateTimelineRequest req2 = new CreateTimelineRequest(adventureID, TimelineType.ADVENTURE,response.getUsername()+" has joined this adventure!" );
        restTemplate.getForObject(INTERNET_PORT + ":" + CHAT_PORT + "/chat/addParticipant/"+adventureID+"/"+userID, String.class);
        return restTemplate.postForObject(INTERNET_PORT + ":" + TIMELINE_PORT + CREATE_TIMELINE, req2, String.class);
    }

    @GetMapping("/removeAttendees/{adventureID}/{userID}")
    public String removeAttendees(@PathVariable UUID adventureID,@PathVariable UUID userID) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {ADVENTURE_PORT, USER_PORT, TIMELINE_PORT};
        service.pingCheck(ports,restTemplate);
        restTemplate.getForObject(INTERNET_PORT + ":" + ADVENTURE_PORT + "/adventure/removeAttendees/"+adventureID+"/"+userID, String.class);
        GetUserByUUIDDTO response = restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + GET_USER + userID, GetUserByUUIDDTO.class);
        assert response != null;
        CreateTimelineRequest req2 = new CreateTimelineRequest(adventureID, TimelineType.ADVENTURE,response.getUsername()+" left this adventure" );
        return restTemplate.postForObject(INTERNET_PORT + ":" + TIMELINE_PORT + CREATE_TIMELINE, req2, String.class);
    }

    @PostMapping("/editAdventure")
    public String editAdventure(@RequestBody EditAdventureRequest req) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {ADVENTURE_PORT, USER_PORT, TIMELINE_PORT};
        service.pingCheck(ports,restTemplate);
        String uid = req.getUserId().toString();
        if(uid.equals("")) {
            throw new ControllerNotAvailable(ERROR);
        }
        String aid = req.getAdventureId().toString();
        if(aid.equals("")) {
            throw new ControllerNotAvailable(ERROR);
        }
        GetUserByUUIDDTO user = restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + GET_USER + UUID.fromString(uid), GetUserByUUIDDTO.class);
        assert user != null;
        CreateTimelineRequest req2 = new CreateTimelineRequest(UUID.fromString(aid), TimelineType.ADVENTURE,user.getUsername()+" edited this adventure." );
        restTemplate.postForObject(INTERNET_PORT + ":" + TIMELINE_PORT + CREATE_TIMELINE, req2, String.class);
        return restTemplate.postForObject(INTERNET_PORT + ":" + ADVENTURE_PORT + "/adventure/editAdventure", req, String.class);
    }

}


