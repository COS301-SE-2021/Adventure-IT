package com.adventureit.itinerary.Service;


import java.util.List;
import java.util.UUID;

public interface ItineraryService {

    public String createItinerary(String title, String description, UUID id, UUID advID, UUID userID) throws Exception;
    public String removeItinerary(UUID id) throws Exception;
    public String addItineraryEntry(String title, String description, UUID id,  UUID entryContainerID) throws Exception;
    public String removeItineraryEntry(UUID id, UUID entryContainerID) throws Exception;
    public String editItineraryEntry(UUID id, UUID entryContainerID, String title, String description) throws Exception;

}
