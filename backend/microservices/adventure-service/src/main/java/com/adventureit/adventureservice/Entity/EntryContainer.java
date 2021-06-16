package com.adventureit.adventureservice.Entity;

import javax.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private UUID creatorID;
    private UUID adventureID;
    @OneToMany
    private List<Entry> entries;

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

    public long getId() {return id;}

    public List<Entry> getEntries(){
        return this.entries;
    }
}
