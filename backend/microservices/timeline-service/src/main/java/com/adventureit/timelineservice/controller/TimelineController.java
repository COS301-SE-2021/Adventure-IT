package com.adventureit.timelineservice.controller;


import com.adventureit.timelineservice.requests.CreateTimelineRequest;
import com.adventureit.timelineservice.responses.TimelineDTO;
import com.adventureit.timelineservice.service.TimelineServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/timeline")
public class TimelineController {


    TimelineServiceImplementation service;

    @GetMapping("/getTimelineByAdventure/{id}")
    public List<TimelineDTO> getTimelineByAdventureID(@PathVariable UUID id){
        return service.getTimelineByAdventureID(id);
    }

    @GetMapping("/deleteTimelineByAdventureID/{id}")
    public String deleteTimelineByAdventureID(@PathVariable UUID id){
        return service.deleteTimelineByAdventureID(id);
    }

    @PostMapping("/createTimeline")
    public String createTimeline(@RequestBody CreateTimelineRequest req){
        return service.createTimelineEntry(req.getAdventureID(),req.getDescription(),req.getType());
    }



}
