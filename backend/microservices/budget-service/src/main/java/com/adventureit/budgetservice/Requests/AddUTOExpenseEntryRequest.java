package com.adventureit.budgetservice.Requests;

import com.adventureit.budgetservice.Entity.Category;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

public class AddUTOExpenseEntryRequest {
    private UUID budgetEntryID;
    private UUID entryContainerID;
    private List<String> payers;
    double amount;
    String title;
    String description;
    Category category;
    String payee;

    public AddUTOExpenseEntryRequest(){}

    public AddUTOExpenseEntryRequest(@JsonProperty("budgetEntryID") UUID budgetEntryID, @JsonProperty("entryContainerID") UUID entryContainerID, @JsonProperty("payers") List<String> payers, @JsonProperty("amount") double amount, @JsonProperty("title") String title, @JsonProperty("description") String description, @JsonProperty("category") Category category, @JsonProperty("payee") String payee) {
        this.budgetEntryID = budgetEntryID;
        this.entryContainerID = entryContainerID;
        this.payers = payers;
        this.amount = amount;
        this.title = title;
        this.description = description;
        this.category = category;
        this.payee = payee;
    }

    public UUID getEntryContainerID() {
        return entryContainerID;
    }

    public UUID getBudgetEntryID() {
        return budgetEntryID;
    }

    public void setPayers(List<String> payers) {
        this.payers = payers;
    }

    public List<String> getPayers() {
        return payers;
    }

    public Category getCategory() {
        return category;
    }

    public String getPayee() {
        return payee;
    }

    public void setBudgetEntryID(UUID budgetEntryID) {
        this.budgetEntryID = budgetEntryID;
    }

    public void setEntryContainerID(UUID entryContainerID) {
        this.entryContainerID = entryContainerID;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setPayee(String payee) {
        this.payee = payee;
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
