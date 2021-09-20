package com.adventureit.maincontroller.controller;

import com.adventureit.maincontroller.service.MainControllerServiceImplementation;
import com.adventureit.shareddtos.location.responses.CurrentLocationResponseDTO;
import com.adventureit.shareddtos.location.responses.LocationResponseDTO;
import com.adventureit.shareddtos.recommendation.request.CreateLocationRequest;
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
    private final MainControllerServiceImplementation service;

    private static final String INTERNET_PORT = "http://localhost";
    private static final String LOCATION_PORT = "9006";
    private static final String ADVENTURE_PORT = "9001";
    private static final String RECOMMENDATION_PORT = "9013";

    @Autowired
    public MainControllerLocationReroute(MainControllerServiceImplementation service) {
        this.service = service;
    }

    @GetMapping("/test")
    public String locationTest(){
        return restTemplate.getForObject(INTERNET_PORT + ":" + LOCATION_PORT + "/location/test", String.class);
    }

    @GetMapping(value="/create/{location}")
    public String createLocation(@PathVariable String location) throws Exception {
        String[] ports = {LOCATION_PORT, RECOMMENDATION_PORT};
        service.pingCheck(ports,restTemplate);
        UUID createdLocationUUID = restTemplate.getForObject(INTERNET_PORT + ":" + LOCATION_PORT + "/location/create/" + location, UUID.class);
        try {
            LocationResponseDTO locationDTO = restTemplate.getForObject(INTERNET_PORT + ":" + LOCATION_PORT + "/location/getLocation/createdLocationUUID"+createdLocationUUID,LocationResponseDTO.class);
            CreateLocationRequest req = new CreateLocationRequest(createdLocationUUID, locationDTO.getFormattedAddress());
            restTemplate.postForObject(INTERNET_PORT + ":" + RECOMMENDATION_PORT + "/recommendation/add/location", req, ResponseEntity.class);
        }
        catch(Exception e){
            return "Error: Malformed create location request";
        }
        return createdLocationUUID.toString();
    }

    @GetMapping("/storeCurrentLocation/{userID}/{latitude}/{longitude}")
    public void storeCurrentLocation(@PathVariable UUID userID, @PathVariable String latitude, @PathVariable String longitude) throws Exception {
        String[] ports = {LOCATION_PORT};
        service.pingCheck(ports,restTemplate);
        restTemplate.getForObject(INTERNET_PORT + ":" + LOCATION_PORT + "/location/storeCurrentLocation/" + userID + "/" + latitude + "/" + longitude, String.class);
    }

    @GetMapping("/getCurrentLocation/{userID}")
    public CurrentLocationResponseDTO getCurrentLocation(@PathVariable UUID userID) throws Exception {
        String[] ports = {LOCATION_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(INTERNET_PORT + ":" + LOCATION_PORT + "/location/getCurrentLocation/" + userID, CurrentLocationResponseDTO.class);
    }

    @GetMapping("/getAllCurrentLocations/{adventureID}")
    public List<CurrentLocationResponseDTO> getAllCurrentLocations(@PathVariable UUID adventureID) throws Exception {
        String[] ports = {LOCATION_PORT, ADVENTURE_PORT};
        service.pingCheck(ports,restTemplate);
        List<UUID> users = restTemplate.getForObject(INTERNET_PORT + ":" + ADVENTURE_PORT + "/adventure/getAttendees/" + adventureID, List.class);
        assert users != null;
        List<CurrentLocationResponseDTO> list = new ArrayList<>();

        for (int i = 0; i<users.size();i++) {
            list.add(restTemplate.getForObject(INTERNET_PORT + ":" + LOCATION_PORT + "/location/getCurrentLocation/" + users.get(i), CurrentLocationResponseDTO.class));
        }

        return list;
    }

    @GetMapping(value = "/getFlagList/{userID}")
    public List<String> getFlagList(@PathVariable UUID userID) throws Exception {
        String[] ports = {LOCATION_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(INTERNET_PORT + ":" + LOCATION_PORT + "/location/getFlagList/" + userID, List.class);
    }
}
