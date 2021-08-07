package com.adventureit.budgetservice.Responses;

import com.adventureit.budgetservice.Entity.Category;

import java.util.List;
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

    public ViewBudgetResponse(){}

    public ViewBudgetResponse(UUID id, UUID entryContainerID, double amount, String title, String description, Category category, String payee, String payer) {
        this.budgetEntryID = id;
        this.entryContainerID = entryContainerID;
        this.amount = amount;
        this.title = title;
        this.description = description;
        this.category = category;
        this.payee = payee;
        this.payer = payer;
    }

    public UUID getEntryContainerID() {
        return entryContainerID;
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

    public void setPayers(List<UUID> payers) {
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
