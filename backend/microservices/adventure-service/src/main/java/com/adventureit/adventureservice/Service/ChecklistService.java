package com.adventureit.adventureservice.Service;

import com.adventureit.adventureservice.Entity.Checklist;
import com.adventureit.adventureservice.Entity.EntryContainer;
import com.adventureit.adventureservice.Requests.CreateChecklistRequest;
import com.adventureit.adventureservice.Requests.GetAdventureByUUIDRequest;
import com.adventureit.adventureservice.Requests.RemoveChecklistRequest;
import com.adventureit.adventureservice.Responses.CreateChecklistResponse;
import com.adventureit.adventureservice.Responses.GetAdventureByUUIDResponse;
import com.adventureit.adventureservice.Responses.RemoveChecklistResponse;
import com.adventureit.userservice.Requests.GetUserByUUIDRequest;
import com.adventureit.userservice.Responses.GetUserByUUIDResponse;
import com.adventureit.userservice.Service.UserServiceImplementation;
import org.springframework.stereotype.Service;

@Service
public class ChecklistService {
    // Autowiring will not work for demo purposes
    private AdventureServiceImplementation adventureServiceImplementation = new AdventureServiceImplementation();
    private UserServiceImplementation userServiceImplementation = new UserServiceImplementation();

    // Add Checklist to Adventure
    public CreateChecklistResponse createChecklist(CreateChecklistRequest req){

        // Get adventure that checklist wants to be added to
        GetAdventureByUUIDRequest advreq = new GetAdventureByUUIDRequest(req.getAdventureID());
        GetAdventureByUUIDResponse retrievedAdventure = this.adventureServiceImplementation.getAdventureByUUID(advreq);

        // Get user that wants to add checklist
        GetUserByUUIDRequest usrreq = new GetUserByUUIDRequest(req.getCreatorID());
        GetUserByUUIDResponse usrres = this.userServiceImplementation.GetUserByUUID(usrreq);

        // Check to see if adventure & user exist
        if(retrievedAdventure.isSuccess() && usrres.isSuccess()){
            Checklist newChecklist = new Checklist(req.getTitle(), req.getDescription(), req.getCreatorID(), req.getAdventureID());
            return new CreateChecklistResponse(newChecklist.getId(), true);
        }
        else {
            return new CreateChecklistResponse(-1, false);
        }

    }

    // Remove Checklist from Adventure
    public RemoveChecklistResponse removeChecklist(RemoveChecklistRequest req){
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
