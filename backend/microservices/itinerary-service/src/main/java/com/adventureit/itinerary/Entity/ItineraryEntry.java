package com.adventureit.itinerary.Entity;

import com.adventureit.adventureservice.Entity.Entry;

import javax.persistence.*;
import java.time.LocalDateTime;
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
public class ItineraryEntry{

    @Id
    private UUID id;
    private UUID entryContainerID;
    private String title;
    private String description;
    private boolean completed;
    private String location;
    LocalDateTime timestamp;

    // Default constructor
    public ItineraryEntry(){}

    // Parameterized constructor: with only title, description and entryContainerID
    public ItineraryEntry(String title, String description, UUID id,  UUID entryContainerID, boolean completed, String location, LocalDateTime timestamp){
        this.title = title;
        this.description=description;
        this.entryContainerID =entryContainerID;
        this.id = id;
        this.completed = completed;
        this.location = location;
        this.timestamp = timestamp;
    }

    public ItineraryEntry(String title, String description, UUID id,  UUID entryContainerID, String location, LocalDateTime timestamp){
        this.title = title;
        this.description=description;
        this.entryContainerID =entryContainerID;
        this.id = id;
        this.completed = false;
        this.location = location;
        this.timestamp = timestamp;
    }


    // Getters and setters


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getEntryContainerID() {
        return entryContainerID;
    }

    public void setEntryContainerID(UUID entryContainerID) {
        this.entryContainerID = entryContainerID;
    }

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isCompleted() {
        return completed;
    }
}
