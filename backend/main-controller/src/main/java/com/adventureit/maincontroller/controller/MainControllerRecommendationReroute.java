package com.adventureit.maincontroller.controller;


import com.adventureit.shareddtos.location.responses.LocationsResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/recommendation")
public class MainControllerRecommendationReroute {
    private final RestTemplate restTemplate = new RestTemplate();

    String IP = "http://localhost";
    String recommendationPort = "9013";

    // User requests arbitrary number of recommendations
    @GetMapping("get/{userId}/{numRecommendations}")
    public LocationsResponseDTO getUserRecommendations(@PathVariable UUID userId, @PathVariable int numRecommendations){
        List<UUID> locationUUIDs = restTemplate.getForObject(IP + ":" + recommendationPort + "/recommendation/get/" + userId + "/" + numRecommendations, List.class);
        return restTemplate.getForObject(IP + ":" + recommendationPort + "/location/getLocations/"+locationUUIDs.toString(), LocationsResponseDTO.class);
    }

    // User requests arbitrary number of popular locations
    @GetMapping("get/popular/{numPopular}")
    public LocationsResponseDTO getMostPopular(@PathVariable UUID userId, @PathVariable int numPopular){
        List<UUID> locationUUIDs = restTemplate.getForObject(IP + ":" + recommendationPort + "/recommendation/popular/" + numPopular, List.class);
        return restTemplate.getForObject(IP + ":" + recommendationPort + "/location/getLocations/"+locationUUIDs.toString(), LocationsResponseDTO.class);
    }
}
