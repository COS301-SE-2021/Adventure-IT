package com.adventureit.adventureservice.Requests;

import java.util.UUID;
/**
 *
 * The params taken in by constructor and what they represent:
 * 1. long ItineraryId: the id of the Itinerary the user wishes to remove
 * 2. UUID adventureID: the id of the adventure that the itienrary is attached to
 * 3. UUID userID: the ID of the user that wishes to remove the itinerary
 *
 */

public class RemoveItineraryRequest {
    private long id;
    private UUID adventureID;
    private UUID userID;

    public RemoveItineraryRequest(long ItineraryId, UUID aID, UUID useID)
    {
        this.id=ItineraryId;
        this.adventureID=aID;
        this.userID=useID;
    }

    public UUID getUserID() {
        return userID;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UUID getAdventureID() {
        return adventureID;
    }

    public void setAdventureID(UUID adventureID) {
        this.adventureID = adventureID;
    }
}