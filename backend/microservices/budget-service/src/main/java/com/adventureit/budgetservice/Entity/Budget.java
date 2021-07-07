package com.adventureit.budgetservice.Entity;

import com.adventureit.adventureservice.Entity.Entry;
import com.adventureit.adventureservice.Entity.EntryContainer;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Budget extends EntryContainer {
    @Id
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

    public Budget(UUID id,String name,UUID creatorID, UUID adventureID, List<Entry> entries){
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

    /**
     * Helper functions
     */
    public boolean CheckIfEntryExists(List<Entry> entries, UUID id) {
        boolean result = false;
        for (Entry b : entries) {
            if (b.getId().equals(id)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public Entry getEntry(List<Entry> entries, UUID id) {
        Entry result = null;
        for (Entry b : entries) {
            if (b.getId().equals(id)) {
                result = b;
                break;
            }
        }
        return result;
    }

    public int getIndex(List<Entry> entries, UUID id) {
        int result = 0;
        for (Entry b : entries) {
            if (b.getId().equals(id)) {
                break;
            }
            result++;
        }
        return result;
    }

}
