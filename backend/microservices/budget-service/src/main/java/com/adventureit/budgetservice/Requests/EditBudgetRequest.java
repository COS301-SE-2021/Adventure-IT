package com.adventureit.budgetservice.Requests;

import com.adventureit.budgetservice.Entity.Category;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

public class EditBudgetRequest {
    UUID id;
    UUID budgetID;
    double amount;
    String title;
    String description;
    Category category;
    List<String> payers;
    String payee;

    public EditBudgetRequest(){}

    public EditBudgetRequest(@JsonProperty("id") UUID id, @JsonProperty("budgetID") UUID budgetID, @JsonProperty("amount") double amount, @JsonProperty("title") String title, @JsonProperty("description") String description, @JsonProperty("payers") List<String> payers, @JsonProperty("payee") String payee){
        this.id = id;
        this.budgetID = budgetID;
        this.amount = amount;
        this.title = title;
        this.description = description;
        this.payers = payers;
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

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public void setPayers(List<String> payers) {
        this.payers = payers;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<String> getPayers() {
        return payers;
    }

    public Category getCategory() {
        return category;
    }
}
