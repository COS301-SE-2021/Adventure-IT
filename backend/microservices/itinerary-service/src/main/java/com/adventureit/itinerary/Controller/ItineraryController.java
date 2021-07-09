package com.adventureit.itinerary.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/itinerary")
public class ItineraryController {

    @GetMapping("/test")
    String test(){
        return "Itinerary Controller is functional";
    }

}
