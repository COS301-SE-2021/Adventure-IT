package com.adventureit.maincontroller.controller;


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
    private MainControllerServiceImplementation service;

    private final String IP = "http://localhost";
    private final String timelinePort = "9012";
    private final String checklistPort = "9008";
    private final String userPort = "9002";

    @Autowired
    public MainControllerChecklistReroute(MainControllerServiceImplementation service) {
        this.service = service;
    }

    @GetMapping("/test")
    public String test(){
        return "Checklist Controller is functional";
    }

    @GetMapping("/viewChecklistsByAdventure/{id}")
    public List<ChecklistResponseDTO> viewChecklistsByAdventure(@PathVariable UUID id) throws Exception {
        String[] ports = {checklistPort};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(IP + ":" + checklistPort + "/checklist/viewChecklistsByAdventure/"+id, List.class);
    }

    @GetMapping("/viewChecklist/{id}")
    public List<ChecklistEntryResponseDTO> viewCheckist(@PathVariable UUID id) throws Exception {
        String[] ports = {checklistPort};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(IP + ":" + checklistPort + "/checklist/viewChecklist/"+id, List.class);
    }

    @GetMapping("/softDelete/{id}/{userID}")
    public String softDelete(@PathVariable UUID id, @PathVariable UUID userID) throws Exception {
        String[] ports = {checklistPort};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(IP + ":" + checklistPort + "/checklist/softDelete/"+id+"/"+userID, String.class);
    }

    @GetMapping("/viewTrash/{id}")
    public List<ChecklistResponseDTO> viewTrash(@PathVariable UUID id) throws Exception {
        String[] ports = {checklistPort};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(IP + ":" + checklistPort + "/checklist/viewTrash/"+id, List.class);
    }

    @GetMapping("/restoreChecklist/{id}/{userID}")
    public String restoreChecklist(@PathVariable UUID id,@PathVariable UUID userID) throws Exception {
        String[] ports = {checklistPort};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(IP + ":" + checklistPort + "/checklist/restoreChecklist/"+id+"/"+userID, String.class);
    }

    @GetMapping("/hardDelete/{id}/{userID}")
    public String hardDelete(@PathVariable UUID id,@PathVariable UUID userID) throws Exception {
        String[] ports = {userPort,checklistPort,timelinePort};
        service.pingCheck(ports,restTemplate);
        GetUserByUUIDDTO user = restTemplate.getForObject(IP + ":" + userPort + "/user/getUser/"+userID, GetUserByUUIDDTO.class);
        ChecklistDTO checklist = restTemplate.getForObject(IP + ":" + checklistPort + "/checklist/getChecklist/"+id, ChecklistDTO.class);
        restTemplate.getForObject(IP + ":" + checklistPort + "/checklist/hardDelete/"+id+"/"+userID, String.class);
        CreateTimelineRequest req2 = new CreateTimelineRequest(id, TimelineType.BUDGET,user.getUsername()+" deleted the "+checklist+" checklist." );
        return restTemplate.postForObject(IP + ":" + timelinePort + "/timeline/createTimeline", req2, String.class);
    }

    @PostMapping("/create")
    public String createChecklist(@RequestBody CreateChecklistRequest req) throws Exception {
        String[] ports = {userPort,checklistPort,timelinePort};
        service.pingCheck(ports,restTemplate);
        GetUserByUUIDDTO user = restTemplate.getForObject(IP + ":" + userPort + "/user/getUser/"+req.getCreatorID(), GetUserByUUIDDTO.class);
        restTemplate.postForObject(IP + ":" + checklistPort + "/checklist/create/", req, String.class);
        CreateTimelineRequest req2 = new CreateTimelineRequest(req.getAdventureID(), TimelineType.CHECKLIST,user.getUsername()+" created a new checklist for "+req.getTitle()+"." );
        return restTemplate.postForObject(IP + ":" + timelinePort + "/timeline/createTimeline", req2, String.class);
    }

    @SneakyThrows
    @PostMapping("/addEntry")
    public String addEntry(@RequestBody AddChecklistEntryRequest req){
        String[] ports = {userPort,checklistPort,timelinePort};
        service.pingCheck(ports,restTemplate);
        GetUserByUUIDDTO user = restTemplate.getForObject(IP + ":" + userPort + "/user/getUser/"+req.getUserId(), GetUserByUUIDDTO.class);
        String returnString = restTemplate.postForObject(IP + ":" + checklistPort + "/checklist/addEntry/", req, String.class);
        UUID checklistID = req.getEntryContainerID();
        ChecklistDTO checklist = restTemplate.getForObject(IP + ":" + checklistPort + "/checklist/getChecklist/"+checklistID, ChecklistDTO.class);
        assert checklist != null;
        UUID adventureId = checklist.getAdventureID();
        CreateTimelineRequest req2 = new CreateTimelineRequest(adventureId, TimelineType.CHECKLIST,user.getUsername()+" added an entry to the "+checklist.getTitle()+" checklist." );
        restTemplate.postForObject(IP + ":" + timelinePort + "/timeline/createTimeline", req2, String.class);
        return returnString;

    }

    @GetMapping("/removeEntry/{id}/{userId}")
    public String removeEntry(@PathVariable UUID id,@PathVariable UUID userId) throws Exception {
        String[] ports = {userPort,checklistPort,timelinePort};
        service.pingCheck(ports,restTemplate);
        GetUserByUUIDDTO user = restTemplate.getForObject(IP + ":" + userPort + "/user/getUser/"+userId, GetUserByUUIDDTO.class);
        ChecklistDTO checklist = restTemplate.getForObject(IP + ":" + checklistPort + "/checklist/getChecklistByEntry/"+id, ChecklistDTO.class);
        assert checklist != null;
        UUID adventureId = checklist.getAdventureID();
        CreateTimelineRequest req2 = new CreateTimelineRequest(adventureId, TimelineType.CHECKLIST,user.getUsername()+" deleted an entry from the "+checklist.getTitle()+" checklist." );
        restTemplate.postForObject(IP + ":" + timelinePort + "/timeline/createTimeline", req2, String.class);
        return restTemplate.getForObject(IP + ":" + checklistPort + "/checklist/removeEntry/"+id, String.class);
    }

    @PostMapping("/editEntry")
    public String editEntry(@RequestBody EditChecklistEntryRequest req) throws Exception {
        String[] ports = {checklistPort,userPort,timelinePort};
        service.pingCheck(ports,restTemplate);
        restTemplate.postForObject(IP + ":" + checklistPort + "/checklist/editEntry/", req, String.class);
        GetUserByUUIDDTO user = restTemplate.getForObject(IP + ":" + userPort + "/user/getUser/"+req.getUserId(), GetUserByUUIDDTO.class);
        UUID checklistID = req.getEntryContainerID();
        ChecklistDTO checklist = restTemplate.getForObject(IP + ":" + checklistPort + "/checklist/getChecklist/"+checklistID, ChecklistDTO.class);
        assert checklist != null;
        UUID adventureId = checklist.getAdventureID();
        CreateTimelineRequest req2 = new CreateTimelineRequest(adventureId, TimelineType.BUDGET,user.getUsername()+" edited the "+checklist.getTitle()+" checklist.");
        return restTemplate.postForObject(IP + ":" + timelinePort + "/timeline/createTimeline", req2, String.class);
    }

    @GetMapping("/markEntry/{id}")
    public void markEntry(@PathVariable UUID id) throws Exception {
        String[] ports = {checklistPort};
        service.pingCheck(ports,restTemplate);
        restTemplate.getForObject(IP + ":" + checklistPort + "/checklist/markEntry/"+id, String.class);
    }
}
