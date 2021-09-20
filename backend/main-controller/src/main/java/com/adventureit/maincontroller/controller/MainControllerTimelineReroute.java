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
    private final MainControllerServiceImplementation service;

    private static final String INTERNET_PORT = "http://localhost";
    private static final String TIMELINE_PORT = "9012";

    @Autowired
    public MainControllerTimelineReroute(MainControllerServiceImplementation service) {
        this.service = service;
    }

    @GetMapping("/getTimelineByAdventure/{id}")
    public List<TimelineDTO> getTimelineByAdventureID(@PathVariable UUID id) throws Exception {
        String[] ports = {TIMELINE_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(INTERNET_PORT + ":" + TIMELINE_PORT + "/timeline/getTimelineByAdventure/"+id, List.class);

    }

    @GetMapping("/deleteTimelineByAdventureID/{id}")
    public String deleteTimelineByAdventureID(@PathVariable UUID id) throws Exception {
        String[] ports = {TIMELINE_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(INTERNET_PORT + ":" + TIMELINE_PORT + "/timeline/deleteTimelineByAdventureID/"+id, String.class);

    }

}
