package com.adventureit.itinerary.Entity;

import com.adventureit.adventureservice.Entity.Entry;
import com.adventureit.adventureservice.Entity.EntryContainer;
import com.adventureit.adventureservice.Entity.ItineraryEntry;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
/**
 *
 * Itinerary is a subclass of the Entry entity!
 *
 * The params taken in by constructor and what they represent:
 * 1. String title: The title of the entryContainer (in this instance, an Itinerary)
 * 2. String description: A description of the Itinerary
 * 3. UUID adventureID: The id of the adventure that this EntryContainer is attached to
 * 4. UUID userID: The id of the user that has created the itinerary and, therefore, receives certain permissions
 *
 */
public class Itinerary extends EntryContainer {
    private String title;
    private String description;
    @OneToMany
    private List<Entry> items = new ArrayList<Entry>();

    // Default constructor
    public Itinerary(){}

    // Parameterized constructor: without prepopulated itinerary entry list
    public Itinerary(String title, String description, UUID id, UUID advID, UUID userID) {
        this.title = title;
        this.description = description;
        this.items = new ArrayList<Entry>();
        this.setAdventureID(advID);
        this.setCreatorID(userID);
        this.setId(id);
    }

    // Parameterized constructor: with prepopulated itinerary entry list
    public Itinerary(String title, String description, UUID id, UUID advID, UUID userID, List<Entry> items) {
        this.title = title;
        this.description = description;
        this.items = items;
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

    public List<Entry> getItems() {
        return items;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setItems(List<Entry> items) {
        this.items = items;
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

}
