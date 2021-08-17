package com.adventureit.maincontroller.Controller;


import com.adventureit.timelineservice.Responses.TimelineDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/timeline")
public class MainControllerTimelineReroute {


    private RestTemplate restTemplate = new RestTemplate();

    private final String IP = "localhost";
    private final String timelinePort = "9012";

    @GetMapping("/getTimelineByAdventure")
    public List<TimelineDTO> getTimelineByAdventureID(@PathVariable UUID id) throws Exception {
        return restTemplate.getForObject("http://"+ IP + ":" + timelinePort + "/timeline/getTimelineByAdventure/"+id, List.class);

    }

    @GetMapping("/deleteTimelineByAdventureID")
    public String deleteTimelineByAdventureID(@PathVariable UUID id){
        return restTemplate.getForObject("http://"+ IP + ":" + timelinePort + "/timeline/deleteTimelineByAdventureID/"+id, String.class);

    }

}
