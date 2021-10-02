package com.adventureit.maincontroller.controller;


import com.adventureit.maincontroller.exceptions.ControllerNotAvailable;
import com.adventureit.maincontroller.service.MainControllerServiceImplementation;
import com.adventureit.shareddtos.location.responses.LocationResponseDTO;
import com.adventureit.shareddtos.location.responses.RecommendedLocationResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/recommendation")
public class MainControllerRecommendationReroute {

    private final RestTemplate restTemplate = new RestTemplate();
    private final MainControllerServiceImplementation service;

    private static final String INTERNET_PORT = "http://internal-microservice-load-balancer-1572194202.us-east-2.elb.amazonaws.com";
    private static final String RECOMMENDATION_PORT = "9013";
    private static final String LOCATION_PORT = "9006";
    private static final String ERROR = "Empty Error";

    @Autowired
    public MainControllerRecommendationReroute(MainControllerServiceImplementation service) {
        this.service = service;
    }

    @GetMapping("/test")
    public String test(){
        return restTemplate.getForObject(INTERNET_PORT + ":" + RECOMMENDATION_PORT + "/recommendation/test", String.class);
    }

    // User requests arbitrary number of recommendations
    @GetMapping("get/{userId}/{numRecommendations}/{location}")
    public List<RecommendedLocationResponseDTO> getUserRecommendations(@PathVariable UUID userId, @PathVariable String numRecommendations, @PathVariable String location) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {RECOMMENDATION_PORT,LOCATION_PORT};
        service.pingCheck(ports, restTemplate);
        String id = userId.toString();
        if(id.equals("")) {
            throw new ControllerNotAvailable(ERROR);
        }
        String num = numRecommendations;
        if(num.equals("")) {
            throw new ControllerNotAvailable(ERROR);
        }
        String loc = location;
        if(loc.equals("")) {
            throw new ControllerNotAvailable(ERROR);
        }
        String[][] locationUUIDs = restTemplate.getForObject(INTERNET_PORT + ":" + RECOMMENDATION_PORT + "/recommendation/get/" + UUID.fromString(id) + "/" + num + "/" + loc, String[][].class);
        List<RecommendedLocationResponseDTO> returnList = new ArrayList<>();
        for (int i = 0; i < Objects.requireNonNull(locationUUIDs).length; i++) {
            LocationResponseDTO locationObject = restTemplate.getForObject(INTERNET_PORT + ":" + LOCATION_PORT + "/location/getLocation/" + locationUUIDs[i][0], LocationResponseDTO.class);
            assert locationObject != null;
            returnList.add(new RecommendedLocationResponseDTO(locationObject.getId(), locationObject.getPhotoReference(), locationObject.getFormattedAddress(), locationObject.getPlaceId(), locationObject.getName(), Boolean.parseBoolean(locationUUIDs[i][1])));
        }
        return returnList;
    }

    @GetMapping("like/{userId}/{locationId}")
    public String likeLocation(@PathVariable UUID userId, @PathVariable UUID locationId) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {RECOMMENDATION_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(INTERNET_PORT + ":" + RECOMMENDATION_PORT + "/recommendation/like/" + userId + "/" + locationId, String.class);
   }

    @GetMapping("visit/{userId}/{locationId}")
    public String visitLocation(@PathVariable UUID userId, @PathVariable UUID locationId) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {RECOMMENDATION_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(INTERNET_PORT + ":" + RECOMMENDATION_PORT + "/recommendation/visit/" + userId + "/" + locationId, String.class);
    }

    // User requests arbitrary number of popular locations
    @GetMapping("get/popular/{userId}/{numPopular}/{location}")
    public List<RecommendedLocationResponseDTO> getMostPopular(@PathVariable UUID userId, @PathVariable String numPopular,@PathVariable String location) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {RECOMMENDATION_PORT,LOCATION_PORT};
        service.pingCheck(ports,restTemplate);
        String id = userId.toString();
        if(id.equals("")) {
            throw new ControllerNotAvailable(ERROR);
        }
        String num = numPopular;
        if(num.equals("")) {
            throw new ControllerNotAvailable(ERROR);
        }
        String loc = location;
        if(loc.equals("")) {
            throw new ControllerNotAvailable(ERROR);
        }
        String[][] locationUUIDs = restTemplate.getForObject(INTERNET_PORT + ":" + RECOMMENDATION_PORT + "/recommendation/get/popular/"+ UUID.fromString(id)+"/" + num +"/"+loc, String[][].class);
        assert locationUUIDs != null;
        if(locationUUIDs[0].length == 0){
            return new ArrayList<>();
        }
        else {
            List<RecommendedLocationResponseDTO> returnList = new ArrayList<>();
            for(int i = 0; i < Objects.requireNonNull(locationUUIDs).length; i++){
                LocationResponseDTO locationObject = restTemplate.getForObject(INTERNET_PORT + ":" + LOCATION_PORT + "/location/getLocation/"+locationUUIDs[i][0], LocationResponseDTO.class);
                assert locationObject != null;
                returnList.add(new RecommendedLocationResponseDTO(locationObject.getId(),locationObject.getPhotoReference(),locationObject.getFormattedAddress(),locationObject.getPlaceId(),locationObject.getName(),Boolean.parseBoolean(locationUUIDs[i][1])));
            }
            return returnList;

        }

    }
}
