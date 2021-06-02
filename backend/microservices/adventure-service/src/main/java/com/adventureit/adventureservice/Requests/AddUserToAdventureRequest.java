package com.adventureit.adventureservice.Requests;

import java.util.UUID;

public class AddUserToAdventureRequest {
    private UUID id;
    private UUID advID;

    public AddUserToAdventureRequest(UUID id, UUID advID)
    {
        this.id = id;
        this.advID = advID;
    }

    public UUID getUserID()
    {
        return this.id;
    }

    public UUID getAdventureID()
    {
        return this.advID;
    }

    public void setUserID(UUID id)
    {
        this.id = id;
    }

    public void setAdventureID(UUID advID)
    {
        this.advID = advID;
    }
}
