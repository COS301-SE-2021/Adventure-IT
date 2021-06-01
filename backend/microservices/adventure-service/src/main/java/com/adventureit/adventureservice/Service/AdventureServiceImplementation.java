package com.adventureit.adventureservice.Service;

import com.adventureit.adventureservice.Entity.Adventure;
import com.adventureit.adventureservice.Requests.AddUserToAdventureRequest;
import com.adventureit.adventureservice.Requests.CreateAdventureRequest;
import com.adventureit.adventureservice.Responses.AddUserToAdventureResponse;
import com.adventureit.adventureservice.Responses.CreateAdventureResponse;
//import com.adventureit.userservice.Service.UserServiceImplementation;
import org.springframework.stereotype.Service;
import java.util.UUID;

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
    public AddUserToAdventureResponse AddUserToAdventure(AddUserToAdventureRequest req)
    {
        /*UUID userID = req.getUserID();
        UUID advID = req.getAdventureID();
        GetUserByUUIDRequest request = new GetUserByUUIDRequest(userID);
        GetUserByUUIDResponse res = user.GetUserByUUID(request);
        User userToBeAdded = null;
        if(res.isSuccess())
        {   userToBeAdded = res.getUser();  }

        return new AddUserToAdventureResponse(true, userToBeAdded.getFirstname()+" "+userToBeAdded.getLastname()+" has been added to adventure: ");
        */
        return new AddUserToAdventureResponse(true, " has been added to adventure: ");
    }


}
