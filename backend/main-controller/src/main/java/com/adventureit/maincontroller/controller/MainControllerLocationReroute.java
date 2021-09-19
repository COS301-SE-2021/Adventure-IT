package com.adventureit.maincontroller.controller;

import com.adventureit.maincontroller.service.MainControllerServiceImplementation;
import com.adventureit.shareddtos.location.responses.CurrentLocationResponseDTO;
import com.adventureit.shareddtos.location.responses.LocationResponseDTO;
import com.adventureit.shareddtos.recommendation.request.CreateLocationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    private MainControllerServiceImplementation service;

    private final String IP = "http://localhost";
    private final String locationPort = "9006";
    private final String adventurePort = "9001";
    private final String recommendationPort = "9013";

    @Autowired
    public MainControllerLocationReroute(MainControllerServiceImplementation service) {
        this.service = service;
    }

    @GetMapping("/test")
    public String locationTest(){
        return restTemplate.getForObject(IP + ":" + locationPort + "/location/test", String.class);
    }

    @GetMapping(value="/create/{location}")
    public String createLocation(@PathVariable String location) throws Exception {
        String[] ports = {locationPort,recommendationPort};
        service.pingCheck(ports,restTemplate);
        UUID createdLocationUUID = restTemplate.getForObject(IP + ":" + locationPort + "/location/create/" + location, UUID.class);
        try {
            LocationResponseDTO locationDTO = restTemplate.getForObject(IP + ":" + locationPort + "/location/getLocation/createdLocationUUID"+createdLocationUUID,LocationResponseDTO.class);
            CreateLocationRequest req = new CreateLocationRequest(createdLocationUUID, locationDTO.getName());
            restTemplate.postForObject(IP + ":" + recommendationPort + "/recommendation/add/location", req, ResponseEntity.class);
        }
        catch(Exception e){
            return "Error: Malformed create location request";
        }
        return createdLocationUUID.toString();
    }

    @GetMapping("/storeCurrentLocation/{userID}/{latitude}/{longitude}")
    public void storeCurrentLocation(@PathVariable UUID userID, @PathVariable String latitude, @PathVariable String longitude) throws Exception {
        String[] ports = {locationPort};
        service.pingCheck(ports,restTemplate);
        restTemplate.getForObject(IP + ":" + locationPort + "/location/storeCurrentLocation/" + userID + "/" + latitude + "/" + longitude, String.class);
    }

    @GetMapping("/getCurrentLocation/{userID}")
    public CurrentLocationResponseDTO getCurrentLocation(@PathVariable UUID userID) throws Exception {
        String[] ports = {locationPort};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(IP + ":" + locationPort + "/location/getCurrentLocation/" + userID, CurrentLocationResponseDTO.class);
    }

    @GetMapping("/getAllCurrentLocations/{adventureID}")
    public List<CurrentLocationResponseDTO> getAllCurrentLocations(@PathVariable UUID adventureID) throws Exception {
        String[] ports = {locationPort,adventurePort};
        service.pingCheck(ports,restTemplate);
        List<UUID> users = restTemplate.getForObject(IP + ":" + adventurePort + "/adventure/getAttendees/" + adventureID, List.class);
        assert users != null;
        List<CurrentLocationResponseDTO> list = new ArrayList<>();

        for (int i = 0; i<users.size();i++) {
            list.add(restTemplate.getForObject(IP + ":" + locationPort + "/location/getCurrentLocation/" + users.get(i), CurrentLocationResponseDTO.class));
        }

        return list;
    }

    @GetMapping(value = "/getFlagList/{userID}")
    public List<String> getFlagList(@PathVariable UUID userID) throws Exception {
        String[] ports = {locationPort};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(IP + ":" + locationPort + "/location/getFlagList/" + userID, List.class);
    }
}
