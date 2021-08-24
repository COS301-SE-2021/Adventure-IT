package com.adventureit.itinerary.service;

import com.adventureit.itinerary.responses.ItineraryEntryResponseDTO;
import com.adventureit.itinerary.responses.ItineraryResponseDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ItineraryService {

    public UUID addItineraryEntry(String title, String description,  UUID entryContainerID,String location, String timestamp);
    public String createItinerary(String title, String description, UUID advID, UUID userID);
    public String removeItineraryEntry(UUID id);
    public String editItineraryEntry(UUID id, UUID entryContainerID, String title, String description,UUID location, LocalDateTime timestamp);
    public String softDelete(UUID id,UUID userID);
    public String hardDelete(UUID id,UUID userID);
    public List<ItineraryResponseDTO> viewTrash(UUID id);
    public String restoreItinerary(UUID id,UUID userID);
    public List<ItineraryEntryResponseDTO> viewItinerary(UUID id);
    public void markCompleted(UUID id);
    public String mockPopulate();
    public ItineraryEntryResponseDTO nextItem(UUID id);
    public void setItineraryEntryLocation(UUID itineraryID, UUID locationID);
    public ItineraryResponseDTO getItineraryById(UUID itineraryID);
    public List<ItineraryResponseDTO> viewItinerariesByAdventure(UUID id);
}
