package com.adventureit.maincontroller.controller;


import com.adventureit.maincontroller.exceptions.ControllerNotAvailable;
import com.adventureit.maincontroller.service.MainControllerServiceImplementation;
import com.adventureit.shareddtos.checklist.requests.AddChecklistEntryRequest;
import com.adventureit.shareddtos.checklist.requests.ChecklistDTO;
import com.adventureit.shareddtos.checklist.requests.CreateChecklistRequest;
import com.adventureit.shareddtos.checklist.requests.EditChecklistEntryRequest;
import com.adventureit.shareddtos.checklist.responses.ChecklistEntryResponseDTO;
import com.adventureit.shareddtos.checklist.responses.ChecklistResponseDTO;
import com.adventureit.shareddtos.timeline.TimelineType;
import com.adventureit.shareddtos.timeline.requests.CreateTimelineRequest;
import com.adventureit.shareddtos.user.responses.GetUserByUUIDDTO;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/checklist")
public class MainControllerChecklistReroute {

    private final RestTemplate restTemplate = new RestTemplate();
    private final MainControllerServiceImplementation service;

    private static final String INTERNET_PORT = "http://localhost";
    private static final String TIMELINE_PORT = "9012";
    private static final String CHECKLIST_PORT = "9008";
    private static final String USER_PORT = "9002";
    private static final String GET_USER = "/user/getUser/";
    private static final String GET_CHECKLIST = "/checklist/getChecklist/";
    private static final String CREATE_TIMELINE = "/timeline/createTimeline";
    private static final String CHECKLIST_M = " checklist.";

    @Autowired
    public MainControllerChecklistReroute(MainControllerServiceImplementation service) {
        this.service = service;
    }

    @GetMapping("/test")
    public String test(){
        return "Checklist Controller is functional";
    }

    @GetMapping("/viewChecklistsByAdventure/{id}")
    public List<ChecklistResponseDTO> viewChecklistsByAdventure(@PathVariable UUID id) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {CHECKLIST_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(INTERNET_PORT + ":" + CHECKLIST_PORT + "/checklist/viewChecklistsByAdventure/"+id, List.class);
    }

    @GetMapping("/viewChecklist/{id}")
    public List<ChecklistEntryResponseDTO> viewCheckist(@PathVariable UUID id) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {CHECKLIST_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(INTERNET_PORT + ":" + CHECKLIST_PORT + "/checklist/viewChecklist/"+id, List.class);
    }

    @GetMapping("/softDelete/{id}/{userID}")
    public String softDelete(@PathVariable UUID id, @PathVariable UUID userID) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {CHECKLIST_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(INTERNET_PORT + ":" + CHECKLIST_PORT + "/checklist/softDelete/"+id+"/"+userID, String.class);
    }

    @GetMapping("/viewTrash/{id}")
    public List<ChecklistResponseDTO> viewTrash(@PathVariable UUID id) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {CHECKLIST_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(INTERNET_PORT + ":" + CHECKLIST_PORT + "/checklist/viewTrash/"+id, List.class);
    }

    @GetMapping("/restoreChecklist/{id}/{userID}")
    public String restoreChecklist(@PathVariable UUID id,@PathVariable UUID userID) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {CHECKLIST_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(INTERNET_PORT + ":" + CHECKLIST_PORT + "/checklist/restoreChecklist/"+id+"/"+userID, String.class);
    }

    @GetMapping("/hardDelete/{id}/{userID}")
    public String hardDelete(@PathVariable UUID id,@PathVariable UUID userID) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {USER_PORT, CHECKLIST_PORT, TIMELINE_PORT};
        service.pingCheck(ports,restTemplate);
        GetUserByUUIDDTO user = restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + GET_USER+userID, GetUserByUUIDDTO.class);
        ChecklistDTO checklist = restTemplate.getForObject(INTERNET_PORT + ":" + CHECKLIST_PORT + GET_CHECKLIST+id, ChecklistDTO.class);
        restTemplate.getForObject(INTERNET_PORT + ":" + CHECKLIST_PORT + "/checklist/hardDelete/"+id+"/"+userID, String.class);
        assert user != null;
        CreateTimelineRequest req2 = new CreateTimelineRequest(id, TimelineType.BUDGET,user.getUsername()+" deleted the "+checklist+CHECKLIST_M );
        return restTemplate.postForObject(INTERNET_PORT + ":" + TIMELINE_PORT + CREATE_TIMELINE, req2, String.class);
    }

    @PostMapping("/create")
    public String createChecklist(@RequestBody CreateChecklistRequest req) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {USER_PORT, CHECKLIST_PORT, TIMELINE_PORT};
        service.pingCheck(ports,restTemplate);
        GetUserByUUIDDTO user = restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + GET_USER+req.getCreatorID(), GetUserByUUIDDTO.class);
        restTemplate.postForObject(INTERNET_PORT + ":" + CHECKLIST_PORT + "/checklist/create/", req, String.class);
        assert user != null;
        CreateTimelineRequest req2 = new CreateTimelineRequest(req.getAdventureID(), TimelineType.CHECKLIST,user.getUsername()+" created a new checklist for "+req.getTitle()+"." );
        return restTemplate.postForObject(INTERNET_PORT + ":" + TIMELINE_PORT + CREATE_TIMELINE, req2, String.class);
    }

    @SneakyThrows
    @PostMapping("/addEntry")
    public String addEntry(@RequestBody AddChecklistEntryRequest req){
        String[] ports = {USER_PORT, CHECKLIST_PORT, TIMELINE_PORT};
        service.pingCheck(ports,restTemplate);
        GetUserByUUIDDTO user = restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + GET_USER+req.getUserId(), GetUserByUUIDDTO.class);
        String returnString = restTemplate.postForObject(INTERNET_PORT + ":" + CHECKLIST_PORT + "/checklist/addEntry/", req, String.class);
        UUID checklistID = req.getEntryContainerID();
        ChecklistDTO checklist = restTemplate.getForObject(INTERNET_PORT + ":" + CHECKLIST_PORT + GET_CHECKLIST+checklistID, ChecklistDTO.class);
        assert checklist != null;
        UUID adventureId = checklist.getAdventureID();
        assert user != null;
        CreateTimelineRequest req2 = new CreateTimelineRequest(adventureId, TimelineType.CHECKLIST,user.getUsername()+" added an entry to the "+checklist.getTitle()+CHECKLIST_M );
        restTemplate.postForObject(INTERNET_PORT + ":" + TIMELINE_PORT + CREATE_TIMELINE, req2, String.class);
        return returnString;

    }

    @GetMapping("/removeEntry/{id}/{userId}")
    public String removeEntry(@PathVariable UUID id,@PathVariable UUID userId) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {USER_PORT, CHECKLIST_PORT, TIMELINE_PORT};
        service.pingCheck(ports,restTemplate);
        GetUserByUUIDDTO user = restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + GET_USER+userId, GetUserByUUIDDTO.class);
        ChecklistDTO checklist = restTemplate.getForObject(INTERNET_PORT + ":" + CHECKLIST_PORT + "/checklist/getChecklistByEntry/"+id, ChecklistDTO.class);
        assert checklist != null;
        UUID adventureId = checklist.getAdventureID();
        assert user != null;
        CreateTimelineRequest req2 = new CreateTimelineRequest(adventureId, TimelineType.CHECKLIST,user.getUsername()+" deleted an entry from the "+checklist.getTitle()+CHECKLIST_M );
        restTemplate.postForObject(INTERNET_PORT + ":" + TIMELINE_PORT + CREATE_TIMELINE, req2, String.class);
        return restTemplate.getForObject(INTERNET_PORT + ":" + CHECKLIST_PORT + "/checklist/removeEntry/"+id, String.class);
    }

    @PostMapping("/editEntry")
    public String editEntry(@RequestBody EditChecklistEntryRequest req) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {CHECKLIST_PORT, USER_PORT, TIMELINE_PORT};
        service.pingCheck(ports,restTemplate);
        restTemplate.postForObject(INTERNET_PORT + ":" + CHECKLIST_PORT + "/checklist/editEntry/", req, String.class);
        GetUserByUUIDDTO user = restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + GET_USER+req.getUserId(), GetUserByUUIDDTO.class);
        UUID checklistID = req.getEntryContainerID();
        ChecklistDTO checklist = restTemplate.getForObject(INTERNET_PORT + ":" + CHECKLIST_PORT + GET_CHECKLIST+checklistID, ChecklistDTO.class);
        assert checklist != null;
        UUID adventureId = checklist.getAdventureID();
        assert user != null;
        CreateTimelineRequest req2 = new CreateTimelineRequest(adventureId, TimelineType.BUDGET,user.getUsername()+" edited the "+checklist.getTitle()+CHECKLIST_M);
        return restTemplate.postForObject(INTERNET_PORT + ":" + TIMELINE_PORT + CREATE_TIMELINE, req2, String.class);
    }

    @GetMapping("/markEntry/{id}")
    public void markEntry(@PathVariable UUID id) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {CHECKLIST_PORT};
        service.pingCheck(ports,restTemplate);
        restTemplate.getForObject(INTERNET_PORT + ":" + CHECKLIST_PORT + "/checklist/markEntry/"+id, String.class);
    }
}
