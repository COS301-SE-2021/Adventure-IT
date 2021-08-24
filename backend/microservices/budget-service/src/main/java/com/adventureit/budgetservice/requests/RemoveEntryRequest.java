package com.adventureit.budgetservice.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class RemoveEntryRequest {
    private UUID id;
    private UUID budgetID;

    public RemoveEntryRequest(){}

    public RemoveEntryRequest(@JsonProperty("id") UUID id,@JsonProperty("budgetID") UUID budgetID){
        this.id = id;
        this.budgetID = budgetID;
    }

    public UUID getBudgetID() {
        return budgetID;
    }

    public void setBudgetID(UUID budgetID) {
        this.budgetID = budgetID;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
