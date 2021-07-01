package com.adventureit.adventureservice.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;
/**
 *
 * The params taken in by constructor and what they represent:
 * 1. long id: The id of the entry to be added to the EntryContainer
 * 2. long entryContainerID: The id of the entryContainer that the entry is part of
 *
 */

@Entity
public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long entryContainerID;

    // Default constructor
    public Entry(){}

    // Getters and setters

    public long getEntryContainerID() {
        return entryContainerID;
    }

    public void setEntryContainerID(long entryConID){this.entryContainerID=entryConID;}

    public long getId() {
        return id;
    }
}
