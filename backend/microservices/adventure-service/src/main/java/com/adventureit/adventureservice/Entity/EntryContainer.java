package com.adventureit.adventureservice.Entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * The params taken in by constructor and what they represent:
 * 1. long Id: the id of the EntryContainer
 * 2. UUID creatorID: the id of the user that creates the entryContainer and, therefore, gets special permissions
 * 3. UUID adventureID: the id of the adventure of that this EntryContainer is attached to
 *
 */

@Entity
public class EntryContainer {
    @Id
    private UUID id;
    private UUID creatorID;
    private UUID adventureID;
    @ElementCollection (fetch=FetchType.EAGER)
    private List<UUID> entries = new ArrayList<UUID>();

    public EntryContainer(UUID adventureID, UUID creatorID){
        this.adventureID = adventureID;
        this.creatorID = creatorID;
    }

    // Default constructor
    public EntryContainer(){}

    public UUID getCreatorID(){return this.creatorID;}

    public UUID getAdventureID(){return this.adventureID;}

    public void setCreatorID(UUID cID){this.creatorID=cID;}

    public void setAdventureID(UUID aID){this.adventureID=aID;}

    public UUID getId() {return id;}

    public void setId(UUID id) {
        this.id = id;
    }

    public void setEntries(List<UUID> entries) {
        this.entries = entries;
    }

    public List<UUID> getEntries() {
        return entries;
    }
}
