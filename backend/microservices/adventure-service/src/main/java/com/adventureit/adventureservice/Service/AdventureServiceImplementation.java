package com.adventureit.adventureservice.Service;

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
        return null;
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

        return new AddUserToAdventureResponse(true, userToBeAdded.getFirstname()+" "+userToBeAdded.getLastname+" has been added to adventure: ");
        */
        return new AddUserToAdventureResponse(true, " has been added to adventure: ");
    }


}
