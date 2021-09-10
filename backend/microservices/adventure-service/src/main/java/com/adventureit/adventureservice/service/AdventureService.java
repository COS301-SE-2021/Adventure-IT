package com.adventureit.adventureservice.service;

import com.adventureit.shareddtos.adventure.requests.EditAdventureRequest;
import com.adventureit.shareddtos.adventure.requests.GetAdventureByUUIDRequest;
import com.adventureit.shareddtos.adventure.responses.CreateAdventureResponse;
import com.adventureit.shareddtos.adventure.responses.GetAdventureByUUIDResponse;
import com.adventureit.shareddtos.adventure.responses.GetAdventuresByUserUUIDResponse;
import com.adventureit.shareddtos.adventure.responses.GetAllAdventuresResponse;
import com.adventureit.shareddtos.adventure.requests.CreateAdventureRequest;

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
    String editAdventure(EditAdventureRequest req);
}
