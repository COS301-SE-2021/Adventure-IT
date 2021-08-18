package com.adventureit.budgetservice.Requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class CalculateExpensesPerUserRequest {
    private UUID budgetID;
    private String userName;

    public CalculateExpensesPerUserRequest(){}

    public CalculateExpensesPerUserRequest(@JsonProperty("budgetID") UUID budgetID, @JsonProperty("userName") String userName ){
        this.budgetID = budgetID;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public UUID getBudgetID() {
        return budgetID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setBudgetID(UUID budgetID) {
        this.budgetID = budgetID;
    }
}
