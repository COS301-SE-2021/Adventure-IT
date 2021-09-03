package com.adventureit.maincontroller.controller;

import com.adventureit.locationservice.responses.CurrentLocationResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/location")
public class MainControllerLocationReroute {

    private final RestTemplate restTemplate = new RestTemplate();

    private final String IP = "http://localhost";
    private final String locationPort = "9006";
    private final String adventurePort = "9001";

    @GetMapping("/test")
    public String locationTest(){
        return restTemplate.getForObject(IP + ":" + locationPort + "/location/test", String.class);
    }

    @GetMapping(value="/create/{location}")
    public String createLocation(@PathVariable String location) {
        return restTemplate.getForObject(IP + ":" + locationPort + "/location/create/" + location, String.class);
    }

    @GetMapping("/storeCurrentLocation/{userID}/{latitude}/{longitude}")
    public void storeCurrentLocation(@PathVariable UUID userID, @PathVariable String latitude, @PathVariable String longitude){
        restTemplate.getForObject(IP + ":" + locationPort + "/location/storeCurrentLocation/" + userID + "/" + latitude + "/" + longitude, String.class);
    }

    @GetMapping("/getCurrentLocation/{userID}")
    public CurrentLocationResponseDTO getCurrentLocation(@PathVariable UUID userID){
        return restTemplate.getForObject(IP + ":" + locationPort + "/location/getCurrentLocation/" + userID, CurrentLocationResponseDTO.class);
    }

    @GetMapping("/getAllCurrentLocations/{adventureID}")
    public List<CurrentLocationResponseDTO> getAllCurrentLocations(@PathVariable UUID adventureID){
        List<UUID> users = restTemplate.getForObject(IP + ":" + adventurePort + "/adventure/getAttendees/" + adventureID, List.class);
        assert users != null;
        List<CurrentLocationResponseDTO> list = new ArrayList<>();

        for (int i = 0; i<users.size();i++) {
            list.add(restTemplate.getForObject(IP + ":" + locationPort + "/location/getCurrentLocation/" + users.get(i), CurrentLocationResponseDTO.class));
        }

        return list;
    }
}
