package com.adventureit.adventureservice.Service;

import com.adventureit.adventureservice.Entity.Adventure;
import com.adventureit.adventureservice.Entity.Checklist;
import com.adventureit.adventureservice.Entity.EntryContainer;
import com.adventureit.adventureservice.Requests.AddUserToAdventureRequest;
import com.adventureit.adventureservice.Requests.CreateAdventureRequest;
import com.adventureit.adventureservice.Requests.GetAdventureByUUIDRequest;
import com.adventureit.adventureservice.Responses.AddUserToAdventureResponse;
import com.adventureit.adventureservice.Responses.CreateAdventureResponse;
//import com.adventureit.userservice.Service.UserServiceImplementation;
import com.adventureit.adventureservice.Responses.GetAdventureByUUIDResponse;
import com.adventureit.userservice.Entities.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service("AdventureServiceImplementation")
public class AdventureServiceImplementation implements AdventureService{
    //private UserServiceImplementation user;

    @Override
    public CreateAdventureResponse createAdventure(CreateAdventureRequest req) {

        Adventure adventure = new Adventure();
        CreateAdventureResponse response = new CreateAdventureResponse();
        adventure.setId(UUID.randomUUID());

        if(req.getName() == "" || req.getName() ==  null){
            return new CreateAdventureResponse(false);
        }else {
            adventure.setName(req.getName());
        }

        if(req.getContainers() ==  null){
            return new CreateAdventureResponse(false);
        }else {
            adventure.setContainers(req.getContainers());
        }

        if(req.getGroup() ==  null){
            return new CreateAdventureResponse(false);
        }
        else {
            adventure.setGroup(req.getGroup());
        }

        if(req.getOwner() ==  null){
            return new CreateAdventureResponse(false);
        }
        else {
            adventure.setOwner(req.getOwner());
        }

        return new CreateAdventureResponse(true, adventure);
    }

    @Override
    public GetAdventureByUUIDResponse getAdventureByUUID (GetAdventureByUUIDRequest req){

        UUID id = UUID.fromString("e9b19e5c-4197-4f88-814a-5e51ff305f7b");
        String name = "Adventure1";
        User owner = new User();
        owner.setUserID(UUID.fromString("933c0a14-a837-4789-991a-15006778f465"));
        Checklist checklist = new Checklist();
        checklist.setId(1);
        checklist.setAdventureID(UUID.fromString("e9b19e5c-4197-4f88-814a-5e51ff305f7b"));
        checklist.setCreatorID(UUID.fromString("933c0a14-a837-4789-991a-15006778f465"));
        ArrayList<EntryContainer> container = new ArrayList(){};
        ArrayList<String> group = new ArrayList();
        Adventure adventure = new Adventure(name,id,owner,group,container);
        return new GetAdventureByUUIDResponse(true,adventure);

    }

    @Override
    public AddUserToAdventureResponse AddUserToAdventure(AddUserToAdventureRequest req)
    {
        Adventure adventure = new Adventure();
        /*UUID userID = req.getUserID();
        UUID advID = req.getAdventureID();
        GetUserByUUIDRequest request = new GetUserByUUIDRequest(userID);
        GetUserByUUIDResponse res = user.GetUserByUUID(request);
        User userToBeAdded = null;
        if(res.isSuccess())
        {   userToBeAdded = res.getUser();  }

        return new AddUserToAdventureResponse(true, userToBeAdded.getFirstname()+" "+userToBeAdded.getLastname()+" has been added to adventure: "+adventure.name);
        */
        return new AddUserToAdventureResponse(true, " has been added to adventure: ");
    }


}
