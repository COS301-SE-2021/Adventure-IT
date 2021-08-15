package com.adventureit.itinerary.Service;


import com.adventureit.itinerary.Entity.Itinerary;
import com.adventureit.itinerary.Responses.ItineraryEntryResponseDTO;
import com.adventureit.itinerary.Responses.ItineraryResponseDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ItineraryService {

    public UUID addItineraryEntry(String title, String description,  UUID entryContainerID,String location, String timestamp) throws Exception;
    public String createItinerary(String title, String description, UUID advID, UUID userID) throws Exception;
    public String removeItineraryEntry(UUID id) throws Exception;
    public String editItineraryEntry(UUID id, UUID entryContainerID, String title, String description,UUID location, LocalDateTime timestamp) throws Exception;
    public String softDelete(UUID id,UUID userID) throws Exception;
    public String hardDelete(UUID id,UUID userID) throws Exception;
    public List<ItineraryResponseDTO> viewTrash(UUID id) throws Exception;
    public String restoreItinerary(UUID id,UUID userID) throws Exception;
    public List<ItineraryEntryResponseDTO> viewItinerary(UUID id) throws Exception;
    public void markCompleted(UUID id) throws Exception;
    public String mockPopulate();
    public ItineraryEntryResponseDTO nextItem(UUID id) throws Exception;
    public void setItineraryEntryLocation(UUID itineraryID, UUID locationID);
}
