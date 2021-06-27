package com.adventureit.adventureservice.Controllers;

import com.adventureit.adventureservice.Entity.Adventure;
import com.adventureit.adventureservice.Requests.CreateAdventureRequest;
import com.adventureit.adventureservice.Responses.CreateAdventureResponse;
import com.adventureit.adventureservice.Responses.RemoveAdventureResponse;
import com.adventureit.adventureservice.Service.AdventureServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
    public List<Adventure> getAllAdventures(){
        return adventureServiceImplementation.getAllAdventures().getAdventures();
    }

    @GetMapping("/owner/{id}")
    public List<Adventure> getAdventuresByOwnerUUID(@PathVariable UUID id){
        return adventureServiceImplementation.getAdventureByOwnerUUID(id).getAdventures();
    }

    @GetMapping("/attendee/{id}")
    public List<Adventure> getAdventuresByAttendeeUUID(@PathVariable UUID id){
        return adventureServiceImplementation.getAdventureByAttendeeUUID(id).getAdventures();
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
