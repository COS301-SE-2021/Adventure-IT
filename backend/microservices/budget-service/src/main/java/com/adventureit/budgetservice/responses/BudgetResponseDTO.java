package com.adventureit.budgetservice.responses;

import java.util.UUID;

public class BudgetResponseDTO {
    private UUID id;
    private String name;
    private final UUID creatorID;
    private final UUID adventureID;
    private final boolean deleted;
    private String description;

    public BudgetResponseDTO(UUID id, String name, UUID creatorID, UUID adventureID,boolean deleted, String description){
        this.id = id;
        this.name = name;
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

    public UUID getAdventureID() {
        return adventureID;
    }

    public String getDescription(){return description;}

    public void setDescription(String d){this.description=d;}

    public boolean isDeleted(){
        return this.deleted;
    }

    public UUID getCreatorID(){
        return this.creatorID;
    }
}
