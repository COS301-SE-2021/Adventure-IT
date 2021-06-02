package com.adventureit.adventureservice.Requests;

import java.util.UUID;

public class RemoveChecklistRequest {
    private long id;
    private UUID adventureID;
    private UUID ownerID;

    /**
     * Parameterized Constructor
     * @param id
     * The id of the checklist to be removed
     * @param ownerID
     * The UUID of the user making the request
     * @param adventureID
     * The UUID of the adventure from which the checklist must be removed
     */

    public RemoveChecklistRequest(long id, UUID ownerID, UUID adventureID){
        this.id = id;
        this.adventureID = adventureID;
        this.ownerID = ownerID;
    }

    public long getId() {
        return id;
    }

    public UUID getAdventureID() {
        return adventureID;
    }

    public UUID getOwnerID() {
        return ownerID;
    }
}
