package com.adventureit.budgetservice.Requests;

import com.adventureit.budgetservice.Entity.BudgetEntry;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CreateBudgetRequest {
    private UUID id;
    private String name;
    private UUID creatorID;
    private UUID adventureID;
    private String description;

    public CreateBudgetRequest() {
    }

    public CreateBudgetRequest(@JsonProperty("id") String id, @JsonProperty("name") String name, @JsonProperty("description") String description, @JsonProperty("creatorID") String creatorID, @JsonProperty("adventureID") String  adventureID) {
        this.id = UUID.fromString(id);
        this.name = name;
        this.description=description;
        this.creatorID = UUID.fromString(creatorID);
        this.adventureID = UUID.fromString(adventureID);
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

    public UUID getCreatorID() {
        return creatorID;
    }

}
