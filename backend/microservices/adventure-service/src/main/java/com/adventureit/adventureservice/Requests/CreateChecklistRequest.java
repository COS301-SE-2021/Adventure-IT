package com.adventureit.adventureservice.Requests;

import java.util.UUID;

public class CreateChecklistRequest {
    private UUID adventureID;
    private UUID creatorID;
    private String title;
    private String description;

    /**
     * Parameterized Constructor
     * @param title
     * The title of the checklist to be created
     * @param description
     * The description of the checklist to be created
     * @param creatorID
     * The UUID of the creator of the checklist
     * @param adventureID
     * The UUID of the adventure to which the checklist must be added
     */
    public CreateChecklistRequest(String title, String description, UUID creatorID, UUID adventureID){
        this.adventureID = adventureID;
        this.creatorID = creatorID;
        this.title = title;
        this.description = description;
    }

    public UUID getAdventureID() {
        return adventureID;
    }

    public UUID getCreatorID() {
        return creatorID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
