package com.adventureit.maincontroller.Controller;


import com.adventureit.adventureservice.Responses.CreateAdventureResponse;
import com.adventureit.budgetservice.Entity.Budget;
import com.adventureit.budgetservice.Requests.*;
import com.adventureit.budgetservice.Responses.*;
//import com.adventureit.timelineservice.Entity.TimelineType;
//import com.adventureit.timelineservice.Requests.CreateTimelineRequest;
import com.adventureit.timelineservice.Entity.TimelineType;
import com.adventureit.timelineservice.Requests.CreateTimelineRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/budget")
public class MainControllerBudgetReroute {

    private RestTemplate restTemplate = new RestTemplate();

    private final String IP = "localhost";
    private final String timelinePort = "9012";
    private final String budgetPort = "9007";

    @PostMapping(value ="/create")
    public String createBudget(@RequestBody CreateBudgetRequest req) {
        restTemplate.postForObject("http://"+ IP + ":" + budgetPort + "/budget/create/", req, String.class);
        CreateTimelineRequest req2 = new CreateTimelineRequest(req.getAdventureID(),req.getCreatorID(), TimelineType.BUDGET,req.getName()+" budget has been created" );
        String timelineResponse = restTemplate.postForObject("http://"+ IP + ":" + timelinePort + "/timeline/createTimeline", req2, String.class);
        return timelineResponse;
    }

    @GetMapping("/hardDelete/{id}/{userID}")
    public String hardDelete(@PathVariable UUID id, @PathVariable UUID userID){
        restTemplate.getForObject("http://"+ IP + ":" + budgetPort + "/hardDelete/"+id+"/"+userID, String.class);
        CreateTimelineRequest req2 = new CreateTimelineRequest(id,userID, TimelineType.BUDGET,"Budget: "+id+" has been deleted" );
        return restTemplate.postForObject("http://"+ IP + ":" + timelinePort + "/timeline/createTimeline", req2, String.class);
 }

    @PostMapping("/editBudget")
    public String editBudget(@RequestBody EditBudgetRequest req){
        return restTemplate.postForObject("http://"+ IP + ":" + budgetPort + "/budget/editBudget/", req, String.class);
    }

    @GetMapping("/removeEntry/{id}/{budgetID}")
    public String removeEntry(@PathVariable UUID id, @PathVariable UUID budgetID){
        return restTemplate.getForObject("http://"+ IP + ":" + budgetPort + "/removeEntry/"+id+"/"+budgetID, String.class);
    }

    @GetMapping("/viewBudgetsByAdventure/{id}")
    public List<BudgetResponseDTO> viewBudgetsByAdventure(@PathVariable UUID id){
        return restTemplate.getForObject("http://"+ IP + ":" + budgetPort + "/budget/viewBudgetsByAdventure/"+id, List.class);
    }

    @GetMapping("/viewBudget/{id}")
    public List<ViewBudgetResponse> viewBudget(@PathVariable UUID id) throws Exception {
        return restTemplate.getForObject("http://"+ IP + ":" + budgetPort + "/budget/viewBudget/"+id, List.class);
    }

    @GetMapping("/softDelete/{id}/{userID}")
    public String softDelete(@PathVariable UUID id, @PathVariable UUID userID) throws Exception {
        return restTemplate.getForObject("http://"+ IP + ":" + budgetPort + "/budget/softDelete/"+id+"/"+userID, String.class);
    }

    @GetMapping("/viewTrash/{id}")
    public List<BudgetResponseDTO> viewTrash(@PathVariable UUID id) throws Exception {
        return restTemplate.getForObject("http://"+ IP + ":" + budgetPort + "/budget/viewTrash/"+id, List.class);

    }

    @GetMapping("/restoreBudget/{id}/{userID}")
    public String restoreBudget(@PathVariable UUID id, @PathVariable UUID userID){
        return restTemplate.getForObject("http://"+ IP + ":" + budgetPort + "/budget/restoreBudget/"+id+"/"+userID, String.class);
    }


    @PostMapping("/addUTOExpense")
    public String addUTOExpense(@RequestBody AddUTOExpenseEntryRequest req) throws Exception {
        return restTemplate.postForObject("http://"+ IP + ":" + budgetPort + "/budget/addUTOExpense/", req, String.class);

    }

    @PostMapping("/addUTUExpense")
    public String addUTUExpense(@RequestBody AddUTUExpenseEntryRequest req) throws Exception {
        return restTemplate.postForObject("http://"+ IP + ":" + budgetPort + "/budget/addUTUExpense/", req, String.class);

    }


    @GetMapping("/calculateExpense/{budgetID}/{userName}")
    public double calculateExpense(@PathVariable UUID budgetID, @PathVariable String userName){
        return restTemplate.getForObject("http://"+ IP + ":" + budgetPort + "/budget/calculateExpense/"+budgetID+"/"+userName, double.class);
    }

    @GetMapping("/getEntriesPerCategory/{id}")
    public List<Integer> getEntriesPerCategory(@PathVariable UUID id) throws Exception {
        return restTemplate.getForObject("http://"+ IP + ":" + budgetPort + "/budget/getEntriesPerCategory/"+id, List.class);
    }

    @GetMapping("/getReportList/{id}")
    public List<String> getReportList(@PathVariable UUID id) throws Exception {
        return restTemplate.getForObject("http://"+ IP + ":" + budgetPort + "/budget/restoreBudget/"+id, List.class);
    }

    @GetMapping("/generateIndividualReport/{id}/{userName}")
    public List<ReportResponseDTO> generateIndividualReport(@PathVariable UUID id,@PathVariable String userName) throws Exception {
        return restTemplate.getForObject("http://"+ IP + ":" + budgetPort + "/budget/restoreBudget/"+id+"/"+userName, List.class);
    }



}
