package com.adventureit.budgetservice.Entity;

import com.adventureit.adventureservice.Entity.Entry;
import com.adventureit.adventureservice.Entity.EntryContainer;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Budget extends EntryContainer {
    String name;
    boolean deleted;

    /**
     * Default Constructor
     */
    public Budget(){}


    public Budget(UUID id,String name,UUID creatorID, UUID adventureID){
        this.setId(id);
        this.name = name;;
        this.setCreatorID(creatorID);
        this.setAdventureID(adventureID);
        deleted = false;
    }

    public Budget(UUID id,String name,UUID creatorID, UUID adventureID, List<UUID> entries){
        this.setId(id);
        this.name = name;;
        this.setCreatorID(creatorID);
        this.setAdventureID(adventureID);
        this.setEntries(entries);
        deleted = false;
    }

    /**
     * Getters and Setters
     */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isDeleted() {
        return deleted;
    }

}
