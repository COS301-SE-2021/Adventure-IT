package com.adventureit.maincontroller.controller;


import com.adventureit.maincontroller.exceptions.ControllerNotAvailable;
import com.adventureit.maincontroller.service.MainControllerServiceImplementation;
import com.adventureit.shareddtos.budget.requests.AddUTOExpenseEntryRequest;
import com.adventureit.shareddtos.budget.requests.AddUTUExpenseEntryRequest;
import com.adventureit.shareddtos.budget.requests.CreateBudgetRequest;
import com.adventureit.shareddtos.budget.requests.EditBudgetRequest;
import com.adventureit.shareddtos.budget.responses.BudgetResponseDTO;
import com.adventureit.shareddtos.budget.responses.ReportResponseDTO;
import com.adventureit.shareddtos.budget.responses.ViewBudgetResponse;
import com.adventureit.shareddtos.timeline.TimelineType;
import com.adventureit.shareddtos.timeline.requests.CreateTimelineRequest;
import com.adventureit.shareddtos.user.responses.GetUserByUUIDDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/budget")
public class MainControllerBudgetReroute {

    private final RestTemplate restTemplate = new RestTemplate();
    private final MainControllerServiceImplementation service;

    private static final String INTERNET_PORT = "http://localhost";
    private static final String TIMELINE_PORT = "9012";
    private static final String BUDGET_PORT = "9007";
    private static final String CREATE_BUDGET ="/budget/getBudgetByBudgetId/";
    private static final String GET_USER = "/user/getUser/";
    private static final String CREATE_TIMELINE = "/timeline/createTimeline";
    private static final String USER_PORT = "9002";
    private static final String BUDGET_M = " budget.";

    @Autowired
    public MainControllerBudgetReroute(MainControllerServiceImplementation service) {
        this.service = service;
    }

    @PostMapping(value ="/create")
    public String createBudget(@RequestBody CreateBudgetRequest req) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {BUDGET_PORT, USER_PORT, TIMELINE_PORT};
        service.pingCheck(ports,restTemplate);
        String id = req.getCreatorID().toString();
        if(id.equals("")) {
            throw new ControllerNotAvailable("Empty Error");
        }
        restTemplate.postForObject(INTERNET_PORT + ":" + BUDGET_PORT + "/budget/create/", req, String.class);
        GetUserByUUIDDTO user = restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + GET_USER + id, GetUserByUUIDDTO.class);
        assert user != null;
        CreateTimelineRequest req2 = new CreateTimelineRequest(req.getAdventureID(), TimelineType.BUDGET,user.getUsername()+" created a new budget for "+req.getName());
        return restTemplate.postForObject(INTERNET_PORT + ":" + TIMELINE_PORT + CREATE_TIMELINE, req2, String.class);
    }

    @GetMapping("/hardDelete/{id}/{userID}")
    public String hardDelete(@PathVariable UUID id, @PathVariable UUID userID) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {BUDGET_PORT, USER_PORT, TIMELINE_PORT};
        service.pingCheck(ports,restTemplate);
        BudgetResponseDTO response = restTemplate.getForObject(INTERNET_PORT + ":" + BUDGET_PORT + CREATE_BUDGET +id, BudgetResponseDTO.class);
        restTemplate.getForObject(INTERNET_PORT + ":" + BUDGET_PORT + "/budget/hardDelete/"+id+"/"+userID, String.class);
        GetUserByUUIDDTO user = restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + GET_USER + userID, GetUserByUUIDDTO.class);
        assert response != null;
        assert user != null;
        CreateTimelineRequest req2 = new CreateTimelineRequest(response.getAdventureID(), TimelineType.BUDGET,user.getUsername()+" deleted the "+response.getName()+BUDGET_M);
        return restTemplate.postForObject(INTERNET_PORT + ":" + TIMELINE_PORT + CREATE_TIMELINE, req2, String.class);
 }

    @PostMapping("/editBudget")
    public String editBudget(@RequestBody EditBudgetRequest req) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {BUDGET_PORT, USER_PORT, TIMELINE_PORT};
        service.pingCheck(ports,restTemplate);
        restTemplate.postForObject(INTERNET_PORT + ":" + BUDGET_PORT + "/budget/editBudget/", req, String.class);
        GetUserByUUIDDTO user = restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + GET_USER + req.getUserId(), GetUserByUUIDDTO.class);
        UUID budgetID = req.getBudgetID();
        assert budgetID != null;
        BudgetResponseDTO response = restTemplate.getForObject(INTERNET_PORT + ":" + BUDGET_PORT + CREATE_BUDGET +budgetID, BudgetResponseDTO.class);
        assert response != null;
        UUID adventureId = response.getAdventureID();
        assert user != null;
        CreateTimelineRequest req2 = new CreateTimelineRequest(adventureId, TimelineType.BUDGET,user.getUsername()+" edited the "+response.getName()+BUDGET_M );
        return restTemplate.postForObject(INTERNET_PORT + ":" + TIMELINE_PORT + CREATE_TIMELINE, req2, String.class);
    }

    @GetMapping("/removeEntry/{id}/{userId}")
    public String removeEntry(@PathVariable UUID id,@PathVariable UUID userId) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {BUDGET_PORT, USER_PORT, TIMELINE_PORT};
        service.pingCheck(ports,restTemplate);
        BudgetResponseDTO response = restTemplate.getForObject(INTERNET_PORT + ":" + BUDGET_PORT + "/budget/getBudgetByBudgetEntryId/"+id, BudgetResponseDTO.class);
        GetUserByUUIDDTO user = restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + GET_USER + userId, GetUserByUUIDDTO.class);
        restTemplate.getForObject(INTERNET_PORT + ":" + BUDGET_PORT + "/budget/removeEntry/"+id, String.class);
        assert response != null;
        UUID adventureId =response.getAdventureID();
        String name = response.getName();
        assert user != null;
        CreateTimelineRequest req2 = new CreateTimelineRequest(adventureId, TimelineType.BUDGET,user.getUsername()+" deleted an entry from the "+name+BUDGET_M );
        return restTemplate.postForObject(INTERNET_PORT + ":" + TIMELINE_PORT + CREATE_TIMELINE, req2, String.class);
    }

    @GetMapping("/viewBudgetsByAdventure/{id}")
    public List<BudgetResponseDTO> viewBudgetsByAdventure(@PathVariable UUID id) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {BUDGET_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(INTERNET_PORT + ":" + BUDGET_PORT + "/budget/viewBudgetsByAdventure/"+id, List.class);
    }

    @GetMapping("/viewBudget/{id}")
    public List<ViewBudgetResponse> viewBudget(@PathVariable UUID id) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {BUDGET_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(INTERNET_PORT + ":" + BUDGET_PORT + "/budget/viewBudget/"+id, List.class);
    }

    @GetMapping("/softDelete/{id}/{userID}")
    public String softDelete(@PathVariable UUID id, @PathVariable UUID userID) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {BUDGET_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(INTERNET_PORT + ":" + BUDGET_PORT + "/budget/softDelete/"+id+"/"+userID, String.class);
    }

    @GetMapping("/viewTrash/{id}")
    public List<BudgetResponseDTO> viewTrash(@PathVariable UUID id) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {BUDGET_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(INTERNET_PORT + ":" + BUDGET_PORT + "/budget/viewTrash/"+id, List.class);

    }

    @GetMapping("/restoreBudget/{id}/{userID}")
    public String restoreBudget(@PathVariable UUID id, @PathVariable UUID userID) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {BUDGET_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(INTERNET_PORT + ":" + BUDGET_PORT + "/budget/restoreBudget/"+id+"/"+userID, String.class);
    }


    @PostMapping("/addUTOExpense")
    public String addUTOExpense(@RequestBody AddUTOExpenseEntryRequest req) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {BUDGET_PORT, TIMELINE_PORT};
        service.pingCheck(ports,restTemplate);
        String returnString = restTemplate.postForObject(INTERNET_PORT + ":" + BUDGET_PORT + "/budget/addUTOExpense/", req, String.class);
        BudgetResponseDTO response = restTemplate.getForObject(INTERNET_PORT + ":" + BUDGET_PORT + CREATE_BUDGET +req.getEntryContainerID(), BudgetResponseDTO.class);
        assert response != null;
        UUID adventureId =response.getAdventureID();
        String name = response.getName();
        CreateTimelineRequest req2 = new CreateTimelineRequest(adventureId, TimelineType.BUDGET,req.getPayer()+" added a new entry to the "+name+BUDGET_M );
        restTemplate.postForObject(INTERNET_PORT + ":" + TIMELINE_PORT + CREATE_TIMELINE, req2, String.class);
        return returnString;
    }

    @PostMapping("/addUTUExpense")
    public String addUTUExpense(@RequestBody AddUTUExpenseEntryRequest req) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {BUDGET_PORT, TIMELINE_PORT};
        service.pingCheck(ports,restTemplate);
        String returnString = restTemplate.postForObject(INTERNET_PORT + ":" + BUDGET_PORT + "/budget/addUTUExpense/", req, String.class);
        BudgetResponseDTO response = restTemplate.getForObject(INTERNET_PORT + ":" + BUDGET_PORT + CREATE_BUDGET +req.getEntryContainerID(), BudgetResponseDTO.class);
        assert response != null;
        UUID adventureId =response.getAdventureID();
        String name = response.getName();
        CreateTimelineRequest req2 = new CreateTimelineRequest(adventureId, TimelineType.BUDGET,req.getPayer()+" added a new entry to the "+name+BUDGET_M );
        restTemplate.postForObject(INTERNET_PORT + ":" + TIMELINE_PORT + CREATE_TIMELINE, req2, String.class);
        return returnString;
    }


    @GetMapping("/calculateExpense/{budgetID}/{userName}")
    public double calculateExpense(@PathVariable UUID budgetID, @PathVariable String userName) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {BUDGET_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(INTERNET_PORT + ":" + BUDGET_PORT + "/budget/calculateExpense/"+budgetID+"/"+userName, double.class);
    }

    @GetMapping("/getEntriesPerCategory/{id}")
    public List<Integer> getEntriesPerCategory(@PathVariable UUID id) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {BUDGET_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(INTERNET_PORT + ":" + BUDGET_PORT + "/budget/getEntriesPerCategory/"+id, List.class);
    }

    @GetMapping("/getReportList/{id}")
    public List<String> getReportList(@PathVariable UUID id) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {BUDGET_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(INTERNET_PORT + ":" + BUDGET_PORT + "/budget/getReportList/"+id, List.class);
    }

    @GetMapping("/generateIndividualReport/{id}/{userName}")
    public List<ReportResponseDTO> generateIndividualReport(@PathVariable UUID id, @PathVariable String userName) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {BUDGET_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(INTERNET_PORT + ":" + BUDGET_PORT + "/budget/generateIndividualReport/"+id+"/"+userName, List.class);
    }

    @GetMapping("/getBudgetByBudgetId/{id}")
    public BudgetResponseDTO generateIndividualReport(@PathVariable UUID id) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {BUDGET_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(INTERNET_PORT + ":" + BUDGET_PORT + CREATE_BUDGET +id, BudgetResponseDTO.class);
    }





}
