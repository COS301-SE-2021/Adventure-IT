package com.adventureit.adventureservice.Service;

import com.adventureit.adventureservice.Entity.Adventure;
import com.adventureit.adventureservice.Entity.Itinerary;
import com.adventureit.adventureservice.Requests.CreateItineraryRequest;
import com.adventureit.adventureservice.Requests.GetAdventureByUUIDRequest;
import com.adventureit.adventureservice.Requests.RemoveItineraryRequest;
import com.adventureit.adventureservice.Responses.CreateItineraryResponse;
import com.adventureit.adventureservice.Responses.GetAdventureByUUIDResponse;
import com.adventureit.adventureservice.Responses.RemoveItineraryResponse;
import com.adventureit.userservice.Exceptions.InvalidRequestException;
import com.adventureit.userservice.Service.UserServiceImplementation;
import main.java.com.adventureit.adventureservice.Repository.ItineraryRepository;
import org.springframework.stereotype.Service;

@Service("ItineraryServiceImplementation")
public class ItineraryServiceImplementation implements ItineraryService {

    private ItineraryRepository ItineraryRepository;
    private com.adventureit.adventureservice.Service.AdventureServiceImplementation adventureServiceImplementation = new AdventureServiceImplementation();
    private UserServiceImplementation userServiceImplementation = new UserServiceImplementation();
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
    @Override
    public CreateItineraryResponse createItinerary(CreateItineraryRequest req) throws InvalidRequestException {

        /*Exception handling for invalid Request*/
        if(req==null||req.getTitle()==null||req.getDescription()==null||req.getAdventureID()==null||req.getUserID()==null){
            throw new InvalidRequestException("Error! Bad Request");
        }
        Itinerary newItinerary=new Itinerary(req.getTitle(),req.getDescription(),req.getAdventureID(), req.getUserID());
        boolean success=false;
        GetAdventureByUUIDRequest advreq = new GetAdventureByUUIDRequest(req.getAdventureID());
        GetAdventureByUUIDResponse advres = this.adventureServiceImplementation.getAdventureByUUID(advreq);
        if(advres.isSuccess()) {
            ItineraryRepository.save(newItinerary);
            success=true;
        }
        return new CreateItineraryResponse(newItinerary.getId(),success);
    }

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

    @Override
    public RemoveItineraryResponse removeItinerary(RemoveItineraryRequest req) throws InvalidRequestException
    {
        /*Exception handling for invalid Request*/
        if(req==null||req.getAdventureID()==null||req.getUserID()==null||req.getId()==0){
            throw new InvalidRequestException("Error! Bad Request");
        }
        GetAdventureByUUIDRequest advreq = new GetAdventureByUUIDRequest(req.getAdventureID());
        GetAdventureByUUIDResponse advres = this.adventureServiceImplementation.getAdventureByUUID(advreq);
        Itinerary it=ItineraryRepository.getById(req.getId());
        Adventure adv=advres.getAdventure();

        if(advres.isSuccess()) {


            if (adv.getOwner().getUserID() == req.getUserID()||it.getCreatorID()==req.getUserID()) {
                ItineraryRepository.delete(it);
                return new RemoveItineraryResponse(true);
            } else {
                return new RemoveItineraryResponse(false);
            }
        }

        return new RemoveItineraryResponse(false);
    }

}
