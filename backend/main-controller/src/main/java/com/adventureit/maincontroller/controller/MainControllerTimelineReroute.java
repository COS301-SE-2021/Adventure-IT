package com.adventureit.maincontroller.controller;


import com.adventureit.maincontroller.service.MainControllerServiceImplementation;
import com.adventureit.shareddtos.timeline.responses.TimelineDTO;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final RestTemplate restTemplate = new RestTemplate();
    private MainControllerServiceImplementation service;

    private final String IP = "http://localhost";
    private final String timelinePort = "9012";

    @Autowired
    public MainControllerTimelineReroute(MainControllerServiceImplementation service) {
        this.service = service;
    }

    @GetMapping("/getTimelineByAdventure/{id}")
    public List<TimelineDTO> getTimelineByAdventureID(@PathVariable UUID id) throws Exception {
        String[] ports = {timelinePort};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(IP + ":" + timelinePort + "/timeline/getTimelineByAdventure/"+id, List.class);

    }

    @GetMapping("/deleteTimelineByAdventureID/{id}")
    public String deleteTimelineByAdventureID(@PathVariable UUID id) throws Exception {
        String[] ports = {timelinePort};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(IP + ":" + timelinePort + "/timeline/deleteTimelineByAdventureID/"+id, String.class);

    }

}
