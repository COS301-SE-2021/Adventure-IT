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
//    @OneToMany
//    private List<Entry> items = new ArrayList<Entry>();

    // Default constructor
    public Itinerary(){}

    // Parameterized constructor: without prepopulated itinerary entry list
    public Itinerary(String title, String description, UUID id, UUID advID, UUID userID) {
        this.title = title;
        this.description = description;
        this.setEntries(new ArrayList<Entry>());
        this.setAdventureID(advID);
        this.setCreatorID(userID);
        this.setId(id);
    }

    // Parameterized constructor: with prepopulated itinerary entry list
    public Itinerary(String title, String description, UUID id, UUID advID, UUID userID, List<Entry> items) {
        this.title = title;
        this.description = description;
        this.setEntries(items);
        this.setAdventureID(advID);
        this.setCreatorID(userID);
        this.setId(id);
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

    public boolean CheckIfEntryExists(List<Entry> entries, UUID id) {
        boolean result = false;
        for (Entry b : entries) {
            if (b.getId().equals(id)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public Entry getEntry(List<Entry> entries, UUID id) {
        Entry result = null;
        for (Entry b : entries) {
            if (b.getId().equals(id)) {
                result = b;
                break;
            }
        }
        return result;
    }

    public int getIndex(List<Entry> entries, UUID id) {
        int result = 0;
        for (Entry b : entries) {
            if (b.getId().equals(id)) {
                break;
            }
            result++;
        }
        return result;
    }

}
