package com.adventureit.adventureservice.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

public class ItineraryEntry extends Entry {
    private String title;
    private String description;

    // Default constructor
    public ItineraryEntry(){}

    // Parameterized constructor: with only title, description and entryContainerID
    public ItineraryEntry(String title, String description,  long entryConID){
        this.title = title;
        this.description=description;
        this.setEntryContainerID(entryConID);
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
