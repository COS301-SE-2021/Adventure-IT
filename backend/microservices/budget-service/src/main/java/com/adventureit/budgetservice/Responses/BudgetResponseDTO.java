package com.adventureit.budgetservice.Responses;

import java.util.List;
import java.util.UUID;

public class BudgetResponseDTO {
    private UUID id;
    private String name;
    private UUID creatorID;
    private UUID adventureID;
    private List<UUID> entries;
    private double limit;
    private boolean deleted;

    public BudgetResponseDTO(){}

    public BudgetResponseDTO(UUID id, String name, UUID creatorID, UUID adventureID,List<UUID> entries,double limit,boolean deleted){
        this.id = id;
        this.name = name;;
        this.creatorID = creatorID;
        this.adventureID = adventureID;
        this.entries = entries;
        this.limit = limit;
        this.deleted = deleted;
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

    public List<UUID> getEntries() {
        return entries;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public double getLimit() {
        return limit;
    }

    public void setEntries(List<UUID> entries) {
        this.entries = entries;
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

    public void setLimit(double limit) {
        this.limit = limit;
    }

    public UUID getCreatorID() {
        return creatorID;
    }

    public boolean isDeleted() {
        return deleted;
    }
}
