package com.adventureit.itinerary.service;

import com.adventureit.shareddtos.itinerary.responses.ItineraryEntryResponseDTO;
import com.adventureit.shareddtos.itinerary.responses.ItineraryResponseDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ItineraryService {

     UUID addItineraryEntry(String title, String description,  UUID entryContainerID,String location, String timestamp);
     String createItinerary(String title, String description, UUID advID, UUID userID);
     String removeItineraryEntry(UUID id);
     String editItineraryEntry(UUID id, UUID entryContainerID, String title, String description,UUID location, LocalDateTime timestamp);
     String softDelete(UUID id,UUID userID);
     String hardDelete(UUID id,UUID userID);
     List<ItineraryResponseDTO> viewTrash(UUID id);
     String restoreItinerary(UUID id,UUID userID);
     List<ItineraryEntryResponseDTO> viewItinerary(UUID id);
     void markCompleted(UUID id);
     String mockPopulate();
     ItineraryEntryResponseDTO nextItem(UUID id,  UUID userID);
     void setItineraryEntryLocation(UUID itineraryID, UUID locationID);
     ItineraryResponseDTO getItineraryById(UUID itineraryID);
     List<ItineraryResponseDTO> viewItinerariesByAdventure(UUID id);
     ItineraryResponseDTO getItineraryByEntryId(UUID itineraryEntryID);
     ItineraryEntryResponseDTO getItineraryEntry(UUID id);
     void checkUserOff(UUID entryID, UUID userID);
     String registerUser(UUID entryID, UUID userID);
}
