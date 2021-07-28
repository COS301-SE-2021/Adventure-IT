package com.adventureit.budgetservice.Requests;

import com.adventureit.budgetservice.Entity.BudgetEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CreateBudgetRequest {
    private UUID id;
    private String name;
    private UUID creatorID;
    private UUID adventureID;
    private double limit;
    private String description;

    public CreateBudgetRequest() {
    }

    public CreateBudgetRequest(UUID id, String name, String description, UUID creatorID, UUID adventureID,double limit) {
        this.id = id;
        this.name = name;
        this.description=description;
        this.creatorID = creatorID;
        this.adventureID = adventureID;
        this.limit = limit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public double getLimit() {
        return limit;
    }

    public void setAdventureID(UUID adventureID) {
        this.adventureID = adventureID;
    }

    public UUID getAdventureID() {
        return adventureID;
    }

    public String getDescription(){return description;}

    public void setCreatorID(UUID creatorID) {
        this.creatorID = creatorID;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }

    public UUID getCreatorID() {
        return creatorID;
    }

}
