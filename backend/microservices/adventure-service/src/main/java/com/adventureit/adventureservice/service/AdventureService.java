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

    CreateAdventureResponse createAdventure(CreateAdventureRequest req);
    GetAdventureByUUIDResponse getAdventureByUUID (GetAdventureByUUIDRequest req);
    List<GetAllAdventuresResponse> getAllAdventures();
    List<GetAdventuresByUserUUIDResponse> getAllAdventuresByUUID(UUID id);
    List<GetAdventuresByUserUUIDResponse> getAdventureByOwnerUUID(UUID ownerID);
    List<GetAdventuresByUserUUIDResponse> getAdventureByAttendeeUUID(UUID attendeeID);
    List<UUID> getAttendees(UUID id);
    void setAdventureLocation(UUID adventureID, UUID locationID);
    void addAttendees(UUID adventureID, UUID userID);
    String removeAttendees(UUID adventureID, UUID userID);
}
