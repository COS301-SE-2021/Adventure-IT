package com.adventureit.checklist.Responses;

import java.util.List;
import java.util.UUID;

public class ChecklistResponseDTO {
    String title;
    String description;
    UUID id;
    UUID creatorID;
    UUID adventureID;
    List<UUID> entries;
    boolean deleted;

    public ChecklistResponseDTO(){}

    public ChecklistResponseDTO(String title, String description, UUID id, UUID creatorID, UUID adventureID, List<UUID> entries, boolean deleted){
        this.title = title;
        this.description = description;
        this.id = id;
        this.creatorID = creatorID;
        this.adventureID = adventureID;
        this.entries = entries;
        this.deleted = deleted;
    }

    public UUID getAdventureID() {
        return adventureID;
    }

    public void setAdventureID(UUID adventureID) {
        this.adventureID = adventureID;
    }

    public String getDescription() {
        return description;
    }

    public void setCreatorID(UUID creatorID) {
        this.creatorID = creatorID;
    }

    public UUID getCreatorID() {
        return creatorID;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setEntries(List<UUID> entries) {
        this.entries = entries;
    }

    public List<UUID> getEntries() {
        return entries;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isDeleted() {
        return deleted;
    }
}