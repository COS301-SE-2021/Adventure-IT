package com.adventureit.timelineservice.Controller;


import com.adventureit.timelineservice.Requests.CreateTimelineRequest;
import com.adventureit.timelineservice.Responses.TimelineDTO;
import com.adventureit.timelineservice.Service.TimelineServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/timeline")
public class TimelineController {

    @Autowired
    TimelineServiceImplementation service;


    @GetMapping("/getTimelineByAdventure/{id}")
    public List<TimelineDTO> getTimelineByAdventureID(@PathVariable UUID id) throws Exception {
        return service.GetTimelineByAdventureID(id);
    }

    @GetMapping("/deleteTimelineByAdventureID/{id}")
    public String deleteTimelineByAdventureID(@PathVariable UUID id){
        return service.deleteTimelineByAdventureID(id);
    }

    @PostMapping("/getTimelineByAdventureID/{id}")
    public String getTimelineByAdventureID(@RequestBody CreateTimelineRequest req){
        return service.createTimelineEntry(req.getAdventureID(),req.getUserID(),req.getDescription(),req.getType());
    }



}
