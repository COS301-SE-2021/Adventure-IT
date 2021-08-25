package com.adventureit.budgetservice.requests;

import com.adventureit.budgetservice.entity.Category;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class EditBudgetRequest {
    UUID id;
    UUID budgetID;
    double amount;
    String title;
    String description;
    Category category;
    String payer;
    String payee;

    public EditBudgetRequest(@JsonProperty("id") UUID id, @JsonProperty("budgetID") UUID budgetID, @JsonProperty("amount") double amount, @JsonProperty("title") String title, @JsonProperty("description") String description, @JsonProperty("payer") String payer, @JsonProperty("payee") String payee){
        this.id = id;
        this.budgetID = budgetID;
        this.amount = amount;
        this.title = title;
        this.description = description;
        this.payer = payer;
        this.payee = payee;
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

    public String getPayee() {
        return payee;
    }

    public String getPayer() {
        return payer;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }
}
