package com.adventureit.itinerary.Entity;

import com.adventureit.adventureservice.Entity.Entry;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;
/**
 *
 * ItineraryEntry is a subclass of the Entry entity!
 *
 * The params taken in by constructor and what they represent:
 * 1. String title: The title of the entry
 * 2. String description: A description of the Itinerary entry
 * 3. Long entryContainerID: The id of the entryContainer (The itinerary, in this instance) that the entry is attached to
 *
 */
@Entity
public class ItineraryEntry extends Entry {
    private String title;
    private String description;

    // Default constructor
    public ItineraryEntry(){}

    // Parameterized constructor: with only title, description and entryContainerID
    public ItineraryEntry(String title, String description, UUID id,  UUID entryContainerID){
        this.title = title;
        this.description=description;
        this.setEntryContainerID(entryContainerID);
        this.setId(id);
    }

    // Getters and setters

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String desc)
    {
        this.description = desc;
    }

}