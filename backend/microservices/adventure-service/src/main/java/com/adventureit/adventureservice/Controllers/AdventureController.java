package com.adventureit.adventureservice.Controllers;

import com.adventureit.adventureservice.Entity.Adventure;
import com.adventureit.adventureservice.Requests.CreateAdventureRequest;
import com.adventureit.adventureservice.Responses.CreateAdventureResponse;
import com.adventureit.adventureservice.Responses.RemoveAdventureResponse;
import com.adventureit.adventureservice.Service.AdventureServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        List<Adventure> allAdventures = adventureServiceImplementation.getAllAdventures().getAdventures();
        Collections.sort(allAdventures, new Comparator<Adventure>() {
            @Override
            public int compare(Adventure o1, Adventure o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });
        return allAdventures;
    }

    @GetMapping("/all/{id}")
    public List<Adventure> getAllAdventuresByUserUUID(@PathVariable UUID id){
        List<Adventure> ownedAdventures = adventureServiceImplementation.getAdventureByOwnerUUID(id).getAdventures();
        List<Adventure> attendingAdventures = adventureServiceImplementation.getAdventureByAttendeeUUID(id).getAdventures();
        List<Adventure> allAdventures = Stream.concat(ownedAdventures.stream(), attendingAdventures.stream()).collect(Collectors.toList());
        Collections.sort(allAdventures, new Comparator<Adventure>() {
            @Override
            public int compare(Adventure o1, Adventure o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });
        return allAdventures;

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
