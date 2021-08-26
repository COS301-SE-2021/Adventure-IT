package com.adventureit.budgetservice.requests;

import com.adventureit.budgetservice.entity.Category;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class AddUTUExpenseEntryRequest {
    private final UUID entryContainerID;
    private final String payer;
    double amount;
    String title;
    String description;
    Category category;
    String payee;

    public AddUTUExpenseEntryRequest(@JsonProperty("entryContainerID") UUID entryContainerID, @JsonProperty("payer") String payer, @JsonProperty("amount") double amount, @JsonProperty("title") String title, @JsonProperty("description") String description, @JsonProperty("category") String category, @JsonProperty("payee") String payee) {
        this.entryContainerID = entryContainerID;
        this.payer = payer;
        this.amount = amount;
        this.title = title;
        this.description = description;
        this.category = Category.valueOf(category);
        this.payee = payee;
    }

    public Category getCategory() {
        return category;
    }

    public String getPayee() {
        return payee;
    }

    public UUID getEntryContainerID() {
        return entryContainerID;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    public String getPayer() {
        return payer;
    }

}
