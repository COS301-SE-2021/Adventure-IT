package com.adventureit.budgetservice.Entity;

import javax.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity

public class BudgetEntry{
    @Id
    private UUID budgetEntryID;
    private UUID entryContainerID;
    @ElementCollection (fetch= FetchType.EAGER)
    private List<UUID> payers;
    double amount;
    String title;
    String description;
    @Enumerated(EnumType.STRING)
    Category category;

    public BudgetEntry() {
    }

    public BudgetEntry(UUID id, UUID entryContainerID, double amount, String title, String description, Category category) {
        this.budgetEntryID = id;
        this.entryContainerID = entryContainerID;
        this.amount = amount;
        this.title = title;
        this.description = description;
        this.category = category;
    }

    public BudgetEntry(UUID id, UUID entryContainerID, double amount, String title, String description, Category category, List<UUID> payers){
        this.budgetEntryID = id;
        this.entryContainerID = entryContainerID;
        this.amount = amount;
        this.title = title;
        this.description = description;
        this.category = category;
        this.payers = payers;
    }

    public UUID getId() {
        return budgetEntryID;
    }

    public void setId(UUID id) {
        this.budgetEntryID = id;
    }

    public UUID getEntryContainerID() {
        return entryContainerID;
    }

    public void setEntryContainerID(UUID entryContainerID) {
        this.entryContainerID = entryContainerID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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

    public List<UUID> getPayers() {
        return payers;
    }

    public void setPayers(List<UUID> payers) {
        this.payers = payers;
    }

    public Category getCategory() {
        return category;
    }

    public void setBudgetEntryID(UUID budgetEntryID) {
        this.budgetEntryID = budgetEntryID;
    }

    public UUID getBudgetEntryID() {
        return budgetEntryID;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
