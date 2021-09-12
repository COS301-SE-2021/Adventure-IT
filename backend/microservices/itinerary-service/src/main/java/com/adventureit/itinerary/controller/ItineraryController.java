package com.adventureit.itinerary.controller;

import com.adventureit.shareddtos.itinerary.responses.StartDateEndDateResponseDTO;
import com.adventureit.shareddtos.itinerary.requests.AddItineraryEntryRequest;
import com.adventureit.shareddtos.itinerary.requests.CreateItineraryRequest;
import com.adventureit.shareddtos.itinerary.requests.EditItineraryEntryRequest;
import com.adventureit.shareddtos.itinerary.responses.ItineraryEntryResponseDTO;
import com.adventureit.shareddtos.itinerary.responses.ItineraryResponseDTO;
import com.adventureit.itinerary.service.ItineraryServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/itinerary")
public class ItineraryController {
    @Autowired
    ItineraryServiceImplementation itineraryServiceImplementation;

    @GetMapping("/test")
    public String test(){
        return "Itinerary Controller is functional";
    }

    @GetMapping("/populate")
    public String populate(){
        return itineraryServiceImplementation.mockPopulate();
    }

    @GetMapping("/viewItinerariesByAdventure/{id}")
    public List<ItineraryResponseDTO> viewItinerariesByAdventure(@PathVariable UUID id) {
        return itineraryServiceImplementation.viewItinerariesByAdventure(id);
    }

    @GetMapping("/viewItinerary/{id}")
    public List<ItineraryEntryResponseDTO> viewItinerary(@PathVariable UUID id){
        return itineraryServiceImplementation.viewItinerary(id);
    }

    @GetMapping("/softDelete/{id}/{userID}")
    public String softDelete(@PathVariable UUID id,@PathVariable UUID userID){
        return itineraryServiceImplementation.softDelete(id,userID);
    }

    @GetMapping("/viewTrash/{id}")
    public List<ItineraryResponseDTO> viewTrash(@PathVariable UUID id){
        return itineraryServiceImplementation.viewTrash(id);
    }

    @GetMapping("/restoreItinerary/{id}/{userID}")
    public String restoreItinerary(@PathVariable UUID id,@PathVariable UUID userID){
        return itineraryServiceImplementation.restoreItinerary(id,userID);
    }

    @GetMapping("/hardDelete/{id}/{userID}")
    public String hardDelete(@PathVariable UUID id, @PathVariable UUID userID){
        return itineraryServiceImplementation.hardDelete(id,userID);
    }

    @PostMapping("/create")
    public String createItinerary(@RequestBody CreateItineraryRequest req){
        return itineraryServiceImplementation.createItinerary(req.getTitle(),req.getDescription(),req.getAdvID(),req.getUserID());
    }

    @PostMapping("/addEntry")
    public UUID addItineraryEntry(@RequestBody AddItineraryEntryRequest req){
        return itineraryServiceImplementation.addItineraryEntry(req.getTitle(),req.getDescription(),req.getEntryContainerID(),req.getLocation(),req.getTimestamp());
    }

    @PostMapping("/editEntry")
    public String editItineraryEntry(@RequestBody EditItineraryEntryRequest req){
        return itineraryServiceImplementation.editItineraryEntry(req.getId(),req.getEntryContainerID(),req.getTitle(),req.getDescription(),req.getLocationId(),req.getTimestamp());
    }

    @GetMapping("/removeEntry/{id}")
    public String removeItineraryEntry(@PathVariable UUID id){
        return itineraryServiceImplementation.removeItineraryEntry(id);
    }

    @GetMapping("/markEntry/{id}")
    public void markItineraryEntry(@PathVariable UUID id){
        itineraryServiceImplementation.markCompleted(id);
    }

    @GetMapping("/getNextEntry/{id}/{userID}")
    public ItineraryEntryResponseDTO getNextEntry(@PathVariable UUID id, @PathVariable UUID userID){
        return itineraryServiceImplementation.nextItem(id, userID);
    }

    @GetMapping("/setLocation/{itineraryId}/{locationID}")
    public void setLocation(@PathVariable UUID itineraryId,@PathVariable UUID locationID){
        itineraryServiceImplementation.setItineraryEntryLocation(itineraryId,locationID);
    }

    @GetMapping("/getItineraryById/{itineraryId}")
    public ItineraryResponseDTO getItineraryById(@PathVariable UUID itineraryId){
        return itineraryServiceImplementation.getItineraryById(itineraryId);
    }

    @GetMapping("/getItineraryByEntryId/{itineraryEntryId}")
    public ItineraryResponseDTO getItineraryByEntryId(@PathVariable UUID itineraryEntryId){
        return itineraryServiceImplementation.getItineraryById(itineraryEntryId);
    }

    @GetMapping("/getItineraryEntry/{entryId}")
    public ItineraryEntryResponseDTO getItineraryEntry(@PathVariable UUID entryId){
        return itineraryServiceImplementation.getItineraryEntry(entryId);
    }

    @GetMapping("/checkUserOff/{entryId}/{userID}")
    public void checkUserOff(@PathVariable UUID entryId, @PathVariable UUID userID){
        itineraryServiceImplementation.checkUserOff(entryId,userID);
    }

    @GetMapping("/getStartDateEndDate/{id}")
    public StartDateEndDateResponseDTO getStartDateEndDate(@PathVariable UUID id){
        return itineraryServiceImplementation.getStartAndEndDate(id);
    }
}
