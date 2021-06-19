package com.adventureit.budgetservice.Requests;

import java.util.UUID;

public class RemoveEntryRequest {
    private UUID id;
    private UUID budgetID;

    public RemoveEntryRequest(){}

    public RemoveEntryRequest(UUID id, UUID budgetID){
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
