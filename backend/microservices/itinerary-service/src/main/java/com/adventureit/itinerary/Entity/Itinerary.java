package com.adventureit.itinerary.Entity;

import com.adventureit.adventureservice.Entity.Entry;
import com.adventureit.adventureservice.Entity.EntryContainer;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Itinerary extends EntryContainer {
    private String title;
    private String description;
    private Boolean deleted;

    // Default constructor
    public Itinerary(){}

    // Parameterized constructor: without prepopulated itinerary entry list
    public Itinerary(String title, String description, UUID id, UUID advID, UUID userID) {
        this.title = title;
        this.description = description;
        this.setEntries(new ArrayList<UUID>());
        this.setAdventureID(advID);
        this.setCreatorID(userID);
        this.setId(id);
        deleted = false;
    }

    // Parameterized constructor: with prepopulated itinerary entry list
    public Itinerary(String title, String description, UUID id, UUID advID, UUID userID, List<UUID> items) {
        this.title = title;
        this.description = description;
        this.setEntries(items);
        this.setAdventureID(advID);
        this.setCreatorID(userID);
        this.setId(id);
        deleted = false;
    }

    // Getters and setters

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
}
