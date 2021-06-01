package com.adventureit.adventureservice.Service;

import com.adventureit.adventureservice.Requests.AddUserToAdventureRequest;
import com.adventureit.adventureservice.Requests.CreateAdventureRequest;
import com.adventureit.adventureservice.Responses.AddUserToAdventureResponse;
import com.adventureit.adventureservice.Responses.CreateAdventureResponse;
import org.springframework.stereotype.Service;
import java.util.UUID;

public class AdventureServiceImplementation implements AdventureService{

    @Override
    public CreateAdventureResponse createAdventure(CreateAdventureRequest req) {
        return null;
    }

    @Override
    public AddUserToAdventureResponse AddUserToAdventure(AddUserToAdventureRequest req)
    {
        return new AddUserToAdventureResponse(true, " has been added to adventure: ");
    }


}
