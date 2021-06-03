package com.adventureit.adventureservice.Entity;

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
    private List<ItineraryEntry> items;

    // Default constructor
    public Itinerary(){}

    // Parameterized constructor: without prepopulated itinerary entry list
    public Itinerary(String title, String description, UUID advID, UUID userID) {
        this.title = title;
        this.description = description;
        this.items = new ArrayList<ItineraryEntry>();
        this.setAdventureID(advID);
        this.setCreatorID(userID);
    }

    // Parameterized constructor: with prepopulated itinerary entry list
    public Itinerary(String title, String description, UUID advID, UUID userID, List<ItineraryEntry> items) {
        this.title = title;
        this.description = description;
        this.items = items;
        this.setAdventureID(advID);
        this.setCreatorID(userID);
    }

    // Method for getting a checklist item at a provided index
    public ItineraryEntry getItem(int index) {
        return this.items.get(index);
    }

    // Method for adding a single checklist item
    public void addItem(ItineraryEntry item) {
        this.items.add(item);
    }

    // Getters and setters

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public List<ItineraryEntry> getItems() {
        return this.items;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setItems(List<ItineraryEntry> items) {
        this.items = items;
    }

}
