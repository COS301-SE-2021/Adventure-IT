package com.adventureit.itinerary.Controller;

import com.adventureit.itinerary.Entity.Itinerary;
import com.adventureit.itinerary.Repository.ItineraryRepository;
import com.adventureit.itinerary.Requests.CreateItineraryRequest;
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
                list.add(new ItineraryResponseDTO(c.getTitle(),c.getDescription(),c.getId(),c.getCreatorID(),c.getAdventureID(),c.getEntries(),c.getDeleted()));
            }
        }
        return list;
    }

    @GetMapping("/viewItinerary/{id}")
    public ItineraryResponseDTO viewItinerary(@PathVariable UUID id) throws Exception {
        return itineraryServiceImplementation.viewItinerary(id);
    }

    @GetMapping("/softDelete/{id}")
    public String softDelete(@PathVariable UUID id) throws Exception {
        return itineraryServiceImplementation.softDelete(id);
    }
    //
    @GetMapping("/viewTrash/{id}")
    public List<ItineraryResponseDTO> viewTrash(@PathVariable UUID id) throws Exception {
        return itineraryServiceImplementation.viewTrash(id);
    }

    @GetMapping("/restoreItinerary/{id}")
    public String restoreItinerary(@PathVariable UUID id) throws Exception {
        return itineraryServiceImplementation.restoreItinerary(id);
    }

    @PostMapping("/create")
    public String createItinerary(@RequestBody CreateItineraryRequest req) throws Exception {
        return itineraryServiceImplementation.createItinerary(req.getTitle(),req.getDescription(),req.getId(),req.getAdvID(),req.getUserID());
    }

}
