package com.adventureit.adventureservice.Service;

import com.adventureit.adventureservice.Requests.CreateAdventureRequest;
import com.adventureit.adventureservice.Requests.GetAdventureByUUIDRequest;
import com.adventureit.adventureservice.Responses.*;

import java.util.List;
import java.util.UUID;

public interface AdventureService{

    public CreateAdventureResponse createAdventure(CreateAdventureRequest req) throws Exception;
    public GetAdventureByUUIDResponse getAdventureByUUID (GetAdventureByUUIDRequest req) throws Exception;
    public GetAllAdventuresResponse getAllAdventures();
    public List<AdventureDTO> getAdventureByOwnerUUID(UUID ownerID);
    public List<AdventureDTO> getAdventureByAttendeeUUID(UUID attendeeID);
    public void mockPopulate();
//   public AddUserToAdventureResponse AddUserToAdventure(AddUserToAdventureRequest req);
}
