//package com.adventureit.adventureservice.Service;
//
//import com.adventureit.adventureservice.Entity.Adventure;
//import com.adventureit.adventureservice.Entity.Itinerary;
//import com.adventureit.adventureservice.Entity.EntryContainer;
//import com.adventureit.adventureservice.Requests.CreateItineraryRequest;
//import com.adventureit.adventureservice.Requests.GetAdventureByUUIDRequest;
//import com.adventureit.adventureservice.Requests.RemoveItineraryRequest;
//import com.adventureit.adventureservice.Responses.CreateItineraryResponse;
//import com.adventureit.adventureservice.Responses.GetAdventureByUUIDResponse;
//import com.adventureit.adventureservice.Responses.RemoveItineraryResponse;
//import com.adventureit.userservice.Service.UserServiceImplementation;
//import com.adventureit.userservice.Exceptions.InvalidRequestException;
//import com.adventureit.userservice.Exceptions.InvalidUserEmailException;
//import com.adventureit.userservice.Exceptions.InvalidUserPasswordException;
//import com.adventureit.userservice.Exceptions.InvalidUserPhoneNumberException;
//import com.adventureit.userservice.Requests.RegisterUserRequest;
//import com.adventureit.userservice.Responses.RegisterUserResponse;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.UUID;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//@Service("ItineraryServiceImplementation")
//public class ItineraryServiceImplementation implements ItineraryService {
//
//    private com.adventureit.adventureservice.Service.AdventureServiceImplementation adventureServiceImplementation = new AdventureServiceImplementation();
//    private UserServiceImplementation userServiceImplementation = new UserServiceImplementation();
//    /**
//     *
//     * @param req
//     * Attributes which will be attained from the req param will include:
//     * ID of the adventure (adventureID)
//     * ID of the user that created it (creatorID)
//     * Title of the itinerary (title)
//     * Description of the itinerary (description)
//     *
//     * Using the request object the CreateItinerary Service will:
//     * 1. Create an itinerary
//     * 2. Add the created itinerary to the adventure
//     *
//     * @return CreateItineraryResponse Object which will return the itinerary ID and a boolean indicating the success of the creation
//     */
//    @Override
//    public CreateItineraryResponse createItinerary(CreateItineraryRequest req) throws InvalidRequestException {
//
//        /*Exception handling for invalid Request*/
//        if(req==null||req.getTitle()==null||req.getDescription()==null||req.getAdventureID()==null||req.getUserID()==null){
//            throw new InvalidRequestException("404 Bad Request");
//        }
//        Itinerary newItinerary=new Itinerary(req.getTitle(),req.getDescription(),req.getAdventureID(), req.getUserID());
//        EntryContainer newEC= (EntryContainer) newItinerary;
//        boolean success=false;
//        GetAdventureByUUIDRequest advreq = new GetAdventureByUUIDRequest(req.getAdventureID());
//        GetAdventureByUUIDResponse advres = this.adventureServiceImplementation.getAdventureByUUID(advreq);
//        if(advres.isSuccess()) {
//            List<EntryContainer> temp = advres.getAdventure().getContainers();
//            temp.add(newEC);
//            success=true;
//        }
//        return new CreateItineraryResponse(newItinerary.getId(),success);
//    }
//
//    /**
//     *
//     * @param req
//     * Attributes which will be attained from the req param will include:
//     * ID of the adventure (adventureID)
//     * ID of the user wishing to do the removing (userID)
//     * ID of the itinerary (ItineraryID)
//     *
//     * Using the request object the RemoveItinerary Service will:
//     * 1. Find the adventure referenced by the adventureID
//     * 2. Find the itinerary attached to said adventure
//     * 3. Check that the user wishing to remove the adventure is either the creator of the itinerary or creator of the adventure
//     * 4. If the user is allowed to remove the itinerary, it will be removed
//     *
//     * @return RemoveItineraryResponse Object which will return a boolean to show the success of the deletion
//     */
//
//    @Override
//    public RemoveItineraryResponse removeItinerary(RemoveItineraryRequest req) throws InvalidRequestException
//    {
//        /*Exception handling for invalid Request*/
//        if(req==null||req.getAdventureID()==null||req.getUserID()==null||req.getId()==0){
//            throw new InvalidRequestException("404 Bad Request");
//        }
//        GetAdventureByUUIDRequest advreq = new GetAdventureByUUIDRequest(req.getAdventureID());
//        GetAdventureByUUIDResponse advres = this.adventureServiceImplementation.getAdventureByUUID(advreq);
//
//        boolean remove=false;
//        int i=0;
//        if(advres.isSuccess()) {
//            Adventure adv=advres.getAdventure();
//            for (; i < adv.getContainers().size(); i++) {
//                if (adv.getContainers().get(i).getId() == req.getId()) {
//                    remove = true;
//                    break;
//                }
//            }
//            if (remove && adv.getOwner().getUserID() == req.getUserID() || adv.getContainers().get(i).getCreatorID() == req.getUserID()) {
//                adv.getContainers().remove(i);
//                return new RemoveItineraryResponse(true);
//            } else {
//                return new RemoveItineraryResponse(false);
//            }
//        }
//
//        return new RemoveItineraryResponse(false);
//    }
//
//}