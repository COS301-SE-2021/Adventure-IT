package com.adventureit.maincontroller.controller;

import com.adventureit.adventureservice.requests.CreateAdventureRequest;
import com.adventureit.adventureservice.requests.EditAdventureRequest;
import com.adventureit.adventureservice.responses.CreateAdventureResponse;
import com.adventureit.adventureservice.responses.GetAdventuresByUserUUIDResponse;
import com.adventureit.adventureservice.responses.GetAllAdventuresResponse;
import com.adventureit.adventureservice.responses.RemoveAdventureResponse;
import com.adventureit.chat.requests.CreateGroupChatRequest;
import com.adventureit.locationservice.responses.LocationResponseDTO;
import com.adventureit.maincontroller.responses.AdventureResponseDTO;
import com.adventureit.timelineservice.entity.TimelineType;
import com.adventureit.timelineservice.requests.CreateTimelineRequest;
import com.adventureit.userservice.entities.Users;
import com.adventureit.userservice.responses.GetUserByUUIDDTO;
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
    private final String IP = "http://localhost";
    private final String adventurePort = "9001";
    private final String userPort = "9002";
    private final String chatPort = "9010";
    private final String timelinePort = "9012";

    @GetMapping("/test")
    public String adventureTest(){
        return restTemplate.getForObject(IP + ":" + adventurePort + "/adventure/test", String.class);
    }

    @GetMapping("/getAttendees/{id}")
    public List<GetUserByUUIDDTO> getAttendees(@PathVariable UUID id){
        List<UUID> users = restTemplate.getForObject(IP + ":" + adventurePort + "/adventure/getAttendees/" + id, List.class);
        List<GetUserByUUIDDTO> list = new ArrayList<>();
        GetUserByUUIDDTO user;
        assert users != null;
        for (int i = 0; i<users.size();i++) {
            user = restTemplate.getForObject(IP + ":" + userPort + "/user/getUser/" + users.get(i), GetUserByUUIDDTO.class);
            assert user != null;
           list.add(user);
        }
        return list;
    }

    @PostMapping(value = "/create")
    public CreateAdventureResponse createAdventure(@RequestBody CreateAdventureRequest req) {
        UUID locationId = restTemplate.getForObject(IP + ":" + "9006" + "/location/create/"+req.getLocation(),UUID.class);
        CreateAdventureResponse response = restTemplate.postForObject(IP + ":" + adventurePort + "/adventure/create/",req, CreateAdventureResponse.class);
        assert response != null;
        assert locationId != null;
        UUID adventureId = response.getAdventure().getAdventureId();
        restTemplate.getForObject(IP + ":" + "9001" + "/adventure/setLocation/"+adventureId.toString()+"/"+locationId,String.class);
        CreateGroupChatRequest req2 = new CreateGroupChatRequest(adventureId,response.getAdventure().getAttendees(),"General");
        restTemplate.postForObject(IP + ":" + chatPort + "/chat/createGroupChat/", req2, String.class);
        return response;
    }

    @GetMapping("/all")
    public List<GetAllAdventuresResponse> getAllAdventures() {
        return restTemplate.getForObject(IP + ":" + adventurePort + "/adventure/all", List.class);
    }

    @GetMapping("/setLocation/{adventureId}/{locationId}")
    public String setLocationAdventures(@PathVariable UUID adventureId,@PathVariable UUID locationId) {

        return restTemplate.getForObject(IP + ":" + adventurePort + "/adventure/setLocation/"+adventureId+"/"+locationId, String.class);
    }

    @GetMapping("/all/{id}")
    public List<AdventureResponseDTO> getAllAdventuresByUserUUID(@PathVariable UUID id){

        List<LinkedHashMap<String,String>> adventures = restTemplate.getForObject(IP + ":" + adventurePort + "/adventure/all/"+id, List.class);
        List<AdventureResponseDTO> returnList = new ArrayList<AdventureResponseDTO>();
        assert adventures != null;
        for (LinkedHashMap<String,String> adventure : adventures) {
            try {
                AdventureResponseDTO responseObject = new AdventureResponseDTO(adventure.get("name"), adventure.get("description"), UUID.fromString(adventure.get("adventureId")), UUID.fromString(adventure.get("ownerId")), LocalDate.parse(adventure.get("startDate")), LocalDate.parse(adventure.get("endDate")));
                LocationResponseDTO adventureLocation = restTemplate.getForObject(IP+":9006/location/getLocation/"+adventure.get("location"), LocationResponseDTO.class);
                responseObject.setLocation(adventureLocation);
                returnList.add(responseObject);
            }
            catch(Exception e){
                e.printStackTrace();
            }

        }
        return returnList;
    }

    @GetMapping("/owner/{id}")
    public List<GetAdventuresByUserUUIDResponse> getAdventuresByOwnerUUID(@PathVariable UUID id){
        return restTemplate.getForObject(IP + ":" + adventurePort + "/adventure/owner/"+id, List.class);
    }

    @GetMapping("/attendee/{id}")
    public List<GetAdventuresByUserUUIDResponse> getAdventuresByAttendeeUUID(@PathVariable UUID id){
        return restTemplate.getForObject(IP + ":" + adventurePort + "/adventure/attendee/"+id, List.class);
    }

    @GetMapping("/remove/{id}/{userID}")
    public void removeAdventure(@PathVariable UUID id, @PathVariable UUID userID){
        restTemplate.delete(IP + ":" + adventurePort + "/adventure/remove/"+id+"/"+userID, RemoveAdventureResponse.class);
    }

    @GetMapping("/addAttendees/{adventureID}/{userID}")
    public String addAttendees(@PathVariable UUID adventureID,@PathVariable UUID userID){
        restTemplate.getForObject(IP + ":" + adventurePort + "/adventure/addAttendees/"+adventureID+"/"+userID, String.class);
        GetUserByUUIDDTO response = restTemplate.getForObject(IP + ":" + userPort + "/user/getUser/"+userID, GetUserByUUIDDTO.class);
        assert response != null;
        CreateTimelineRequest req2 = new CreateTimelineRequest(adventureID, TimelineType.ADVENTURE,response.getUsername()+" has joined this adventure!" );
        restTemplate.getForObject(IP + ":" + chatPort + "/chat/addParticipant/"+adventureID+"/"+userID, String.class);
        return restTemplate.postForObject(IP + ":" + timelinePort + "/timeline/createTimeline", req2, String.class);
    }

    @GetMapping("/removeAttendees/{adventureID}/{userID}")
    public String removeAttendees(@PathVariable UUID adventureID,@PathVariable UUID userID){
        restTemplate.getForObject(IP + ":" + adventurePort + "/adventure/removeAttendees/"+adventureID+"/"+userID, String.class);
        GetUserByUUIDDTO response = restTemplate.getForObject(IP + ":" + userPort + "/user/getUser/"+userID, GetUserByUUIDDTO.class);
        assert response != null;
        CreateTimelineRequest req2 = new CreateTimelineRequest(adventureID, TimelineType.ADVENTURE,response.getUsername()+" left this adventure" );
        return restTemplate.postForObject(IP + ":" + timelinePort + "/timeline/createTimeline", req2, String.class);
    }

    @PostMapping("/editAdventure")
    public String editAdventure(@RequestBody EditAdventureRequest req){
        String timelinePort = "9012";
        GetUserByUUIDDTO user = restTemplate.getForObject(IP + ":" + userPort + "/user/getUser/"+req.getUserId(), GetUserByUUIDDTO.class);
        CreateTimelineRequest req2 = new CreateTimelineRequest(req.getAdventureId(), TimelineType.ADVENTURE,user.getUsername()+" edited this adventure." );
        restTemplate.postForObject(IP + ":" + timelinePort + "/timeline/createTimeline", req2, String.class);
        return restTemplate.postForObject(IP + ":" + adventurePort + "/adventure/editAdventure", req, String.class);
    }

}


