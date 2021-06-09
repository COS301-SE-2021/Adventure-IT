package com.adventureit.adventureservice.Controller;

import com.adventureit.adventureservice.Requests.CreateAdventureRequest;
import com.adventureit.adventureservice.Requests.CreateItineraryRequest;
import com.adventureit.adventureservice.Requests.RemoveItineraryRequest;
import com.adventureit.adventureservice.Responses.CreateAdventureResponse;
import com.adventureit.adventureservice.Responses.CreateItineraryResponse;
import com.adventureit.adventureservice.Responses.RemoveItineraryResponse;
import com.adventureit.adventureservice.Service.ItineraryService;
import com.adventureit.userservice.Exceptions.InvalidRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class ItineraryController {

    private ItineraryService service;

    @Autowired
    public ItineraryController(ItineraryService service) {
        this.service = service;
    }

    @PostMapping(value = "api/createItinerary", consumes = "application/json", produces = "application/json")
    public CreateItineraryResponse createItinerary (@RequestBody CreateItineraryRequest req) throws InvalidRequestException {
        return service.createItinerary(req);
    }

    @PostMapping(value = "api/removeItinerary", consumes = "application/json", produces = "application/json")
    public RemoveItineraryResponse removeItinerary (@RequestBody RemoveItineraryRequest req) throws InvalidRequestException {
        return service.removeItinerary(req);
    }
}
