package com.adventureit.budgetservice.Entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Budget{

    @Id
    private UUID budgetID;
    private UUID creatorID;
    private UUID adventureID;
    private String name;
    private boolean deleted;
    private String description;
//    private double budgetLimit;

    public Budget(){

    }

    public Budget(UUID budgetId,String name, String description, UUID creatorID, UUID adventureID) {
        this.budgetID = budgetId;
        this.creatorID = creatorID;
        this.adventureID = adventureID;
        this.name = name;
        this.deleted = false;
        this.description = description;
    }

    public UUID getBudgetId() {
        return budgetID;
    }

    public void setBudgetId(UUID budgetId) {
        this.budgetID = budgetId;
    }

    public UUID getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(UUID creatorID) {
        this.creatorID = creatorID;
    }

    public UUID getAdventureID() {
        return adventureID;
    }

    public void setAdventureID(UUID adventureID) {
        this.adventureID = adventureID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
