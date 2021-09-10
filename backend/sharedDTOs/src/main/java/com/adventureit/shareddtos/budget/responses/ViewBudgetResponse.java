package com.adventureit.shareddtos.budget.responses;

import com.adventureit.shareddtos.budget.Category;

import java.util.UUID;

public class ViewBudgetResponse {
    private UUID budgetEntryID;
    private UUID entryContainerID;
    private String payer;
    double amount;
    String title;
    String description;
    Category category;
    String payee;

    public ViewBudgetResponse(UUID id, UUID entryContainerID, String payer, double amount, String title, String description, Category category, String payee) {
        this.budgetEntryID = id;
        this.entryContainerID = entryContainerID;
        this.payer = payer;
        this.amount = amount;
        this.title = title;
        this.description = description;
        this.category = category;
        this.payee = payee;
    }

    public String getPayer() {
        return payer;
    }

    public UUID getBudgetEntryID() {
        return budgetEntryID;
    }

    public String getTitle() {
        return title;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getPayee() {
        return payee;
    }

    public Category getCategory() {
        return category;
    }

    public void setEntryContainerID(UUID entryContainerID) {
        this.entryContainerID = entryContainerID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public void setBudgetEntryID(UUID budgetEntryID) {
        this.budgetEntryID = budgetEntryID;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
