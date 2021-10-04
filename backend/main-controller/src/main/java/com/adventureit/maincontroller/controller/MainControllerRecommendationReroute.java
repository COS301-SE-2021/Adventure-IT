package com.adventureit.maincontroller.controller;


import com.adventureit.maincontroller.exceptions.ControllerNotAvailable;
import com.adventureit.maincontroller.service.MainControllerServiceImplementation;
import com.adventureit.shareddtos.location.responses.LocationResponseDTO;
import com.adventureit.shareddtos.location.responses.RecommendedLocationResponseDTO;
import com.adventureit.shareddtos.recommendation.request.CreateLocationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.sql.Array;
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
        if(locationUUIDs[0].length != 0){
        for (int i = 0; i < Objects.requireNonNull(locationUUIDs).length; i++) {
            LocationResponseDTO locationObject = restTemplate.getForObject(INTERNET_PORT + ":" + LOCATION_PORT + "/location/getLocation/" + locationUUIDs[i][0], LocationResponseDTO.class);
            assert locationObject != null;
            returnList.add(new RecommendedLocationResponseDTO(locationObject.getId(), locationObject.getPhotoReference(), locationObject.getFormattedAddress(), locationObject.getPlaceId(), locationObject.getName(), Boolean.parseBoolean(locationUUIDs[i][1])));
        }
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

    @GetMapping("populate")
    public String Populate() {
        List<String> users = new ArrayList<>(List.of("c74b2567-85e8-465e-a1ff-8ad6e22b8021", "15986343-f1f2-4885-9991-e143fa663af5", "05bbe608-eb77-4c62-b824-f4a3a0568e7f", "80e1b64d-fd53-4f3a-84a9-14541caff723"));
        List<String> locations = new ArrayList<>(List.of("Two Oceans Aquarium","Simon's Town Museum","Royal Observatory, Cape of Good Hope","Rhodes Memorial","Ratanga Junction","Monkeyland Primate Sanctuary","Mariner's wharf","Heart of Cape Town Museum","Chapman's peak","District Six Museum","Duiker Island","Cape Town Holocaust Centre","Cape Medical Museum","Castle of Good Hope","Pilansberg","Lion Park","Lesedi Cultural Village","Sudwala Caves","Oribi Gorge","Wonder Cave","Oriental Plaza","Melrose House","Market Theatre","Nelson Mandela House","Kruger House","Hector Pieterson Museum","Cradle of Humankind","Basotho Cultural Village","Vredefort Dome","Grahamstown","Namaqualand","Table Mountain", "Lions Head", "Kirstenbosch Botanical Gardens", "Boulders Beach", "Robben Island", "Cape Point", "Kruger National Park", "Madikwe Game Reserve", "V&A Waterfront", "Franschhoek Motor Museum", "Newlands Brewery", "Groot Constantia", "Cango Caves", "The Big Tree at Tsitsikamma", "Ostrich Farm, Oudtshoorn", "Addo Elephant National Park", "Bathurst", "Jeffrey’s Bay", "The Big Hole, Kimberley", "Augrabies Falls", "The Apartheid Museum", "Gold Reef City", "Newtown Cultural Precinct", "Satyagraha House", "Nelson Mandela Square", "Montecasino", "SAB World of Beer", "Voortrekker Monument", "The Union Buildings", "Maropeng", "Sterkfontein Caves", "Hartbeespoort", "The Palace of the Lost City", "Blyde River Canyon", "uShaka Marine World", "Valley of a Thousand Hills", "The Drakensberg", "Howick Falls", "Gateway Theatre of Shopping"));
        for (int i = 0; i < locations.size(); i++) {
            UUID createdLocationUUID = restTemplate.getForObject(INTERNET_PORT + ":" + LOCATION_PORT + "/location/create/" + locations.get(i)+" South Africa", UUID.class);
            try {
                LocationResponseDTO locationDTO = restTemplate.getForObject(INTERNET_PORT + ":" + LOCATION_PORT + "/location/getLocation/" + createdLocationUUID, LocationResponseDTO.class);
                assert locationDTO != null;
                CreateLocationRequest req = new CreateLocationRequest(createdLocationUUID, locationDTO.getFormattedAddress());
                restTemplate.postForObject(INTERNET_PORT + ":" + RECOMMENDATION_PORT + "/recommendation/add/location", req, String.class);
            } catch (Exception e) {
                return "Error: Malformed create location request: " + e.getMessage();
            }
            for(int k=0;k<users.size();k++)
            {
                int check=(int)(Math.random() * 30);
                if(check>20) {
                    restTemplate.getForObject(INTERNET_PORT + ":" + RECOMMENDATION_PORT + "/recommendation/like/" + users.get(k) + "/" + createdLocationUUID, String.class);
                }
                if(check>10)
                {
                    restTemplate.getForObject(INTERNET_PORT + ":" + RECOMMENDATION_PORT + "/recommendation/visit/" + users.get(k) + "/" + createdLocationUUID, String.class);
                }
            }
        }
        return "Completed";
    }
}
