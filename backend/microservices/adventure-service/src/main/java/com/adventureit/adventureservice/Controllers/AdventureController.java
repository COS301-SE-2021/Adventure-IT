package com.adventureit.adventureservice.Controllers;

import com.adventureit.adventureservice.Requests.CreateAdventureRequest;
import com.adventureit.adventureservice.Responses.CreateAdventureResponse;
import com.adventureit.adventureservice.Responses.GetAdventuresByUserUUIDResponse;
import com.adventureit.adventureservice.Responses.GetAllAdventuresResponse;
import com.adventureit.adventureservice.Responses.RemoveAdventureResponse;
import com.adventureit.adventureservice.Service.AdventureServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/adventure")
public class AdventureController {
    @Autowired
    AdventureServiceImplementation adventureServiceImplementation;

    @GetMapping("/test")
    public String test(){
        return "Adventure Controller is functional \n";
    }

    @GetMapping("/populate")
    public String populate(){
        adventureServiceImplementation.mockPopulate();
        return "Mock adventures populated \n";
    }

    @GetMapping("/all")
    public List<GetAllAdventuresResponse> getAllAdventures() {
        return adventureServiceImplementation.getAllAdventures();
    }

    @GetMapping("/all/{id}")
    public List<GetAdventuresByUserUUIDResponse> getAllAdventuresByUserUUID(@PathVariable UUID id){
        return adventureServiceImplementation.getallAdventuresByUUID(id);
    }

    @GetMapping("/owner/{id}")
    public List<GetAdventuresByUserUUIDResponse> getAdventuresByOwnerUUID(@PathVariable UUID id){
        return adventureServiceImplementation.getAdventureByOwnerUUID(id);
    }

    @GetMapping("/attendee/{id}")
    public List<GetAdventuresByUserUUIDResponse> getAdventuresByAttendeeUUID(@PathVariable UUID id){
        return adventureServiceImplementation.getAdventureByAttendeeUUID(id);
    }

    @PostMapping("/create")
    public CreateAdventureResponse createAdventure(@RequestBody CreateAdventureRequest req){
        return adventureServiceImplementation.createAdventure(req);
    }

    @DeleteMapping("/remove/{id}")
    public RemoveAdventureResponse removeAdventure(@PathVariable UUID id){
        return adventureServiceImplementation.removeAdventure(id);
    }
}
