package com.adventureit.adventureservice.service;

import com.adventureit.adventureservice.requests.CreateAdventureRequest;
import com.adventureit.adventureservice.requests.GetAdventureByUUIDRequest;
import com.adventureit.adventureservice.responses.CreateAdventureResponse;
import com.adventureit.adventureservice.responses.GetAdventureByUUIDResponse;
import com.adventureit.adventureservice.responses.GetAdventuresByUserUUIDResponse;
import com.adventureit.adventureservice.responses.GetAllAdventuresResponse;

import java.util.List;
import java.util.UUID;

public interface AdventureService{

    // TODO: Define specific exceptions here, against convention to throw a generic exception
    public CreateAdventureResponse createAdventure(CreateAdventureRequest req) throws Exception;
    public GetAdventureByUUIDResponse getAdventureByUUID (GetAdventureByUUIDRequest req) throws Exception;
    public List<GetAllAdventuresResponse> getAllAdventures();
    public List<GetAdventuresByUserUUIDResponse> getAllAdventuresByUUID(UUID id);
    public List<GetAdventuresByUserUUIDResponse> getAdventureByOwnerUUID(UUID ownerID);
    public List<GetAdventuresByUserUUIDResponse> getAdventureByAttendeeUUID(UUID attendeeID);
    public List<UUID> getAttendees(UUID id) throws Exception;
    public void setAdventureLocation(UUID adventureID, UUID locationID);
    public void addAttendees(UUID adventureID, UUID userID) throws Exception;
}
