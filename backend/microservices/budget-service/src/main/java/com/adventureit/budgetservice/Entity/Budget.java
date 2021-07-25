package com.adventureit.budgetservice.Entity;

import com.adventureit.adventureservice.Entity.EntryContainer;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class Budget extends EntryContainer {
    String name;
    boolean deleted;
    double limit;
    String description;

    /**
     * Default Constructor
     */
    public Budget(){}


    public Budget(UUID id,String name, String description,UUID creatorID, UUID adventureID, double limit){
        this.setId(id);
        this.name = name;
        this.description=description;
        this.setCreatorID(creatorID);
        this.setAdventureID(adventureID);
        deleted = false;
        this.limit = limit;
    }

    public Budget(UUID id,String name, String description,UUID creatorID, UUID adventureID, List<UUID> entries, double limit){
        this.setId(id);
        this.name = name;
        this.description=description;
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

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }

    public String getDescription(){return description;}
}
