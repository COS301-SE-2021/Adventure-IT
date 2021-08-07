package com.adventureit.maincontroller.Controller;


import com.adventureit.checklist.Requests.AddChecklistEntryRequest;
import com.adventureit.checklist.Requests.CreateChecklistRequest;
import com.adventureit.checklist.Requests.EditChecklistEntryRequest;
import com.adventureit.checklist.Responses.ChecklistEntryResponseDTO;
import com.adventureit.checklist.Responses.ChecklistResponseDTO;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/checklist")
public class MainControllerChecklistReroute {

    private RestTemplate restTemplate = new RestTemplate();

    private final String IP = "localhost";
    private final String timelinePort = "9012";
    private final String checklistPort = "9008";

    @GetMapping("/test")
    String test(){
        return "Checklist Controller is functional";
    }



    @GetMapping("/viewChecklistsByAdventure/{id}")
    public List<ChecklistResponseDTO> viewChecklistsByAdventure(@PathVariable UUID id){
        return restTemplate.getForObject("http://"+ IP + ":" + checklistPort + "/checklist/viewChecklistsByAdventure/"+id, List.class);
    }

    @GetMapping("/viewChecklist/{id}")
    public List<ChecklistEntryResponseDTO> viewCheckist(@PathVariable UUID id) throws Exception {
        return restTemplate.getForObject("http://"+ IP + ":" + checklistPort + "/checklist/viewChecklist/"+id, List.class);
    }

    @GetMapping("/softDelete/{id}/{userID}")
    public String softDelete(@PathVariable UUID id, @PathVariable UUID userID) throws Exception {
        return restTemplate.getForObject("http://"+ IP + ":" + checklistPort + "/checklist/softDelete/"+id+"/"+userID, String.class);
    }
    //
    @GetMapping("/viewTrash/{id}")
    public List<ChecklistResponseDTO> viewTrash(@PathVariable UUID id) throws Exception {
        return restTemplate.getForObject("http://"+ IP + ":" + checklistPort + "/checklist/viewTrash/"+id, List.class);
    }

    @GetMapping("/restoreChecklist/{id}/{userID}")
    public String restoreChecklist(@PathVariable UUID id,@PathVariable UUID userID) throws Exception {
        return restTemplate.getForObject("http://"+ IP + ":" + checklistPort + "/checklist/restoreChecklist/"+id+"/"+userID, String.class);
    }

    @GetMapping("/hardDelete/{id}/{userID}")
    public String hardDelete(@PathVariable UUID id,@PathVariable UUID userID) throws Exception {
        return restTemplate.getForObject("http://"+ IP + ":" + checklistPort + "/checklist/hardDelete/"+id+"/"+userID, String.class);
    }

    @PostMapping("/create")
    public String createChecklist(@RequestBody CreateChecklistRequest req) throws Exception {
        return restTemplate.postForObject("http://"+ IP + ":" + checklistPort + "/budget/create/", req, String.class);
    }

    @PostMapping("/addEntry")
    public String addEntry(@RequestBody AddChecklistEntryRequest req) throws Exception {
        return restTemplate.postForObject("http://"+ IP + ":" + checklistPort + "/budget/addEntry/", req, String.class);
    }

    @GetMapping("/removeEntry/{id}")
    public String removeEntry(@PathVariable UUID id) throws Exception {
        return restTemplate.getForObject("http://"+ IP + ":" + checklistPort + "/checklist/removeEntry/"+id, String.class);
    }

    @PostMapping("/editEntry")
    public String editEntry(@RequestBody EditChecklistEntryRequest req) throws Exception {
        return restTemplate.postForObject("http://"+ IP + ":" + checklistPort + "/budget/editEntry/", req, String.class);
    }

    @GetMapping("/markEntry/{id}")
    public void markEntry(@PathVariable UUID id) throws Exception {
       restTemplate.getForObject("http://"+ IP + ":" + checklistPort + "/checklist/markEntry/"+id, String.class);
    }
}
