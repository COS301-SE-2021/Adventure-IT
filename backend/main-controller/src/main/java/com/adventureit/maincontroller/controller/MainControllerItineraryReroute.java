package com.adventureit.maincontroller.controller;


import com.adventureit.maincontroller.exceptions.ControllerNotAvailable;
import com.adventureit.maincontroller.exceptions.CurrentLocationException;
import com.adventureit.maincontroller.exceptions.InvalidItineraryEntryException;
import com.adventureit.maincontroller.responses.RegisteredUsersDTO;
import com.adventureit.maincontroller.service.MainControllerServiceImplementation;
import com.adventureit.shareddtos.adventure.AdventureDTO;
import com.adventureit.shareddtos.adventure.responses.GetAdventureByUUIDResponse;
import com.adventureit.shareddtos.itinerary.requests.AddItineraryEntryRequest;
import com.adventureit.shareddtos.itinerary.requests.CreateItineraryRequest;
import com.adventureit.shareddtos.itinerary.requests.EditItineraryEntryRequest;
import com.adventureit.shareddtos.itinerary.responses.ItineraryEntryResponseDTO;
import com.adventureit.shareddtos.itinerary.responses.ItineraryResponseDTO;
import com.adventureit.shareddtos.itinerary.responses.StartDateEndDateResponseDTO;
import com.adventureit.shareddtos.location.responses.LocationResponseDTO;
import com.adventureit.maincontroller.responses.MainItineraryEntryResponseDTO;
import com.adventureit.shareddtos.notification.requests.SendEmailRequest;
import com.adventureit.shareddtos.recommendation.request.CreateLocationRequest;
import com.adventureit.shareddtos.timeline.TimelineType;
import com.adventureit.shareddtos.timeline.requests.CreateTimelineRequest;
import com.adventureit.shareddtos.user.responses.GetUserByUUIDDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/itinerary")
public class MainControllerItineraryReroute {

    private final RestTemplate restTemplate = new RestTemplate();
    private final MainControllerServiceImplementation service;

    private static final String INTERNET_PORT = "http://localhost";
    private static final String LOCATION_PORT = "9006";
    private static final String ITINERARY_PORT = "9009";
    private static final String TIMELINE_PORT = "9012";
    private static final String ADVENTURE_PORT = "9001";
    private static final String USER_PORT = "9002";
    private static final String RECOMMENDATION_PORT = "9013";
    private static final String NOTIFICATION_PORT = "9004";
    private static final String GET_USER = "/user/getUser/";
    private static final String GET_ITIN_BY_ID = "/itinerary/getItineraryById/";
    private static final String CREATE_TIMELINE = "/timeline/createTimeline";
    private static final String GET_LOCATION = "/location/getLocation/";
    private static final String ITINERARY_M = " itinerary.";
    private static final String ERROR = "Empty Error";

    @Autowired
    public MainControllerItineraryReroute(MainControllerServiceImplementation service) {
        this.service = service;
    }

    @GetMapping("/test")
    public String itineraryTest(){
        return restTemplate.getForObject(INTERNET_PORT + ":" + ITINERARY_PORT + "/itinerary/test", String.class);
    }

    @PostMapping(value = "/addEntry")
    public UUID addItineraryEntry(@RequestBody AddItineraryEntryRequest req) throws ControllerNotAvailable, InterruptedException , NullPointerException {
        String[] ports = {ITINERARY_PORT, USER_PORT, ADVENTURE_PORT, TIMELINE_PORT};
        service.pingCheck(ports,restTemplate);

        String id = req.getUserId().toString();
        if(id.equals("")) {
            throw new ControllerNotAvailable(ERROR);
        }
        String ecid = req.getEntryContainerID().toString();
        if(ecid.equals("")) {
            throw new ControllerNotAvailable(ERROR);
        }

        GetUserByUUIDDTO user = restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + GET_USER + UUID.fromString(id), GetUserByUUIDDTO.class);
        ItineraryResponseDTO itinerary = restTemplate.getForObject(INTERNET_PORT + ":" + ITINERARY_PORT + GET_ITIN_BY_ID + UUID.fromString(ecid), ItineraryResponseDTO.class);
        assert itinerary != null;
        UUID adventureId = itinerary.getAdventureID();
        try {
            GetAdventureByUUIDResponse response = restTemplate.getForObject(INTERNET_PORT + ":" + ADVENTURE_PORT + "/adventure/getAdventureByUUID/" + adventureId, GetAdventureByUUIDResponse.class);
            if(response == null){
                throw new NullPointerException("Adventure is null");
            }
            AdventureDTO adventureResponse = response.getAdventure();
            LocalDateTime timestamp = LocalDateTime.parse(req.getTimestamp());
            if((timestamp.toLocalDate().compareTo(adventureResponse.getEndDate()) > 0) || (timestamp.toLocalDate().compareTo(adventureResponse.getStartDate()) < 0)){
                throw new InvalidItineraryEntryException("Itinerary Entry does not fit within Adventure");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        String lid = req.getLocation();
        if(lid.equals("")) {
            throw new ControllerNotAvailable(ERROR);
        }
        UUID locationId = restTemplate.getForObject(INTERNET_PORT + ":" + LOCATION_PORT + "/location/create/" + lid, UUID.class);
        LocationResponseDTO locationDTO = restTemplate.getForObject(INTERNET_PORT + ":" + LOCATION_PORT + GET_LOCATION +locationId,LocationResponseDTO.class);

        assert locationDTO != null;
        CreateLocationRequest req3 = new CreateLocationRequest(locationId, locationDTO.getName());
        restTemplate.postForObject(INTERNET_PORT + ":" + RECOMMENDATION_PORT + "/recommendation/add/location", req3, String.class);

        UUID itineraryID = restTemplate.postForObject(INTERNET_PORT + ":" + ITINERARY_PORT + "/itinerary/addEntry", req, UUID.class);
        restTemplate.getForObject(INTERNET_PORT + ":" + ITINERARY_PORT + "/itinerary/setLocation/" + itineraryID + "/" + locationId, String.class);

        assert user != null;
        CreateTimelineRequest req2 = new CreateTimelineRequest(adventureId, TimelineType.ITINERARY, user.getUsername() + " added an entry to the " + req.getTitle() + ITINERARY_M);
        restTemplate.postForObject(INTERNET_PORT + ":" + TIMELINE_PORT + CREATE_TIMELINE, req2, String.class);
        return itineraryID;
    }

    @GetMapping("/viewItinerariesByAdventure/{id}")
    public List<ItineraryResponseDTO> viewItinerariesByAdventure(@PathVariable UUID id) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {ITINERARY_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(INTERNET_PORT + ":" + ITINERARY_PORT + "/itinerary/viewItinerariesByAdventure/"+id, List.class);
    }

    @GetMapping("/viewItinerary/{id}")
    public List<MainItineraryEntryResponseDTO> viewItinerary(@PathVariable UUID id) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {ITINERARY_PORT, LOCATION_PORT};
        service.pingCheck(ports,restTemplate);
        List<LinkedHashMap<String,String>> entries = restTemplate.getForObject(INTERNET_PORT + ":" + ITINERARY_PORT + "/itinerary/viewItinerary/"+id, List.class);
        List<MainItineraryEntryResponseDTO> list = new ArrayList<>();

        assert entries != null;
        for (LinkedHashMap entry :
                entries) {
            try {
                LocationResponseDTO itineraryLocation = restTemplate.getForObject(INTERNET_PORT +":"+ LOCATION_PORT +GET_LOCATION+entry.get("location"), LocationResponseDTO.class);
                MainItineraryEntryResponseDTO responseObject = new MainItineraryEntryResponseDTO((String)entry.get("title"), (String)entry.get("description"), UUID.fromString((String)entry.get("id")), UUID.fromString((String)entry.get("entryContainerID")),(boolean)entry.get("completed"),itineraryLocation, LocalDateTime.parse((String)entry.get("timestamp")),(Map<UUID,Boolean>)entry.get("registeredUsers"));

                list.add(responseObject);
            } catch (Exception e) {
                return list;
            }

        }

        return list;
    }

    @GetMapping("/softDelete/{id}/{userID}")
    public String softDelete(@PathVariable UUID id,@PathVariable UUID userID) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {ITINERARY_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(INTERNET_PORT + ":" + ITINERARY_PORT + "/itinerary/softDelete/"+id+"/"+userID, String.class);
    }

    @GetMapping("/viewTrash/{id}")
    public List<ItineraryResponseDTO> viewTrash(@PathVariable UUID id) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {ITINERARY_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject( INTERNET_PORT + ":" + ITINERARY_PORT + "/itinerary/viewTrash/"+id, List.class);
    }

    @GetMapping("/restoreItinerary/{id}/{userID}")
    public String restoreItinerary(@PathVariable UUID id,@PathVariable UUID userID) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {ITINERARY_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(INTERNET_PORT + ":" + ITINERARY_PORT + "/itinerary/restoreItinerary/"+id+"/"+userID, String.class);
    }

    @GetMapping("/hardDelete/{id}/{userID}")
    public String hardDelete(@PathVariable UUID id, @PathVariable UUID userID) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {ITINERARY_PORT, USER_PORT, TIMELINE_PORT};
        service.pingCheck(ports,restTemplate);
        GetUserByUUIDDTO user = restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + GET_USER+userID, GetUserByUUIDDTO.class);

        ItineraryResponseDTO response = restTemplate.getForObject(INTERNET_PORT + ":" + ITINERARY_PORT + GET_ITIN_BY_ID + id, ItineraryResponseDTO.class);
        String returnString = restTemplate.getForObject(INTERNET_PORT + ":" + ITINERARY_PORT + "/itinerary/hardDelete/" + id + "/" + userID, String.class);
        assert response != null;
        assert user != null;
        CreateTimelineRequest req2 = new CreateTimelineRequest(response.getAdventureID(), TimelineType.ITINERARY, user.getUsername() + " deleted the " + response.getTitle() + ITINERARY_M);
        restTemplate.postForObject(INTERNET_PORT + ":" + TIMELINE_PORT + CREATE_TIMELINE, req2, String.class);
        return returnString;
    }

    @PostMapping("/create")
    public String createItinerary(@RequestBody CreateItineraryRequest req) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {ITINERARY_PORT, USER_PORT, TIMELINE_PORT};
        service.pingCheck(ports,restTemplate);
        String id = req.getUserID().toString();
        if(id.equals("")) {
            throw new ControllerNotAvailable(ERROR);
        }
        GetUserByUUIDDTO user = restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + GET_USER + UUID.fromString(id), GetUserByUUIDDTO.class);
        String returnString = restTemplate.postForObject(INTERNET_PORT + ":" + ITINERARY_PORT + "/itinerary/create/", req, String.class);
        assert user != null;
        String aid = req.getAdvID().toString();
        if(aid.equals("")) {
            throw new ControllerNotAvailable(ERROR);
        }
        CreateTimelineRequest req2 = new CreateTimelineRequest(UUID.fromString(aid), TimelineType.ITINERARY, user.getUsername() + " created a new Itinerary for " + req.getTitle() + ".");
        restTemplate.postForObject(INTERNET_PORT + ":" + TIMELINE_PORT + CREATE_TIMELINE, req2, String.class);
        return returnString;

    }


    @PostMapping("/editEntry")
    public String editItineraryEntry(@RequestBody EditItineraryEntryRequest req) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {ITINERARY_PORT, USER_PORT, TIMELINE_PORT, LOCATION_PORT};
        service.pingCheck(ports,restTemplate);
        String lid = req.getLocation().toString();
        if(lid.equals("")) {
            throw new ControllerNotAvailable(ERROR);
        }
        String id = req.getUserId().toString();
        if(id.equals("")) {
            throw new ControllerNotAvailable(ERROR);
        }
        UUID locationId = restTemplate.getForObject(INTERNET_PORT + ":" + LOCATION_PORT + "/location/create/"+ lid,UUID.class);
        GetUserByUUIDDTO user = restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + GET_USER + UUID.fromString(id), GetUserByUUIDDTO.class);
        req.setLocationId(locationId);
        String ecid = req.getEntryContainerID().toString();
        if(ecid.equals("")) {
            throw new ControllerNotAvailable(ERROR);
        }
        String returnString = restTemplate.postForObject(INTERNET_PORT + ":" + ITINERARY_PORT + "/itinerary/editEntry/", req, String.class);
        ItineraryResponseDTO itinerary = restTemplate.getForObject(INTERNET_PORT + ":" + ITINERARY_PORT + GET_ITIN_BY_ID + UUID.fromString(ecid), ItineraryResponseDTO.class);
        assert itinerary != null;
        UUID adventureId = itinerary.getAdventureID();
        assert user != null;
        CreateTimelineRequest req2 = new CreateTimelineRequest(adventureId, TimelineType.ITINERARY, user.getUsername() + " edited the " + itinerary.getTitle() + ITINERARY_M);
        restTemplate.postForObject(INTERNET_PORT + ":" + TIMELINE_PORT + CREATE_TIMELINE, req2, String.class);
        return returnString;
    }

    @GetMapping("/removeEntry/{id}/{userId}")
    public String removeItineraryEntry(@PathVariable UUID id,@PathVariable UUID userId) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {ITINERARY_PORT, USER_PORT, TIMELINE_PORT};
        service.pingCheck(ports,restTemplate);
        GetUserByUUIDDTO user = restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + GET_USER+userId, GetUserByUUIDDTO.class);
        ItineraryResponseDTO response = restTemplate.getForObject(INTERNET_PORT + ":" + ITINERARY_PORT + "/itinerary/getItineraryByEntryId/"+id, ItineraryResponseDTO.class);
        assert response != null;
        assert user != null;
        CreateTimelineRequest req2 = new CreateTimelineRequest(response.getAdventureID(), TimelineType.ITINERARY,user.getUsername()+" deleted an entry from the "+response.getTitle()+ITINERARY_M );
        restTemplate.postForObject( INTERNET_PORT + ":" + TIMELINE_PORT + CREATE_TIMELINE, req2, String.class);
        return restTemplate.getForObject(INTERNET_PORT + ":" + ITINERARY_PORT + "/itinerary/removeEntry/"+id, String.class);
    }

    @GetMapping("/markEntry/{id}")
    public void markItineraryEntry(@PathVariable UUID id) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {ITINERARY_PORT};
        service.pingCheck(ports,restTemplate);
        restTemplate.getForObject(INTERNET_PORT + ":" + ITINERARY_PORT + "/itinerary/markEntry/"+id, String.class);
    }

    @GetMapping("/getNextEntry/{id}//{userID}")
    public MainItineraryEntryResponseDTO getNextEntry(@PathVariable UUID id,@PathVariable UUID userID) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {ITINERARY_PORT, LOCATION_PORT};
        service.pingCheck(ports,restTemplate);
        ItineraryEntryResponseDTO entry = restTemplate.getForObject(INTERNET_PORT + ":" + ITINERARY_PORT + "/itinerary/getNextEntry/"+id+"/"+userID, ItineraryEntryResponseDTO.class);
        assert entry != null;
        LocationResponseDTO location = restTemplate.getForObject(INTERNET_PORT + ":" + LOCATION_PORT + GET_LOCATION + entry.getLocation(), LocationResponseDTO.class);
        return new MainItineraryEntryResponseDTO(entry.getTitle(), entry.getDescription(), entry.getId(), entry.getEntryContainerID(), entry.isCompleted(), location, entry.getTimestamp(), entry.getRegisteredUsers());
    }

    @GetMapping("/setLocation/{itineraryId}/{locationID}")
    public void setLocation(@PathVariable UUID itineraryId,@PathVariable UUID locationID) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {ITINERARY_PORT};
        service.pingCheck(ports,restTemplate);
        restTemplate.getForObject(INTERNET_PORT + ":" + ITINERARY_PORT + "/itinerary/setLocation/"+itineraryId+"/"+locationID, String.class);
    }

    @GetMapping("/checkUserOff/{userID}/{entryID}")
    public void checkUserOff(@PathVariable UUID userID,@PathVariable UUID entryID) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {ITINERARY_PORT, LOCATION_PORT, RECOMMENDATION_PORT,NOTIFICATION_PORT};
        service.pingCheck(ports,restTemplate);
        ItineraryEntryResponseDTO entry = restTemplate.getForObject(INTERNET_PORT + ":" + ITINERARY_PORT + "/itinerary/getItineraryEntry/"+ entryID, ItineraryEntryResponseDTO.class);
        assert entry != null;

        Boolean flag = restTemplate.getForObject(INTERNET_PORT + ":" + LOCATION_PORT + "/location/compareGeography/"+ entry.getLocation() + "/" + userID, Boolean.class);
        assert flag != null;

        if(flag){
            restTemplate.getForObject(INTERNET_PORT + ":" + ITINERARY_PORT + "/itinerary/checkUserOff/"+ entryID + "/" + userID, String.class);
            restTemplate.getForObject(INTERNET_PORT + ":" + LOCATION_PORT + "/location/addFlagLocation/"+ entry.getLocation() + "/" + userID, String.class);
            restTemplate.getForObject(INTERNET_PORT + ":" + RECOMMENDATION_PORT + "/recommendation/visit/" + userID + "/" + entry.getLocation(), String.class);
        }
        else{
            throw new CurrentLocationException("Check User Off: User is in the incorrect location");
        }
        LocationResponseDTO location = restTemplate.getForObject(INTERNET_PORT + ":" + LOCATION_PORT + GET_LOCATION + entry.getLocation() , LocationResponseDTO.class);
        GetUserByUUIDDTO user = restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + GET_USER + userID, GetUserByUUIDDTO.class);
        assert user != null;
        if (user.getSettings().equals(false)){
            assert location != null;
            String link = location.getFormattedAddress();
            String refactoredLink = link.replace(" ","%20");
            SendEmailRequest req = new SendEmailRequest(user.getEmergencyEmail(),"Check-in confirmed",user.getFirstname()+" has checked into: "+location.getName()+".\n Use the link to see their location:\n http://maps.google.com/maps?q="+refactoredLink+"&z=17\n \n \n \n From Adventure IT team");
            restTemplate.postForObject(INTERNET_PORT + ":" + NOTIFICATION_PORT + "/notification/sendemail/" ,req, String.class);
        }
    }

    @GetMapping("/getStartDateEndDate/{id}")
    public StartDateEndDateResponseDTO getStartDateEndDate(@PathVariable UUID id) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {ITINERARY_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(INTERNET_PORT + ":" + ITINERARY_PORT + "/itinerary/getStartDateEndDate/"+id, StartDateEndDateResponseDTO.class);
    }

    @GetMapping("/registerUser/{userID}/{entryID}")
    public String registerUser(@PathVariable UUID userID,@PathVariable UUID entryID) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {ITINERARY_PORT};
        service.pingCheck(ports,restTemplate);
       return restTemplate.getForObject(INTERNET_PORT + ":" + ITINERARY_PORT + "/itinerary/registerUser/"+ entryID + "/" + userID, String.class);
    }

    @GetMapping("/deregisterUser/{userID}/{entryID}")
    public String deregisterUser(@PathVariable UUID userID,@PathVariable UUID entryID) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {ITINERARY_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(INTERNET_PORT + ":" + ITINERARY_PORT + "/itinerary/deregisterUser/"+ entryID + "/" + userID, String.class);
    }

    @GetMapping("/getRegisteredUsers/{id}")
    public List<RegisteredUsersDTO> getRegisteredUsers(@PathVariable UUID id) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {ITINERARY_PORT, USER_PORT};
        service.pingCheck(ports,restTemplate);

        Map<UUID, Boolean> list = restTemplate.getForObject(INTERNET_PORT + ":" + ITINERARY_PORT + "/itinerary/getRegisteredUsers/" + id, Map.class);
        assert list != null;
        List<RegisteredUsersDTO> users = new ArrayList<>();
        GetUserByUUIDDTO user;

        if (list.size() == 0) {
            return users;
        }

        for (Map.Entry<UUID, Boolean> entry : list.entrySet()) {
            user = restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + GET_USER + entry.getKey(), GetUserByUUIDDTO.class);
            assert user != null;
            users.add(new RegisteredUsersDTO(user, entry.getValue()));
        }

        return users;
    }

    @GetMapping("/isRegisteredUser/{id}/{userId}")
    public boolean isRegisteredUser(@PathVariable UUID id, @PathVariable UUID userId) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {ITINERARY_PORT};
        service.pingCheck(ports,restTemplate);
        Map<String, Boolean> list = restTemplate.getForObject(INTERNET_PORT + ":" + ITINERARY_PORT + "/itinerary/getRegisteredUsers/" + id, Map.class);
        assert list != null;

        for (Map.Entry<String, Boolean> entry : list.entrySet()) {
            if (entry.getKey().equals(userId.toString())) {
                return true;
            }
        }
        return false;
    }

}
