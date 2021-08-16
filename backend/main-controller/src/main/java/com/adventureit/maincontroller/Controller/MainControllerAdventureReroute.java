package com.adventureit.maincontroller.Controller;

import com.adventureit.adventureservice.Requests.CreateAdventureRequest;
import com.adventureit.adventureservice.Responses.CreateAdventureResponse;
import com.adventureit.adventureservice.Responses.GetAdventuresByUserUUIDResponse;
import com.adventureit.adventureservice.Responses.GetAllAdventuresResponse;
import com.adventureit.adventureservice.Responses.RemoveAdventureResponse;
import com.adventureit.chat.Requests.CreateGroupChatRequest;
import com.adventureit.itinerary.Responses.ItineraryEntryResponseDTO;
import com.adventureit.locationservice.Entity.Location;
import com.adventureit.locationservice.Responses.LocationResponseDTO;

import com.adventureit.maincontroller.Responses.AdventureResponseDTO;
import com.adventureit.timelineservice.Entity.TimelineType;
import com.adventureit.timelineservice.Requests.CreateTimelineRequest;
import com.adventureit.maincontroller.Responses.AdventureResponseDTO;
import com.adventureit.maincontroller.Responses.MainItineraryEntryResponseDTO;
import com.adventureit.userservice.Entities.Users;
import com.adventureit.userservice.Responses.GetUserByUUIDDTO;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/adventure")
public class MainControllerAdventureReroute {

    @Autowired
    private EurekaClient eurekaClient;
    private RestTemplate restTemplate = new RestTemplate();
    private final String IP = "localhost";
    private final String adventurePort = "9001";
    private final String userPort = "9002";
    private final String chatPort = "9010";
    private final String locationPort = "9006";
    private final String timelinePort = "9012";

    @GetMapping("/test")
    public String adventureTest(){
        return restTemplate.getForObject("http://"+ IP + ":" + adventurePort + "/adventure/test", String.class);
    }

    @GetMapping("/getAttendees/{id}")
    public List<GetUserByUUIDDTO> getAttendees(@PathVariable UUID id) throws Exception {
        List<UUID> users = restTemplate.getForObject("http://"+ IP + ":" + adventurePort + "/adventure/getAttendees/" + id, List.class);
        List<GetUserByUUIDDTO> list = new ArrayList<>();
        Users user;
        System.out.println(users);
        for (int x = 0; x < users.size(); x++){
            user = restTemplate.getForObject("http://"+ IP + ":" + userPort + "/user/GetUser/" + users.get(x) , Users.class);
            list.add(new GetUserByUUIDDTO(user.getUserID(),user.getUsername(), user.getFirstname(),user.getLastname(),user.getEmail()));
        }
        return list;
    }

    @PostMapping(value = "/create")
    public CreateAdventureResponse createAdventure(@RequestBody CreateAdventureRequest req) {
        System.out.println(req.getStartDate());
        UUID locationId = restTemplate.getForObject("http://"+ IP + ":" + "9006" + "/location/create/"+req.getLocation(),UUID.class);
        CreateAdventureResponse response = restTemplate.postForObject("http://"+ IP + ":" + adventurePort + "/adventure/create/",req, CreateAdventureResponse.class);
        UUID adventureId = response.getAdventure().getAdventureId();
        restTemplate.getForObject("http://"+ IP + ":" + "9001" + "/adventure/setLocation/"+adventureId.toString()+"/"+locationId.toString(),String.class);
        CreateGroupChatRequest req2 = new CreateGroupChatRequest(adventureId,response.getAdventure().getAttendees(),"General");
        restTemplate.postForObject("http://"+ IP + ":" + chatPort + "/chat/createGroupChat/", req2, String.class);
        return response;
    }

    @GetMapping("/all")
    public List<GetAllAdventuresResponse> getAllAdventures() {
        return restTemplate.getForObject("http://"+ IP + ":" + adventurePort + "/adventure/all", List.class);
    }

    @GetMapping("/setLocation/{adventureId}/{locationId}")
    public String setLocationAdventures(@PathVariable UUID adventureId,@PathVariable UUID locationId) {
        return restTemplate.getForObject("http://"+ IP + ":" + adventurePort + "/adventure/setLocation/"+adventureId+"/"+locationId, String.class);
    }

    @GetMapping("/all/{id}")

    public List<AdventureResponseDTO> getAllAdventuresByUserUUID(@PathVariable UUID id){

        List <AdventureResponseDTO> listToReturn = new ArrayList<>();
        List<GetAdventuresByUserUUIDResponse> list=restTemplate.getForObject("http://"+ IP + ":" + adventurePort + "/adventure/all/"+id, List.class);
        LocationResponseDTO location;

        for (GetAdventuresByUserUUIDResponse entry:list) {
            location = restTemplate.getForObject("http://"+ IP + ":" + locationPort + "/location/getLocation/" + entry.getLocation(), LocationResponseDTO.class);
            listToReturn.add(new AdventureResponseDTO(entry.getName(),entry.getDescription(),entry.getAdventureId(),entry.getOwnerId(),entry.getStartDate(),entry.getEndDate(),location));
        }

        return listToReturn;
    }

    @GetMapping("/owner/{id}")
    public List<GetAdventuresByUserUUIDResponse> getAdventuresByOwnerUUID(@PathVariable UUID id){
        return restTemplate.getForObject("http://"+ IP + ":" + adventurePort + "/adventure/owner/"+id, List.class);
    }

    @GetMapping("/attendee/{id}")
    public List<GetAdventuresByUserUUIDResponse> getAdventuresByAttendeeUUID(@PathVariable UUID id){
        return restTemplate.getForObject("http://"+ IP + ":" + adventurePort + "/adventure/attendee/"+id, List.class);
    }

    @DeleteMapping("/remove/{id}/{userID}")
    public RemoveAdventureResponse removeAdventure(@PathVariable UUID id, @PathVariable UUID userID) throws Exception {
        return restTemplate.getForObject("http://"+ IP + ":" + adventurePort + "/adventure/remove/"+id+"/"+userID, RemoveAdventureResponse.class);
    }

    @GetMapping("/addAttendees/{adventureID}/{userID}")
    public String addAttendees(@PathVariable UUID adventureID,@PathVariable UUID userID) throws Exception {
        restTemplate.getForObject("http://"+ IP + ":" + adventurePort + "/adventure/addAttendees/"+adventureID+"/"+userID, String.class);
        GetUserByUUIDDTO response = restTemplate.getForObject("http://"+ IP + ":" + userPort + "/user/addAttendees/"+userID, GetUserByUUIDDTO.class);
        CreateTimelineRequest req2 = new CreateTimelineRequest(adventureID, TimelineType.ADVENTURE,response.getUsername()+" has been added to this adventure" );
        return restTemplate.postForObject("http://"+ IP + ":" + timelinePort + "/timeline/createTimeline", req2, String.class);

    }

}


