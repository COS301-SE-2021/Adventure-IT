package com.adventureit.maincontroller.controller;


import com.adventureit.shareddtos.location.responses.LocationResponseDTO;
import com.adventureit.shareddtos.location.responses.LocationsResponseDTO;
import com.adventureit.shareddtos.location.responses.RecommendedLocationResponseDTO;
import org.springframework.http.ResponseEntity;
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

    String IP = "http://localhost";
    String recommendationPort = "9013";
    String locationPort = "9006";

    // User requests arbitrary number of recommendations
    @GetMapping("get/{userId}/{numRecommendations}")
    public List<RecommendedLocationResponseDTO> getUserRecommendations(@PathVariable UUID userId, @PathVariable int numRecommendations){
        String[][] locationUUIDs = restTemplate.getForObject(IP + ":" + recommendationPort + "/recommendation/get/" + userId + "/" + numRecommendations, String[][].class);
        List<RecommendedLocationResponseDTO> returnList = new ArrayList<>();
        for(int i = 0; i < Objects.requireNonNull(locationUUIDs).length; i++){
            LocationResponseDTO location = restTemplate.getForObject(IP + ":" + locationPort + "/location/getLocation/"+locationUUIDs[i][0], LocationResponseDTO.class);
            returnList.add(new RecommendedLocationResponseDTO(location.getId(),location.getPhotoReference(),location.getFormattedAddress(),location.getPlaceId(),location.getName(),Boolean.parseBoolean(locationUUIDs[i][1])));
        }
        return returnList;
    }

    @GetMapping("like/{userId}/{locationId}")
    public ResponseEntity<String> likeLocation(@PathVariable UUID userId, @PathVariable UUID locationId) {
        return restTemplate.getForObject(IP + ":" + recommendationPort + "/recommendation/like/" + userId + "/" + locationId, ResponseEntity.class);
    }

    @GetMapping("visit/{userId}/{locationId}")
    public ResponseEntity<String> visitLocation(@PathVariable UUID userId, @PathVariable UUID locationId) {
        return restTemplate.getForObject(IP + ":" + recommendationPort + "/recommendation/visit/" + userId + "/" + locationId, ResponseEntity.class);
    }

    // User requests arbitrary number of popular locations
    @GetMapping("get/popular/{numPopular}")
    public LocationsResponseDTO getMostPopular(@PathVariable UUID userId, @PathVariable int numPopular){
        List<UUID> locationUUIDs = restTemplate.getForObject(IP + ":" + recommendationPort + "/recommendation/popular/" + numPopular, List.class);
        return restTemplate.getForObject(IP + ":" + recommendationPort + "/location/getLocations/"+locationUUIDs.toString(), LocationsResponseDTO.class);
    }
}
