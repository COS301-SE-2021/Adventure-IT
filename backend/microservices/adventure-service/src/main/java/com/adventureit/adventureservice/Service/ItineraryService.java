package com.adventureit.adventureservice.Service;

import com.adventureit.adventureservice.Requests;
import com.adventureit.adventureservice.Responses;


public interface ItineraryService {
    public CreateItineraryResponse createItinerary(CreateItineraryRequest req);
    public RemoveItineraryResponse removeItinerary(RemoveItineraryRequest req);
}
