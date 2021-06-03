package com.adventureit.adventureservice.Service;

import com.adventureit.adventureservice.Requests.AddUserToAdventureRequest;
import com.adventureit.adventureservice.Requests.CreateAdventureRequest;
import com.adventureit.adventureservice.Requests.GetAdventureByUUIDRequest;
import com.adventureit.adventureservice.Responses.AddUserToAdventureResponse;
import com.adventureit.adventureservice.Responses.CreateAdventureResponse;
import com.adventureit.adventureservice.Responses.GetAdventureByUUIDResponse;
import org.springframework.stereotype.Service;

public interface AdventureService{

    public CreateAdventureResponse createAdventure(CreateAdventureRequest req);
    public GetAdventureByUUIDResponse getAdventureByUUID (GetAdventureByUUIDRequest req);
    public AddUserToAdventureResponse AddUserToAdventure(AddUserToAdventureRequest req);
}
