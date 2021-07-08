package com.adventureit.adventureservice.Entity;

import javax.persistence.Entity;
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
    private UUID id;
    private UUID entryContainerID;

    // Default constructor
    public Entry(){}

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
}
