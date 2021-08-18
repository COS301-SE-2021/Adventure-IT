package com.adventureit.itinerary.Controller;

import com.adventureit.itinerary.Entity.Itinerary;
import com.adventureit.itinerary.Entity.ItineraryEntry;
import com.adventureit.itinerary.Repository.ItineraryRepository;
import com.adventureit.itinerary.Requests.AddItineraryEntryRequest;
import com.adventureit.itinerary.Requests.CreateItineraryRequest;
import com.adventureit.itinerary.Requests.EditItineraryEntryRequest;
import com.adventureit.itinerary.Requests.RemoveItineraryEntryRequest;
import com.adventureit.itinerary.Responses.ItineraryEntryResponseDTO;
import com.adventureit.itinerary.Responses.ItineraryResponseDTO;
import com.adventureit.itinerary.Service.ItineraryServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/itinerary")
public class ItineraryController {
    @Autowired
    ItineraryServiceImplementation itineraryServiceImplementation;
    @Autowired
    ItineraryRepository itineraryRepository;

    @GetMapping("/test")
    String test(){
        return "Itinerary Controller is functional";
    }

    @GetMapping("/populate")
    public String populate(){
        return itineraryServiceImplementation.mockPopulate();
    }

    @GetMapping("/viewItinerariesByAdventure/{id}")
    public List<ItineraryResponseDTO> viewItinerariesByAdventure(@PathVariable UUID id) throws Exception {
        List<Itinerary> itineraries = itineraryRepository.findAllByAdventureID(id);
        List<ItineraryResponseDTO> list = new ArrayList<>();
        for (Itinerary c:itineraries) {
            if(!c.getDeleted()){
                list.add(new ItineraryResponseDTO(c.getTitle(),c.getDescription(),c.getId(),c.getCreatorID(),c.getAdventureID(),c.getDeleted()));
            }
        }
        return list;
    }

    @GetMapping("/viewItinerary/{id}")
    public List<ItineraryEntryResponseDTO> viewItinerary(@PathVariable UUID id) throws Exception {
        return itineraryServiceImplementation.viewItinerary(id);
    }

    @GetMapping("/softDelete/{id}/{userID}")
    public String softDelete(@PathVariable UUID id,@PathVariable UUID userID) throws Exception {
        return itineraryServiceImplementation.softDelete(id,userID);
    }
    //
    @GetMapping("/viewTrash/{id}")
    public List<ItineraryResponseDTO> viewTrash(@PathVariable UUID id) throws Exception {
        return itineraryServiceImplementation.viewTrash(id);
    }

    @GetMapping("/restoreItinerary/{id}/{userID}")
    public String restoreItinerary(@PathVariable UUID id,@PathVariable UUID userID) throws Exception {
        return itineraryServiceImplementation.restoreItinerary(id,userID);
    }

    @GetMapping("/hardDelete/{id}/{userID}")
    public String hardDelete(@PathVariable UUID id, @PathVariable UUID userID) throws Exception {
        return itineraryServiceImplementation.hardDelete(id,userID);
    }

    @PostMapping("/create")
    public String createItinerary(@RequestBody CreateItineraryRequest req) throws Exception {
        return itineraryServiceImplementation.createItinerary(req.getTitle(),req.getDescription(),req.getAdvID(),req.getUserID());
    }

    @PostMapping("/addEntry")
    public UUID addItineraryEntry(@RequestBody AddItineraryEntryRequest req) throws Exception {
        return itineraryServiceImplementation.addItineraryEntry(req.getTitle(),req.getDescription(),req.getEntryContainerID(),req.getLocation(),req.getTimestamp());
    }

    @PostMapping("/editEntry")
    public String editItineraryEntry(@RequestBody EditItineraryEntryRequest req) throws Exception {
        return itineraryServiceImplementation.editItineraryEntry(req.getId(),req.getEntryContainerID(),req.getTitle(),req.getDescription(),req.getLocationId(),req.getTimestamp());
    }

    @GetMapping("/removeEntry/{id}")
    public String removeItineraryEntry(@PathVariable UUID id) throws Exception {
        return itineraryServiceImplementation.removeItineraryEntry(id);
    }

    @GetMapping("/markEntry/{id}")
    public void markItineraryEntry(@PathVariable UUID id) throws Exception {
        itineraryServiceImplementation.markCompleted(id);
    }

    @GetMapping("/getNextEntry/{id}")
    public ItineraryEntryResponseDTO getNextEntry(@PathVariable UUID id) throws Exception {
        return itineraryServiceImplementation.nextItem(id);
    }

    @GetMapping("/setLocation/{itineraryId}/{locationID}")
    public void setLocation(@PathVariable UUID itineraryId,@PathVariable UUID locationID) throws Exception {
        itineraryServiceImplementation.setItineraryEntryLocation(itineraryId,locationID);
    }

    @GetMapping("/getItineraryById/{itineraryId}")
    public ItineraryResponseDTO getItineraryById(@PathVariable UUID itineraryId){
        return itineraryServiceImplementation.getItineraryById(itineraryId);
    }

}
