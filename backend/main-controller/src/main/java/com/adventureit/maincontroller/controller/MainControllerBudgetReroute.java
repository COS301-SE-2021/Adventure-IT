package com.adventureit.maincontroller.controller;


import com.adventureit.budgetservice.requests.AddUTOExpenseEntryRequest;
import com.adventureit.budgetservice.requests.AddUTUExpenseEntryRequest;
import com.adventureit.budgetservice.requests.CreateBudgetRequest;
import com.adventureit.budgetservice.requests.EditBudgetRequest;
import com.adventureit.budgetservice.responses.BudgetResponseDTO;
import com.adventureit.budgetservice.responses.ReportResponseDTO;
import com.adventureit.budgetservice.responses.ViewBudgetResponse;
import com.adventureit.timelineservice.entity.TimelineType;
import com.adventureit.timelineservice.requests.CreateTimelineRequest;
import com.adventureit.userservice.responses.GetUserByUUIDDTO;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/budget")
public class MainControllerBudgetReroute {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String IP = "http://localhost";
    private final String timelinePort = "9012";
    private final String budgetPort = "9007";
    private final String createBudget ="/budget/getBudgetByBudgetId/";
    private final String userPort = "9002";

    @PostMapping(value ="/create")
    public String createBudget(@RequestBody CreateBudgetRequest req) {
        restTemplate.postForObject(IP + ":" + budgetPort + "/budget/create/", req, String.class);
        GetUserByUUIDDTO user = restTemplate.getForObject(IP + ":" + userPort + "/user/getUser/"+req.getCreatorID(), GetUserByUUIDDTO.class);
        CreateTimelineRequest req2 = new CreateTimelineRequest(req.getAdventureID(), TimelineType.BUDGET,user.getUsername()+" created a new budget for "+req.getName());
        return restTemplate.postForObject(IP + ":" + timelinePort + "/timeline/createTimeline", req2, String.class);
    }

    @GetMapping("/hardDelete/{id}/{userID}")
    public String hardDelete(@PathVariable UUID id, @PathVariable UUID userID){
        BudgetResponseDTO response = restTemplate.getForObject(IP + ":" + budgetPort + createBudget+id, BudgetResponseDTO.class);
        restTemplate.getForObject(IP + ":" + budgetPort + "/budget/hardDelete/"+id+"/"+userID, String.class);
        GetUserByUUIDDTO user = restTemplate.getForObject(IP + ":" + userPort + "/user/getUser/"+userID, GetUserByUUIDDTO.class);
        assert response != null;
        CreateTimelineRequest req2 = new CreateTimelineRequest(response.getAdventureID(), TimelineType.BUDGET,user.getUsername()+" deleted the "+response.getName()+" budget.");
        return restTemplate.postForObject(IP + ":" + timelinePort + "/timeline/createTimeline", req2, String.class);
 }

    @PostMapping("/editBudget")
    public String editBudget(@RequestBody EditBudgetRequest req){
        restTemplate.postForObject(IP + ":" + budgetPort + "/budget/editBudget/", req, String.class);
        GetUserByUUIDDTO user = restTemplate.getForObject(IP + ":" + userPort + "/user/getUser/"+req.getUserId(), GetUserByUUIDDTO.class);
        UUID budgetID = req.getBudgetID();
        assert budgetID != null;
        BudgetResponseDTO response = restTemplate.getForObject(IP + ":" + budgetPort + createBudget+budgetID, BudgetResponseDTO.class);
        assert response != null;
        UUID adventureId = response.getAdventureID();
        CreateTimelineRequest req2 = new CreateTimelineRequest(adventureId, TimelineType.BUDGET,user.getUsername()+" edited the "+response.getName()+" budget." );
        return restTemplate.postForObject(IP + ":" + timelinePort + "/timeline/createTimeline", req2, String.class);
    }

    @GetMapping("/removeEntry/{id}/{userId}")
    public String removeEntry(@PathVariable UUID id,@PathVariable UUID userId){
        BudgetResponseDTO response = restTemplate.getForObject(IP + ":" + budgetPort + "/budget/getBudgetByBudgetEntryId/"+id, BudgetResponseDTO.class);
        GetUserByUUIDDTO user = restTemplate.getForObject(IP + ":" + userPort + "/user/getUser/"+userId, GetUserByUUIDDTO.class);
        restTemplate.getForObject(IP + ":" + budgetPort + "/budget/removeEntry/"+id, String.class);
        assert response != null;
        UUID adventureId =response.getAdventureID();
        String name = response.getName();
        CreateTimelineRequest req2 = new CreateTimelineRequest(adventureId, TimelineType.BUDGET,user.getUsername()+" deleted an entry from the "+name+" budget." );
        return restTemplate.postForObject(IP + ":" + timelinePort + "/timeline/createTimeline", req2, String.class);
    }

    @GetMapping("/viewBudgetsByAdventure/{id}")
    public List<BudgetResponseDTO> viewBudgetsByAdventure(@PathVariable UUID id){
        return restTemplate.getForObject(IP + ":" + budgetPort + "/budget/viewBudgetsByAdventure/"+id, List.class);
    }

    @GetMapping("/viewBudget/{id}")
    public List<ViewBudgetResponse> viewBudget(@PathVariable UUID id){
        return restTemplate.getForObject(IP + ":" + budgetPort + "/budget/viewBudget/"+id, List.class);
    }

    @GetMapping("/softDelete/{id}/{userID}")
    public String softDelete(@PathVariable UUID id, @PathVariable UUID userID){
        return restTemplate.getForObject(IP + ":" + budgetPort + "/budget/softDelete/"+id+"/"+userID, String.class);
    }

    @GetMapping("/viewTrash/{id}")
    public List<BudgetResponseDTO> viewTrash(@PathVariable UUID id){
        return restTemplate.getForObject(IP + ":" + budgetPort + "/budget/viewTrash/"+id, List.class);

    }

    @GetMapping("/restoreBudget/{id}/{userID}")
    public String restoreBudget(@PathVariable UUID id, @PathVariable UUID userID){
        return restTemplate.getForObject(IP + ":" + budgetPort + "/budget/restoreBudget/"+id+"/"+userID, String.class);
    }


    @PostMapping("/addUTOExpense")
    public String addUTOExpense(@RequestBody AddUTOExpenseEntryRequest req){
        String returnString = restTemplate.postForObject(IP + ":" + budgetPort + "/budget/addUTOExpense/", req, String.class);
        BudgetResponseDTO response = restTemplate.getForObject(IP + ":" + budgetPort + createBudget +req.getEntryContainerID(), BudgetResponseDTO.class);
        assert response != null;
        UUID adventureId =response.getAdventureID();
        String name = response.getName();
        CreateTimelineRequest req2 = new CreateTimelineRequest(adventureId, TimelineType.BUDGET,req.getPayer()+" added a new entry to the "+name+" budget." );
        restTemplate.postForObject(IP + ":" + timelinePort + "/timeline/createTimeline", req2, String.class);
        return returnString;
    }

    @PostMapping("/addUTUExpense")
    public String addUTUExpense(@RequestBody AddUTUExpenseEntryRequest req){
        String returnString = restTemplate.postForObject(IP + ":" + budgetPort + "/budget/addUTUExpense/", req, String.class);
        BudgetResponseDTO response = restTemplate.getForObject(IP + ":" + budgetPort + createBudget+req.getEntryContainerID(), BudgetResponseDTO.class);
        assert response != null;
        UUID adventureId =response.getAdventureID();
        String name = response.getName();
        CreateTimelineRequest req2 = new CreateTimelineRequest(adventureId, TimelineType.BUDGET,req.getPayer()+" added a new entry to the "+name+" budget." );
        restTemplate.postForObject(IP + ":" + timelinePort + "/timeline/createTimeline", req2, String.class);
        return returnString;
    }


    @GetMapping("/calculateExpense/{budgetID}/{userName}")
    public double calculateExpense(@PathVariable UUID budgetID, @PathVariable String userName){
        return restTemplate.getForObject(IP + ":" + budgetPort + "/budget/calculateExpense/"+budgetID+"/"+userName, double.class);
    }

    @GetMapping("/getEntriesPerCategory/{id}")
    public List<Integer> getEntriesPerCategory(@PathVariable UUID id){
        return restTemplate.getForObject(IP + ":" + budgetPort + "/budget/getEntriesPerCategory/"+id, List.class);
    }

    @GetMapping("/getReportList/{id}")
    public List<String> getReportList(@PathVariable UUID id) {
        return restTemplate.getForObject(IP + ":" + budgetPort + "/budget/getReportList/"+id, List.class);
    }

    @GetMapping("/generateIndividualReport/{id}/{userName}")
    public List<ReportResponseDTO> generateIndividualReport(@PathVariable UUID id, @PathVariable String userName){
        return restTemplate.getForObject(IP + ":" + budgetPort + "/budget/generateIndividualReport/"+id+"/"+userName, List.class);
    }

    @GetMapping("/getBudgetByBudgetId/{id}")
    public BudgetResponseDTO generateIndividualReport(@PathVariable UUID id) {
        return restTemplate.getForObject(IP + ":" + budgetPort + createBudget +id, BudgetResponseDTO.class);
    }





}
