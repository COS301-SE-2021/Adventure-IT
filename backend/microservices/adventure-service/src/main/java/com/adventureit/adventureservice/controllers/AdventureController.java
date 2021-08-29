package com.adventureit.adventureservice.controllers;

import com.adventureit.adventureservice.requests.CreateAdventureRequest;
import com.adventureit.adventureservice.requests.GetAdventureByUUIDRequest;
import com.adventureit.adventureservice.responses.*;
import com.adventureit.adventureservice.service.AdventureServiceImplementation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/adventure")
public class AdventureController {
    AdventureServiceImplementation adventureServiceImplementation;

    public AdventureController(AdventureServiceImplementation adventureServiceImplementation){
        this.adventureServiceImplementation = adventureServiceImplementation;
    }

    @GetMapping("/test")
    public String test() {
        return "Adventure Controller is functional";
    }

    @GetMapping("/all")
    public List<GetAllAdventuresResponse> getAllAdventures() {
        return adventureServiceImplementation.getAllAdventures();
    }

    @GetMapping("/setLocation/{adventureId}/{locationId}")
    public String setLocationAdventures(@PathVariable UUID adventureId,@PathVariable UUID locationId) {
        adventureServiceImplementation.setAdventureLocation(adventureId,locationId);
        return "Working";
    }

    @GetMapping("/all/{id}")
    public List<GetAdventuresByUserUUIDResponse> getAllAdventuresByUserUUID(@PathVariable UUID id){
        return adventureServiceImplementation.getAllAdventuresByUUID(id);
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

    @DeleteMapping("/remove/{id}/{userID}")
    public RemoveAdventureResponse removeAdventure(@PathVariable UUID id, @PathVariable UUID userID) {
        return adventureServiceImplementation.removeAdventure(id, userID);
    }

    @GetMapping("/getAttendees/{id}")
    public List<UUID> getAttendees(@PathVariable UUID id) {
        return adventureServiceImplementation.getAttendees(id);
    }

    @GetMapping("/addAttendees/{adventureID}/{userID}")
    public void addAttendees(@PathVariable UUID adventureID,@PathVariable UUID userID) {
        adventureServiceImplementation.addAttendees(adventureID,userID);
    }

    @GetMapping("/getAdventureByUUID/{id}")
    public GetAdventureByUUIDResponse getAdventureByUUID(@PathVariable UUID id){
        return adventureServiceImplementation.getAdventureByUUID(new GetAdventureByUUIDRequest(id));
    }
    @GetMapping("/removeAttendees/{adventureID}/{userID}")
    public void removeAttendees(@PathVariable UUID adventureID,@PathVariable UUID userID) {
        adventureServiceImplementation.removeAttendees(adventureID,userID);
    }

}
