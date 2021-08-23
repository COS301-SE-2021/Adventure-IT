package com.adventureit.adventureservice.controllers;

import com.adventureit.adventureservice.Requests.CreateAdventureRequest;
import com.adventureit.adventureservice.Responses.CreateAdventureResponse;
import com.adventureit.adventureservice.Responses.GetAdventuresByUserUUIDResponse;
import com.adventureit.adventureservice.Responses.GetAllAdventuresResponse;
import com.adventureit.adventureservice.Responses.RemoveAdventureResponse;
import com.adventureit.adventureservice.Service.AdventureServiceImplementation;
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

    // TODO: setLocationAdventures response object
    @GetMapping("/setLocation/{adventureId}/{locationId}")
    public String setLocationAdventures(@PathVariable UUID adventureId,@PathVariable UUID locationId) {
        adventureServiceImplementation.setAdventureLocation(adventureId,locationId);
        return "Working";
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

    @DeleteMapping("/remove/{id}/{userID}")
    public RemoveAdventureResponse removeAdventure(@PathVariable UUID id, @PathVariable UUID userID) throws Exception {
        return adventureServiceImplementation.removeAdventure(id, userID);
    }

    // TODO: getAttendees response object
    @GetMapping("/getAttendees/{id}")
    public List<UUID> getAttendees(@PathVariable UUID id) throws Exception {
        return adventureServiceImplementation.getAttendees(id);
    }

    @GetMapping("/addAttendees/{adventureID}/{userID}")
    public void addAttendees(@PathVariable UUID adventureID,@PathVariable UUID userID) throws Exception {
        adventureServiceImplementation.addAttendees(adventureID,userID);
    }
}
