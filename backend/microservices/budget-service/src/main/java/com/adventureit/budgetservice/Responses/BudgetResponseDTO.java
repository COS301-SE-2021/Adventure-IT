package com.adventureit.budgetservice.Responses;

import java.util.List;
import java.util.UUID;

public class BudgetResponseDTO {
    private UUID id;
    private String name;
    private UUID creatorID;
    private UUID adventureID;
    private boolean deleted;
    private String description;

    public BudgetResponseDTO(){}

    public BudgetResponseDTO(UUID id, String name, UUID creatorID, UUID adventureID,boolean deleted, String description){
        this.id = id;
        this.name = name;;
        this.creatorID = creatorID;
        this.adventureID = adventureID;
        this.deleted = deleted;
        this.description=description;
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

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setAdventureID(UUID adventureID) {
        this.adventureID = adventureID;
    }

    public UUID getAdventureID() {
        return adventureID;
    }

    public void setCreatorID(UUID creatorID) {
        this.creatorID = creatorID;
    }

    public UUID getCreatorID() {
        return creatorID;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public String getDescription(){return description;}

    public void setDescription(String d){this.description=d;}
}
