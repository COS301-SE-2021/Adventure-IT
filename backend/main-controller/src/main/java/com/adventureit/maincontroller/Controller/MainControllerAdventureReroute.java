package com.adventureit.maincontroller.Controller;

import com.adventureit.adventureservice.Responses.CreateAdventureResponse;
import com.adventureit.maincontroller.Requests.Adventure.CreateAdventureRequest;
import com.adventureit.maincontroller.Service.ConnectionFactory;
import com.adventureit.userservice.Entities.Users;
import com.adventureit.userservice.Responses.GetUserByUUIDDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
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
    private final String locationPort = "9006";

    @GetMapping("/test")
    public String adventureTest(){
        return restTemplate.getForObject("http://"+ IP + ":" + adventurePort + "/adventure/test", String.class);
    }

    @GetMapping("getAttendees/{id}")
    public List<GetUserByUUIDDTO> getAttendees(@PathVariable UUID id) throws Exception {
        List<UUID> users = restTemplate.getForObject("http://"+ IP + ":" + adventurePort + "/adventure/getAttendees/" + id, List.class);
        List<GetUserByUUIDDTO> list = new ArrayList<>();
        Users user;
        for (UUID x:users){
            user = restTemplate.getForObject("http://"+ IP + ":" + userPort + "api/GetUser/" + id, Users.class);
            list.add(new GetUserByUUIDDTO(user.getUserID(),user.getUsername(), user.getFirstname(),user.getLastname(),user.getEmail(),user.getPhoneNumber()));
        }
        return list;
    }

    @GetMapping("/location/test")
    public String locationTest(){
        return restTemplate.getForObject("http://"+ IP + ":" + locationPort + "/location/test", String.class);
    }

    @GetMapping(value="location/create/{location}")
    public String createLocation(@PathVariable String location) {
        return restTemplate.getForObject("http://"+ IP + ":" + locationPort + "/location/create/" + location, String.class);
    }

    @PostMapping(value = "/create")
    public CreateAdventureResponse createAdventure(@RequestBody CreateAdventureRequest req) {
        return restTemplate.postForObject("http://"+ IP + ":" + adventurePort + "/adventure/create/", req, CreateAdventureResponse.class);
    }

}


