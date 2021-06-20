package com.adventureit.adventureservice.Service;

import com.adventureit.adventureservice.Requests.CreateAdventureRequest;
import com.adventureit.adventureservice.Requests.GetAdventureByUUIDRequest;
import com.adventureit.adventureservice.Responses.CreateAdventureResponse;
import com.adventureit.adventureservice.Responses.GetAdventureByUUIDResponse;
import com.adventureit.adventureservice.Responses.GetAdventuresByUserUUIDResponse;
import com.adventureit.adventureservice.Responses.GetAllAdventuresResponse;

import java.util.UUID;

public interface AdventureService{

    public CreateAdventureResponse createAdventure(CreateAdventureRequest req) throws Exception;
    public GetAdventureByUUIDResponse getAdventureByUUID (GetAdventureByUUIDRequest req) throws Exception;
    public GetAllAdventuresResponse getAllAdventures();
    public GetAdventuresByUserUUIDResponse getAdventureByOwnerUUID(UUID ownerID);
    public GetAdventuresByUserUUIDResponse getAdventureByAttendeeUUID(UUID attendeeID);
    public void mockPopulate();
//    public AddUserToAdventureResponse AddUserToAdventure(AddUserToAdventureRequest req);
}
