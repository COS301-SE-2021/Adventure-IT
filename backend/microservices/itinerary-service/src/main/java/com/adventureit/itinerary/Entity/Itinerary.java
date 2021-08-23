package com.adventureit.itinerary.Entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Itinerary {

    @Id
    private UUID id;
    private UUID creatorID;
    private UUID adventureID;
    private String title;
    private String description;
    private Boolean deleted;

    /**
     * Default constructor
     */
    public Itinerary(){}

    /**
     * Parameterized constructor: without prepopulated itinerary entry list
     */
    public Itinerary(String title, String description, UUID id, UUID advID, UUID userID) {
        this.title = title;
        this.description = description;
        this.id = id;
        this.creatorID = userID;
        this.adventureID = advID;
        deleted = false;
    }

    public Itinerary(String title, String description, UUID advID, UUID userID) {
        this.title = title;
        this.description = description;
        this.id = UUID.randomUUID();
        this.creatorID = userID;
        this.adventureID = advID;
        deleted = false;
    }

    /**
     * Getters and Setters
     */

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(UUID creatorID) {
        this.creatorID = creatorID;
    }

    public UUID getAdventureID() {
        return adventureID;
    }

    public void setAdventureID(UUID adventureID) {
        this.adventureID = adventureID;
    }
}
