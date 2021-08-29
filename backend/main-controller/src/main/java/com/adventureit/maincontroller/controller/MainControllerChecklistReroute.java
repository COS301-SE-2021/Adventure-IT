package com.adventureit.maincontroller.controller;


import com.adventureit.checklist.requests.AddChecklistEntryRequest;
import com.adventureit.checklist.requests.ChecklistDTO;
import com.adventureit.checklist.requests.CreateChecklistRequest;
import com.adventureit.checklist.requests.EditChecklistEntryRequest;
import com.adventureit.checklist.responses.ChecklistEntryResponseDTO;
import com.adventureit.checklist.responses.ChecklistResponseDTO;
import com.adventureit.timelineservice.entity.TimelineType;
import com.adventureit.timelineservice.requests.CreateTimelineRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/checklist")
public class MainControllerChecklistReroute {

    private final RestTemplate restTemplate = new RestTemplate();

    private final String IP = "http://localhost";
    private final String timelinePort = "9012";
    private final String checklistPort = "9008";

    @GetMapping("/test")
    public String test(){
        return "Checklist Controller is functional";
    }



    @GetMapping("/viewChecklistsByAdventure/{id}")
    public List<ChecklistResponseDTO> viewChecklistsByAdventure(@PathVariable UUID id){
        return restTemplate.getForObject(IP + ":" + checklistPort + "/checklist/viewChecklistsByAdventure/"+id, List.class);
    }

    @GetMapping("/viewChecklist/{id}")
    public List<ChecklistEntryResponseDTO> viewCheckist(@PathVariable UUID id){
        return restTemplate.getForObject(IP + ":" + checklistPort + "/checklist/viewChecklist/"+id, List.class);
    }

    @GetMapping("/softDelete/{id}/{userID}")
    public String softDelete(@PathVariable UUID id, @PathVariable UUID userID){
        return restTemplate.getForObject(IP + ":" + checklistPort + "/checklist/softDelete/"+id+"/"+userID, String.class);
    }
    //
    @GetMapping("/viewTrash/{id}")
    public List<ChecklistResponseDTO> viewTrash(@PathVariable UUID id){
        return restTemplate.getForObject(IP + ":" + checklistPort + "/checklist/viewTrash/"+id, List.class);
    }

    @GetMapping("/restoreChecklist/{id}/{userID}")
    public String restoreChecklist(@PathVariable UUID id,@PathVariable UUID userID){
        return restTemplate.getForObject(IP + ":" + checklistPort + "/checklist/restoreChecklist/"+id+"/"+userID, String.class);
    }

    @GetMapping("/hardDelete/{id}/{userID}")
    public String hardDelete(@PathVariable UUID id,@PathVariable UUID userID){
        restTemplate.getForObject(IP + ":" + checklistPort + "/checklist/hardDelete/"+id+"/"+userID, String.class);
        CreateTimelineRequest req2 = new CreateTimelineRequest(id, TimelineType.BUDGET,"Checklist("+id+"): has been deleted" );
        return restTemplate.postForObject(IP + ":" + timelinePort + "/timeline/createTimeline", req2, String.class);
    }

    @PostMapping("/create")
    public String createChecklist(@RequestBody CreateChecklistRequest req){
        restTemplate.postForObject(IP + ":" + checklistPort + "/checklist/create/", req, String.class);
        CreateTimelineRequest req2 = new CreateTimelineRequest(req.getAdventureID(), TimelineType.CHECKLIST,"Checklist("+req.getTitle()+") has been created" );
        return restTemplate.postForObject(IP + ":" + timelinePort + "/timeline/createTimeline", req2, String.class);
    }

    @PostMapping("/addEntry")
    public String addEntry(@RequestBody AddChecklistEntryRequest req){
        String returnString = restTemplate.postForObject(IP + ":" + checklistPort + "/checklist/addEntry/", req, String.class);
        UUID checklistID = req.getEntryContainerID();
        ChecklistDTO checklist = restTemplate.getForObject(IP + ":" + checklistPort + "/checklist/getChecklist/"+checklistID, ChecklistDTO.class);
        assert checklist != null;
        UUID adventureId = checklist.getAdventureID();
        CreateTimelineRequest req2 = new CreateTimelineRequest(adventureId, TimelineType.CHECKLIST,"An entry has been added to the "+req.getTitle()+" checklist." );
        restTemplate.postForObject(IP + ":" + timelinePort + "/timeline/createTimeline", req2, String.class);
        return returnString;

    }

    @GetMapping("/removeEntry/{id}")
    public String removeEntry(@PathVariable UUID id){
        ChecklistDTO checklist = restTemplate.getForObject(IP + ":" + checklistPort + "/checklist/getChecklistByEntry/"+id, ChecklistDTO.class);
        assert checklist != null;
        UUID adventureId = checklist.getAdventureID();
        CreateTimelineRequest req2 = new CreateTimelineRequest(adventureId, TimelineType.CHECKLIST,"An entry has been removed to the "+checklist.getTitle()+" checklist." );
        restTemplate.postForObject(IP + ":" + timelinePort + "/timeline/createTimeline", req2, String.class);
        return restTemplate.getForObject(IP + ":" + checklistPort + "/checklist/removeEntry/"+id, String.class);
    }

    @PostMapping("/editEntry")
    public String editEntry(@RequestBody EditChecklistEntryRequest req){
        restTemplate.postForObject(IP + ":" + checklistPort + "/checklist/editEntry/", req, String.class);
        UUID checklistID = req.getEntryContainerID();
        ChecklistDTO checklist = restTemplate.getForObject(IP + ":" + checklistPort + "/checklist/getChecklist/"+checklistID, ChecklistDTO.class);
        assert checklist != null;
        UUID adventureId = checklist.getAdventureID();
        CreateTimelineRequest req2 = new CreateTimelineRequest(adventureId, TimelineType.BUDGET,"Checklist("+req.getTitle()+"): has been edited" );
        return restTemplate.postForObject(IP + ":" + timelinePort + "/timeline/createTimeline", req2, String.class);
    }

    @GetMapping("/markEntry/{id}")
    public void markEntry(@PathVariable UUID id){
       restTemplate.getForObject(IP + ":" + checklistPort + "/checklist/markEntry/"+id, String.class);
    }
}
