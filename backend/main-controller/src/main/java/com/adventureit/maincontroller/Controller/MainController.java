package com.adventureit.maincontroller.Controller;

<<<<<<< Updated upstream
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

=======
//import com.adventureit.userservice.Entities.Users;
//import com.adventureit.userservice.Responses.GetUserByUUIDDTO;
import com.adventureit.adventureservice.Requests.CreateAdventureRequest;
import com.adventureit.adventureservice.Responses.CreateAdventureResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
>>>>>>> Stashed changes
import java.util.UUID;

@RestController
public class MainController {

    @Autowired
    private EurekaClient eurekaClient;

    private RestTemplate restTemplate = new RestTemplate();
<<<<<<< Updated upstream
=======
    private final String IP = "localhost";
    private final String adventurePort = "9001";
    private final String userPort = "9002";
    private final String locationPort = "9006";
>>>>>>> Stashed changes

    @RequestMapping("/adventure/test")
    public String adventureTest(){
        InstanceInfo adventureInstance =eurekaClient.getApplication("ADVENTURE-SERVICE").getInstances().get(0);
        String adventureIP = adventureInstance.getIPAddr();
        int adventurePort = adventureInstance.getPort();
        return restTemplate.getForObject("http://"+ adventureIP + ":" + adventurePort + "/adventure/test", String.class);
    }
/*
    @RequestMapping("/api/GetUser/{id}")
    public GetUserByUUIDResponse getUserByUUID(@PathVariable UUID id){
        InstanceInfo adventureInstance =eurekaClient.getApplication("ADVENTURE-SERVICE").getInstances().get(0);
        String adventureIP = adventureInstance.getIPAddr();
        int adventurePort = adventureInstance.getPort();
        return restTemplate.getForObject("http://"+ adventureIP + ":" + adventurePort + "/adventure/test", String.class);
    }*/
<<<<<<< Updated upstream
=======

    @GetMapping("/adventure/test")
    public String adventureTest(){
        return restTemplate.getForObject("http://"+ IP + ":" + adventurePort + "/adventure/test", String.class);
    }

//    @GetMapping("adventure/getAttendees/{id}")
//    public List<GetUserByUUIDDTO> getAttendees(@PathVariable UUID id) throws Exception {
//        List<UUID> users = restTemplate.getForObject("http://"+ IP + ":" + adventurePort + "/adventure/getAttendees/" + id, List.class);
//        List<GetUserByUUIDDTO> list = new ArrayList<>();
//        Users user;
//        for (UUID x:users){
//            user = restTemplate.getForObject("http://"+ IP + ":" + userPort + "api/GetUser/" + id, Users.class);
//            list.add(new GetUserByUUIDDTO(user.getUserID(),user.getUsername(), user.getFirstname(),user.getLastname(),user.getEmail(),user.getPhoneNumber()));
//        }
//        return list;
//    }

    @GetMapping("/location/test")
    public String locationTest(){
        return restTemplate.getForObject("http://"+ IP + ":" + locationPort + "/location/test", String.class);
    }

    @GetMapping(value="location/create/{location}")
    public String createLocation(@PathVariable String location) {
        return restTemplate.getForObject("http://"+ IP + ":" + locationPort + "/location/create/" + location, String.class);
    }

    @PostMapping(value = "adventure/create")
    public CreateAdventureResponse createAdventure(@RequestBody CreateAdventureRequest req) {
        return restTemplate.postForObject("http://"+ IP + ":" + adventurePort + "/adventure/create/", req, CreateAdventureResponse.class);
    }
>>>>>>> Stashed changes
}


