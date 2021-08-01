package com.adventureit.budgetservice.Requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class CalculateExpensesPerUserRequest {
    private UUID budgetID;
    private UUID userID;

    public CalculateExpensesPerUserRequest(){}

    public CalculateExpensesPerUserRequest(@JsonProperty("budgetID") UUID budgetID, @JsonProperty("userID") UUID userID ){
        this.budgetID = budgetID;
        this.userID = userID;
    }

    public UUID getUserID() {
        return userID;
    }

    public UUID getBudgetID() {
        return budgetID;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
    }

    public void setBudgetID(UUID budgetID) {
        this.budgetID = budgetID;
    }
}
