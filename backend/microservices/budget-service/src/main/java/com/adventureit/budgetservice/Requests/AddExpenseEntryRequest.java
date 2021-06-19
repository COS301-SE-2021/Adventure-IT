package com.adventureit.budgetservice.Requests;

import java.util.UUID;

public class AddExpenseEntryRequest {
    private UUID id;
    private UUID budgetID;
    double amount;
    String title;
    String description;

    public AddExpenseEntryRequest(){}

    public AddExpenseEntryRequest(UUID id, UUID budgetID,double amount,String title,String description){
        this.id = id;
        this.budgetID = budgetID;
        this.amount = amount;
        this.title = title;
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getBudgetID() {
        return budgetID;
    }

    public void setBudgetID(UUID budgetID) {
        this.budgetID = budgetID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
