package com.adventureit.adventureservice.Requests;

import java.util.UUID;

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