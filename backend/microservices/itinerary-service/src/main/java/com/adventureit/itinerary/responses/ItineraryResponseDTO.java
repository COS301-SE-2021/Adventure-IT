package com.adventureit.itinerary.responses;

import java.util.UUID;

public class ItineraryResponseDTO {
    String title;
    String description;
    UUID id;
    UUID creatorID;
    UUID adventureID;
    boolean deleted;

    public ItineraryResponseDTO(){}

    public ItineraryResponseDTO(String title, String description, UUID id, UUID creatorID, UUID adventureID,  boolean deleted){
        this.title = title;
        this.description = description;
        this.id = id;
        this.creatorID = creatorID;
        this.adventureID = adventureID;
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
