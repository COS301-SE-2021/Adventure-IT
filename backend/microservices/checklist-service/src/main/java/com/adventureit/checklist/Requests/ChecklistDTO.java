package com.adventureit.checklist.Requests;

import java.util.UUID;

public class ChecklistDTO {

    private UUID id;
    private UUID creatorID;
    private UUID adventureID;
    private String title;
    private String description;
    private boolean deleted;

    ChecklistDTO(){}

    public ChecklistDTO(UUID id, UUID creatorID, UUID adventureID, String title, String description, boolean deleted) {
        this.id = id;
        this.creatorID = creatorID;
        this.adventureID = adventureID;
        this.title = title;
        this.description = description;
        this.deleted = deleted;
    }

    public UUID getId() {
        return id;
    }

    public UUID getCreatorID() {
        return creatorID;
    }

    public UUID getAdventureID() {
        return adventureID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDeleted() {
        return deleted;
    }
}
