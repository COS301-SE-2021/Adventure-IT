package com.adventureit.adventureservice.Controller;

import com.adventureit.adventureservice.Requests.CreateChecklistRequest;
import com.adventureit.adventureservice.Requests.CreateItineraryRequest;
import com.adventureit.adventureservice.Requests.RemoveChecklistRequest;
import com.adventureit.adventureservice.Responses.CreateChecklistResponse;
import com.adventureit.adventureservice.Responses.CreateItineraryResponse;
import com.adventureit.adventureservice.Responses.RemoveChecklistResponse;
import com.adventureit.adventureservice.Service.ChecklistService;
import com.adventureit.userservice.Exceptions.InvalidRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class ChecklistController {
    private ChecklistService service;

    @Autowired
    public ChecklistController(ChecklistService service) {
        this.service = service;
    }

    @PostMapping(value = "api/createChecklist", consumes = "application/json", produces = "application/json")
    public CreateChecklistResponse createChecklist (@RequestBody CreateChecklistRequest req) throws InvalidRequestException {
        return service.createChecklist(req);
    }

    @PostMapping(value = "api/removeChecklist", consumes = "application/json", produces = "application/json")
    public RemoveChecklistResponse removeChecklist (@RequestBody RemoveChecklistRequest req) throws InvalidRequestException {
        return service.removeChecklist(req);
    }


}
