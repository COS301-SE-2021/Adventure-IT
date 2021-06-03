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

    /**
     *
     * @param req
     * Attributes which will be attained from the req param will include:
     * Name of the Adventure (name)
     * ID of the Adventure (id)
     * Owner of the Adventure (owner)
     * Groups of the Adventure (group)
     * Containers of the Adventure (Containers)
     *
     * Using the request object the CreateAdventure Service will:
     * 1. Check if id is not null
     * 2. Check if name is not empty or null
     * 3. Check if Containers is not null
     * 4. Check if group is not null
     * 5. Check if owner is not null
     * 6. Create and add new Adventure to database
     *
     * @return CreateAdventureResponse Object which will indicate whether
     * registration was successful or if an error occurred
     */
    @Override
    public CreateAdventureResponse createAdventure(CreateAdventureRequest req){

        Adventure adventure = new Adventure();
        CreateAdventureResponse response = new CreateAdventureResponse();
        adventure.setId(UUID.randomUUID());
        if(req.getId() == null){
            return new CreateAdventureResponse(false);
        }
        if(req.getName() == "" || req.getName() ==  null){
            return new CreateAdventureResponse(false);
        }else {
            adventure.setName(req.getName());
        }
        if(req.getContainers() ==  null){
            return new CreateAdventureResponse(false);
        }else{
            adventure.setContainers(req.getContainers());
        }
        if(req.getGroup() ==  null){
            return new CreateAdventureResponse(false);
        }
        else{
            adventure.setGroup(req.getGroup());
        }
        if(req.getOwner() ==  null){
            return new CreateAdventureResponse(false);
        }
        else{
            adventure.setOwner(req.getOwner());
        }

        return new CreateAdventureResponse(true, adventure);
    }

    /**
     * Get Adventure By UUID is currently a mock service which returns a set adventure until a persistence layer is created
     * The service will acquire the id from the request object then return an adventure with a set name, owner, checklist,
     * container and group.
     *
     * NB: This currently only for testing purposes.
     *
     * When the persistence layer is created the Service will search the database for a specific Adventure.
     * @param req a GetAdventureByUUID request will be sent in with the id of the adventure that should be retrieved
     * @return returns a GetAdventureByUUID response which currently is a set adventure for testing purposes
     */
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

    /**
     * @param req
     * Attributes used from the req attribute:
     * Adventure ID (advID)
     * User ID (userID)
     *
     * Using the request object from the AddUserToAdventure service will:
     * 1. Add user to array list in adventure
     *
     * @return AddUserToAdventureResponse object will indicate whether the user
     * has been successfully added to the adventure or whether an error occured
     */
    @Override
    public AddUserToAdventureResponse AddUserToAdventure(AddUserToAdventureRequest req)
    {
        Adventure adventure = new Adventure();
        UUID advID = req.getAdventureID();
        GetAdventureByUUIDRequest request0 = new GetAdventureByUUIDRequest(advID);
        //GetAdventureByUUIDResponse res0 = adventure.getAdventureByUUID(request0);
        /*UUID userID = req.getUserid();
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
