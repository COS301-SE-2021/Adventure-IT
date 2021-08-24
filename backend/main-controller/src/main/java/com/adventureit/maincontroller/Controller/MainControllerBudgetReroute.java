package com.adventureit.maincontroller.Controller;


import com.adventureit.budgetservice.requests.AddUTOExpenseEntryRequest;
import com.adventureit.budgetservice.requests.AddUTUExpenseEntryRequest;
import com.adventureit.budgetservice.requests.CreateBudgetRequest;
import com.adventureit.budgetservice.requests.EditBudgetRequest;
import com.adventureit.budgetservice.responses.BudgetResponseDTO;
import com.adventureit.budgetservice.responses.ReportResponseDTO;
import com.adventureit.budgetservice.responses.ViewBudgetResponse;
import com.adventureit.timelineservice.Entity.TimelineType;
import com.adventureit.timelineservice.Requests.CreateTimelineRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
        CreateTimelineRequest req2 = new CreateTimelineRequest(req.getAdventureID(), TimelineType.BUDGET,req.getName()+" budget has been created" );
        String timelineResponse = restTemplate.postForObject("http://"+ IP + ":" + timelinePort + "/timeline/createTimeline", req2, String.class);
        return timelineResponse;
    }

    @GetMapping("/hardDelete/{id}/{userID}")
    public String hardDelete(@PathVariable UUID id, @PathVariable UUID userID){
        BudgetResponseDTO response = restTemplate.getForObject("http://"+ IP + ":" + budgetPort + "/budget/getBudgetByBudgetId/"+id, BudgetResponseDTO.class);
        restTemplate.getForObject("http://"+ IP + ":" + budgetPort + "/budget/hardDelete/"+id+"/"+userID, String.class);
        CreateTimelineRequest req2 = new CreateTimelineRequest(response.getAdventureID(), TimelineType.BUDGET,"Budget: "+id+" has been deleted" );
        return restTemplate.postForObject("http://"+ IP + ":" + timelinePort + "/timeline/createTimeline", req2, String.class);
 }

    @PostMapping("/editBudget")
    public String editBudget(@RequestBody EditBudgetRequest req){
        restTemplate.postForObject("http://"+ IP + ":" + budgetPort + "/budget/editBudget/", req, String.class);
        UUID budgetID = req.getBudgetID();
        UUID adventureId = restTemplate.getForObject("http://"+ IP + ":" + budgetPort + "/budget/getBudgetByBudgetEntryId/"+budgetID, BudgetResponseDTO.class).getAdventureID();
        CreateTimelineRequest req2 = new CreateTimelineRequest(adventureId, TimelineType.BUDGET,"Budget: "+req.getTitle()+" has been edited" );
        return restTemplate.postForObject("http://"+ IP + ":" + timelinePort + "/timeline/createTimeline", req2, String.class);
    }

    @GetMapping("/removeEntry/{id}")
    public String removeEntry(@PathVariable UUID id){
        BudgetResponseDTO response = restTemplate.getForObject("http://"+ IP + ":" + budgetPort + "/budget/getBudgetByBudgetEntryId/"+id, BudgetResponseDTO.class);
        restTemplate.getForObject("http://"+ IP + ":" + budgetPort + "/budget/removeEntry/"+id, String.class);

        UUID adventureId =response.getAdventureID();
        String name = response.getName();
        CreateTimelineRequest req2 = new CreateTimelineRequest(adventureId, TimelineType.BUDGET,"Budget: "+name+" has been deleted" );
        return restTemplate.postForObject("http://"+ IP + ":" + timelinePort + "/timeline/createTimeline", req2, String.class);
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
        String returnString = restTemplate.postForObject("http://"+ IP + ":" + budgetPort + "/budget/addUTOExpense/", req, String.class);
        BudgetResponseDTO response = restTemplate.getForObject("http://"+ IP + ":" + budgetPort + "/budget/getBudgetByBudgetId/"+req.getEntryContainerID(), BudgetResponseDTO.class);
        UUID adventureId =response.getAdventureID();
        String name = response.getName();
        CreateTimelineRequest req2 = new CreateTimelineRequest(adventureId, TimelineType.BUDGET,"Budget: "+name+" has been updated" );
        restTemplate.postForObject("http://"+ IP + ":" + timelinePort + "/timeline/createTimeline", req2, String.class);
        return returnString;
    }

    @PostMapping("/addUTUExpense")
    public String addUTUExpense(@RequestBody AddUTUExpenseEntryRequest req) throws Exception {
        String returnString = restTemplate.postForObject("http://"+ IP + ":" + budgetPort + "/budget/addUTUExpense/", req, String.class);
        BudgetResponseDTO response = restTemplate.getForObject("http://"+ IP + ":" + budgetPort + "/budget/getBudgetByBudgetId/"+req.getEntryContainerID(), BudgetResponseDTO.class);
        UUID adventureId =response.getAdventureID();
        String name = response.getName();
        CreateTimelineRequest req2 = new CreateTimelineRequest(adventureId, TimelineType.BUDGET,"Budget: "+name+" has been updated" );
        restTemplate.postForObject("http://"+ IP + ":" + timelinePort + "/timeline/createTimeline", req2, String.class);
        return returnString;
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
    public List<String> getReportList(@PathVariable UUID id) {
        return restTemplate.getForObject("http://"+ IP + ":" + budgetPort + "/budget/getReportList/"+id, List.class);
    }

    @GetMapping("/generateIndividualReport/{id}/{userName}")
    public List<ReportResponseDTO> generateIndividualReport(@PathVariable UUID id, @PathVariable String userName) throws Exception {
        return restTemplate.getForObject("http://"+ IP + ":" + budgetPort + "/budget/generateIndividualReport/"+id+"/"+userName, List.class);
    }

    @GetMapping("/getBudgetByBudgetId/{id}")
    public BudgetResponseDTO generateIndividualReport(@PathVariable UUID id) {
        return restTemplate.getForObject("http://"+ IP + ":" + budgetPort + "/budget/getBudgetByBudgetId/"+id, BudgetResponseDTO.class);
    }





}
