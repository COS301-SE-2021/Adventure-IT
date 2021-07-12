package com.adventureit.itinerary.Controller;

import com.adventureit.itinerary.Entity.Itinerary;
import com.adventureit.itinerary.Repository.ItineraryRepository;
import com.adventureit.itinerary.Responses.ItineraryResponseDTO;
import com.adventureit.itinerary.Service.ItineraryServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            list.add(new ItineraryResponseDTO(c.getTitle(),c.getDescription(),c.getId(),c.getCreatorID(),c.getAdventureID(),c.getEntries(),c.getDeleted()));
        }
        return list;
    }

    @GetMapping("/viewItinerary/{id}")
    public ItineraryResponseDTO viewItinerary(@PathVariable UUID id) throws Exception {
        return itineraryServiceImplementation.viewItinerary(id);
    }

}
