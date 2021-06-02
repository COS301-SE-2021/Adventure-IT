package com.adventureit.adventureservice.Service;

import com.adventureit.adventureservice.Requests.CreateItineraryRequest;
import com.adventureit.adventureservice.Requests.RemoveItineraryRequest;
import com.adventureit.adventureservice.Responses.CreateItineraryResponse;
import com.adventureit.adventureservice.Responses.RemoveItineraryResponse;
import com.adventureit.userservice.Exceptions.InvalidRequestException;


public interface ItineraryService {
    public CreateItineraryResponse createItinerary(CreateItineraryRequest req) throws InvalidRequestException;
    public RemoveItineraryResponse removeItinerary(RemoveItineraryRequest req) throws InvalidRequestException;
}
