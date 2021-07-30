package com.adventureit.budgetservice.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import java.util.UUID;

@Entity

public class BudgetEntry{
    @Id
    private UUID budgetEntryID;
    private UUID entryContainerID;
    double amount;
    String title;
    String description;

    public BudgetEntry() {
    }

    public BudgetEntry(UUID id, UUID entryContainerID, double amount, String title, String description) {
        this.budgetEntryID = id;
        this.entryContainerID = entryContainerID;
        this.amount = amount;
        this.title = title;
        this.description = description;
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
}
