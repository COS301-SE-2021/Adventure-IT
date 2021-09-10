package com.adventureit.shareddtos.budget.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class CreateBudgetRequest {
    private String name;
    private final UUID creatorID;
    private final UUID adventureID;
    private final String description;

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

    public UUID getAdventureID() {
        return adventureID;
    }

    public String getDescription(){return description;}

    public UUID getCreatorID() {
        return creatorID;
    }

}
