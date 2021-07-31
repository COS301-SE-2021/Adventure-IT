package com.adventureit.budgetservice.Requests;

import com.adventureit.budgetservice.Entity.Category;

import java.util.List;
import java.util.UUID;

public class EditBudgetRequest {
    UUID id;
    UUID budgetID;
    double amount;
    String title;
    String description;
    Category category;
    List<UUID> payers;
    String payee;
    UUID payeeID;

    public EditBudgetRequest(){}

    public EditBudgetRequest(UUID id, UUID budgetID, double amount,String title,String description, List<UUID> payers, String payee){
        this.id = id;
        this.budgetID = budgetID;
        this.amount = amount;
        this.title = title;
        this.description = description;
        this.payers = payers;
        this.payee = payee;
        this.payeeID = null;
    }

    public EditBudgetRequest(UUID id, UUID budgetID, double amount,String title,String description, List<UUID> payers, UUID payeeID){
        this.id = id;
        this.budgetID = budgetID;
        this.amount = amount;
        this.title = title;
        this.description = description;
        this.payers = payers;
        this.payee = null;
        this.payeeID = payeeID;
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

    public UUID getPayeeID() {
        return payeeID;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayeeID(UUID payeeID) {
        this.payeeID = payeeID;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public void setPayers(List<UUID> payers) {
        this.payers = payers;
    }


    public void setCategory(Category category) {
        this.category = category;
    }

    public List<UUID> getPayers() {
        return payers;
    }

    public Category getCategory() {
        return category;
    }
}
