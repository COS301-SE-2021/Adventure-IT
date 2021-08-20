package com.adventureit.budgetservice.Requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class CreateBudgetRequest {
    private String name;
    private UUID creatorID;
    private UUID adventureID;
    private String description;

    public CreateBudgetRequest() {
    }

    public CreateBudgetRequest(@JsonProperty("name") String name, @JsonProperty("description") String description, @JsonProperty("creatorID") String creatorID, @JsonProperty("adventureID") String  adventureID) {
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
