package com.adventureit.adventureservice.Service;

import com.adventureit.adventureservice.Entity.Checklist;
import com.adventureit.adventureservice.Entity.EntryContainer;
import com.adventureit.adventureservice.Requests.CreateChecklistRequest;
import com.adventureit.adventureservice.Requests.GetAdventureByUUIDRequest;
import com.adventureit.adventureservice.Requests.RemoveChecklistRequest;
import com.adventureit.adventureservice.Responses.CreateChecklistResponse;
import com.adventureit.adventureservice.Responses.GetAdventureByUUIDResponse;
import com.adventureit.adventureservice.Responses.RemoveChecklistResponse;
import com.adventureit.userservice.Exceptions.InvalidRequestException;
import com.adventureit.userservice.Requests.GetUserByUUIDRequest;
import com.adventureit.userservice.Responses.GetUserByUUIDResponse;
import com.adventureit.userservice.Service.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChecklistService {
    @Autowired
    private AdventureServiceImplementation adventureServiceImplementation;

    // TODO: This needs to use the controller & not the autowired service
    private UserServiceImplementation userServiceImplementation;

    /**
     * Creating a checklist
     *
     * @param req
     * Contains all necessary information to create a new checklist:
     * - Title of checklist
     * - Description of checklist
     * - UUID of user creating checklist
     * - UUID of adventure to which checklist must be assigned
     * @return
     * - The ID of the newly created checklist
     * - Whether or not the creation was successful
     */
    public CreateChecklistResponse createChecklist(CreateChecklistRequest req) throws InvalidRequestException {

        // Confirm that the request is valid (ie no null values), throw exception if not
        if(req == null || req.getAdventureID() == null || req.getCreatorID() == null || req.getTitle()==null || req.getDescription()==null){
            throw new InvalidRequestException("Error: Malformed CreateChecklistRequest (one or more null values)");
        }

        // Get adventure that checklist wants to be added to
        GetAdventureByUUIDRequest advreq = new GetAdventureByUUIDRequest(req.getAdventureID());
        GetAdventureByUUIDResponse retrievedAdventure = this.adventureServiceImplementation.getAdventureByUUID(advreq);

        // Get user that wants to add checklist
        GetUserByUUIDRequest usrreq = new GetUserByUUIDRequest(req.getCreatorID());
        GetUserByUUIDResponse usrres = this.userServiceImplementation.GetUserByUUID(usrreq);

        // Check to see if adventure & user exist
        if(retrievedAdventure.isSuccess() && usrres.isSuccess()){
            Checklist newChecklist = new Checklist(req.getTitle(), req.getDescription(), req.getCreatorID(), req.getAdventureID());
            List<EntryContainer> temp = retrievedAdventure.getAdventure().getContainers();
            temp.add(newChecklist);
            ArrayList<EntryContainer> tempArr = new ArrayList<EntryContainer>();
            tempArr.addAll(temp);
            retrievedAdventure.getAdventure().setContainers(tempArr);
            return new CreateChecklistResponse(newChecklist.getId(), true);
        }
        else {
            return new CreateChecklistResponse(-1, false);
        }

    }

    /**
     * Removing a checklist
     *
     * @param req
     * Contains all necessary information to create a new checklist:
     * - The ID of the checklist to be deleted
     * - UUID of the user attempting to perform the deletion
     * - UUID of the adventure to which the checklist that will be deleted belongs
     * @return
     * - Whether or not the deletion was successful
     */
    public RemoveChecklistResponse removeChecklist(RemoveChecklistRequest req) throws InvalidRequestException{
        // Confirm that the request is valid (ie no null values), throw exception if not
        if(req == null || req.getAdventureID() == null || req.getId() == null || req.getOwnerID() == null){
            throw new InvalidRequestException("Error: Malformed RemoveChecklistRequest (one or more null values)");
        }

        // Get adventure that checklist wants to be added to
        GetAdventureByUUIDRequest advreq = new GetAdventureByUUIDRequest(req.getAdventureID());
        GetAdventureByUUIDResponse advres = this.adventureServiceImplementation.getAdventureByUUID(advreq);

        // Get user that wants to add checklist
        GetUserByUUIDRequest usrreq = new GetUserByUUIDRequest(req.getOwnerID());
        GetUserByUUIDResponse usrres = this.userServiceImplementation.GetUserByUUID(usrreq);
        
        // Check to see if user is valid, adventure is valid & that the user is the owner of the checklist
        if(advres.isSuccess() && usrres.isSuccess() && advres.getAdventure().getOwner().getUserID().equals(usrres.getUser().getUserID())){
            for (EntryContainer e :
                    advres.getAdventure().getContainers()) {
                if(e.getId() == req.getId()){
                    advres.getAdventure().getContainers().remove(e);
                    break;
                }
            }
            return new RemoveChecklistResponse(true);
        }
        else {
            return new RemoveChecklistResponse(false);

        }
        
    }
}
