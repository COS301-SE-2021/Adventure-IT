package com.adventureit.maincontroller.Controller;


import com.adventureit.adventureservice.Responses.CreateAdventureResponse;
import com.adventureit.budgetservice.Requests.CreateBudgetRequest;
import com.adventureit.budgetservice.Responses.CreateBudgetResponse;
import com.adventureit.timelineservice.Entity.TimelineType;
import com.adventureit.timelineservice.Requests.CreateTimelineRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/budget")
public class MainControllerBudgetReroute {

    private RestTemplate restTemplate = new RestTemplate();

    private final String IP = "localhost";
    private final String timelinePort = "9012";
    private final String budgetPort = "9007";

//    @GetMapping("/createBudget")
//    public String adventureTest(){
//        return restTemplate.getForObject("http://"+ IP + ":" + adventurePort + "/adventure/test", String.class);
//    }
//
//    @PostMapping(value = "/create")
//    public CreateAdventureResponse createAdventure(@RequestBody CreateAdventureRequest req) {
//        return restTemplate.postForObject("http://"+ IP + ":" + adventurePort + "/adventure/create/", req, CreateAdventureResponse.class);
//    }

    @PostMapping(value ="/create")
    public String createBudget(@RequestBody CreateBudgetRequest req) {
        System.out.println(req.getName());
        System.out.println(req.getDescription());
        System.out.println(req.getCreatorID());
        System.out.println(req.getAdventureID());



         return restTemplate.postForObject("http://"+ IP + ":" + budgetPort + "/budget/create/", req, String.class);





        //CreateTimelineRequest req2 = new CreateTimelineRequest(req.getAdventureID(),req.getCreatorID(), TimelineType.BUDGET,req.getName()+" budget has been created" );
        //String timelineResponse = restTemplate.postForObject("http://"+ IP + ":" + timelinePort + "/timeline/create", req2, String.class);

    }



}
