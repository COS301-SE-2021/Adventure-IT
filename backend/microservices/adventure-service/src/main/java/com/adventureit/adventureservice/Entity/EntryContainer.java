package com.adventureit.adventureservice.Entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class EntryContainer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private UUID creatorID;
    private UUID adventureID;

    // Default constructor
    public EntryContainer(){}

    public UUID getCreatorID(){return this.creatorID;}

    public UUID getAdventureID(){return this.adventureID;}

    public void setCreatorID(UUID cID){this.creatorID=cID;}

    public void setAdventureID(UUID aID){this.adventureID=aID;}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
