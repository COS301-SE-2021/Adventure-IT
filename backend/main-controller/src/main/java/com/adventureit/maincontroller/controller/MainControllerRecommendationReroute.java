package com.adventureit.maincontroller.controller;


import com.adventureit.shareddtos.location.responses.LocationsResponseDTO;
import org.springframework.http.ResponseEntity;
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
    private MainControllerServiceImplementation service;

    private final String IP = "http://localhost";
    private final String recommendationPort = "9013";

    @Autowired
    public MainControllerRecommendationReroute(MainControllerServiceImplementation service) {
        this.service = service;
    }
    String IP = "http://localhost";
    String recommendationPort = "9013";
    String locationPort = "9006";

    // User requests arbitrary number of recommendations
    @GetMapping("get/{userId}/{numRecommendations}/{location}")
    public List<RecommendedLocationResponseDTO> getUserRecommendations(@PathVariable UUID userId, @PathVariable String numRecommendations, @PathVariable String location){
        String[][] locationUUIDs = restTemplate.getForObject(IP + ":" + recommendationPort + "/recommendation/get/" + userId + "/" + numRecommendations+"/"+location, String[][].class);
        List<RecommendedLocationResponseDTO> returnList = new ArrayList<>();
        for(int i = 0; i < Objects.requireNonNull(locationUUIDs).length; i++){
            LocationResponseDTO locationObject = restTemplate.getForObject(IP + ":" + locationPort + "/location/getLocation/"+locationUUIDs[i][0], LocationResponseDTO.class);
            returnList.add(new RecommendedLocationResponseDTO(locationObject.getId(),locationObject.getPhotoReference(),locationObject.getFormattedAddress(),locationObject.getPlaceId(),locationObject.getName(),Boolean.parseBoolean(locationUUIDs[i][1])));
        }
        return returnList;
    @GetMapping("get/{userId}/{numRecommendations}")
    public LocationsResponseDTO getUserRecommendations(@PathVariable UUID userId, @PathVariable int numRecommendations) throws Exception {
        String[] ports = {recommendationPort};
        service.pingCheck(ports,restTemplate);
        List<UUID> locationUUIDs = restTemplate.getForObject(IP + ":" + recommendationPort + "/recommendation/get/" + userId + "/" + numRecommendations, List.class);
        return restTemplate.getForObject(IP + ":" + recommendationPort + "/location/getLocations/"+locationUUIDs.toString(), LocationsResponseDTO.class);
    }

    @GetMapping("like/{userId}/{locationId}")
    public String likeLocation(@PathVariable UUID userId, @PathVariable UUID locationId) {
        return restTemplate.getForObject(IP + ":" + recommendationPort + "/recommendation/like/" + userId + "/" + locationId, String.class);
    public ResponseEntity<String> likeLocation(@PathVariable UUID userId, @PathVariable UUID locationId) throws Exception {
        String[] ports = {recommendationPort};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(IP + ":" + recommendationPort + "/recommendation/like/" + userId + "/" + locationId, ResponseEntity.class);
    }

    @GetMapping("visit/{userId}/{locationId}")
    public String visitLocation(@PathVariable UUID userId, @PathVariable UUID locationId) {
        return restTemplate.getForObject(IP + ":" + recommendationPort + "/recommendation/visit/" + userId + "/" + locationId, String.class);
    public ResponseEntity<String> visitLocation(@PathVariable UUID userId, @PathVariable UUID locationId) throws Exception {
        String[] ports = {recommendationPort};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(IP + ":" + recommendationPort + "/recommendation/visit/" + userId + "/" + locationId, ResponseEntity.class);
    }

    // User requests arbitrary number of popular locations
    @GetMapping("get/popular/{userId}/{numPopular}/{location}")
    public List<RecommendedLocationResponseDTO> getMostPopular(@PathVariable UUID userId, @PathVariable String numPopular,@PathVariable String location){
        String[][] locationUUIDs = restTemplate.getForObject(IP + ":" + recommendationPort + "/recommendation/get/popular/"+ userId+"/" +numPopular+"/"+location, String[][].class);
        List<RecommendedLocationResponseDTO> returnList = new ArrayList<>();
        for(int i = 0; i < Objects.requireNonNull(locationUUIDs).length; i++){
            LocationResponseDTO locationObject = restTemplate.getForObject(IP + ":" + locationPort + "/location/getLocation/"+locationUUIDs[i][0], LocationResponseDTO.class);
            returnList.add(new RecommendedLocationResponseDTO(locationObject.getId(),locationObject.getPhotoReference(),locationObject.getFormattedAddress(),locationObject.getPlaceId(),locationObject.getName(),Boolean.parseBoolean(locationUUIDs[i][1])));
        }
        return returnList;
    @GetMapping("get/popular/{numPopular}")
    public LocationsResponseDTO getMostPopular(@PathVariable UUID userId, @PathVariable int numPopular) throws Exception {
        String[] ports = {recommendationPort};
        service.pingCheck(ports,restTemplate);
        List<UUID> locationUUIDs = restTemplate.getForObject(IP + ":" + recommendationPort + "/recommendation/popular/" + numPopular, List.class);
        return restTemplate.getForObject(IP + ":" + recommendationPort + "/location/getLocations/"+locationUUIDs.toString(), LocationsResponseDTO.class);
    }
}
