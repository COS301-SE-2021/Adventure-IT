package com.adventureit.checklist.Requests;

import java.util.UUID;

public class CreateChecklistRequest {
    String title;
    String description;
    UUID creatorID;
    UUID adventureID;

    public CreateChecklistRequest(String title, String description, String id, String creatorID, String adventureID){
        this.title = title;
        this.description = description;
        this.creatorID = UUID.fromString(creatorID);
        this.adventureID = UUID.fromString(adventureID);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public UUID getCreatorID() {
        return creatorID;
    }

    public UUID getAdventureID() {
        return adventureID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatorID(UUID creatorID) {
        this.creatorID = creatorID;
    }

    public void setAdventureID(UUID adventureID) {
        this.adventureID = adventureID;
    }
}
