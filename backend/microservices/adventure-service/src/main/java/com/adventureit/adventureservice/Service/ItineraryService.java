package com.adventureit.adventureservice.Service;

import com.adventureit.adventureservice.Requests.CreateItineraryRequest;
import com.adventureit.adventureservice.Requests.RemoveItineraryRequest;
import com.adventureit.adventureservice.Responses.CreateItineraryResponse;
import com.adventureit.adventureservice.Responses.RemoveItineraryResponse;
import com.adventureit.userservice.Exceptions.InvalidRequestException;


public interface ItineraryService {
    /**
     *
     * @param req
     * Attributes which will be attained from the req param will include:
     * ID of the adventure (adventureID)
     * ID of the user that created it (creatorID)
     * Title of the itinerary (title)
     * Description of the itinerary (description)
     *
     * Using the request object the CreateItinerary Service will:
     * 1. Create an itinerary
     * 2. Add the created itinerary to the adventure
     *
     * @return CreateItineraryResponse Object which will return the itinerary ID and a boolean indicating the success of the creation
     */
    public CreateItineraryResponse createItinerary(CreateItineraryRequest req) throws InvalidRequestException;
    /**
     *
     * @param req
     * Attributes which will be attained from the req param will include:
     * ID of the adventure (adventureID)
     * ID of the user wishing to do the removing (userID)
     * ID of the itinerary (ItineraryID)
     *
     * Using the request object the RemoveItinerary Service will:
     * 1. Find the adventure referenced by the adventureID
     * 2. Find the itinerary attached to said adventure
     * 3. Check that the user wishing to remove the adventure is either the creator of the itinerary or creator of the adventure
     * 4. If the user is allowed to remove the itinerary, it will be removed
     *
     * @return RemoveItineraryResponse Object which will return a boolean to show the success of the deletion
     */
    public RemoveItineraryResponse removeItinerary(RemoveItineraryRequest req) throws InvalidRequestException;
}
