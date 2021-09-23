package com.adventureit.budgetservice.entity;

import com.adventureit.shareddtos.budget.Category;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The Budget Entry class
 * This class defines the attributes of the Budget Entry entity to set up the table in the database
 */
@Entity
public class BudgetEntry{
    @Id
    private UUID budgetEntryID;
    private UUID entryContainerID;
    private String payer;
    double amount;
    String title;
    String description;
    @Enumerated(EnumType.STRING)
    Category category;
    LocalDateTime timestamp;

    /**
     * Default constructor
     */
    public BudgetEntry() {
    }

    /**
     * Budget model Constructor which takes in the following parameters:
     * @param id - The UUID of the budget entry
     * @param entryContainerID - The UUID of the budget the entry belongs to
     * @param amount - The amount of the transaction
     * @param title - The title of the budget entry
     * @param description - The description of the budget entry
     * @param category - The category of the budget entry
     * @param payer - The payer of the transaction
     */
    public BudgetEntry(UUID id, UUID entryContainerID, double amount, String title, String description, Category category, String payer){
        this.budgetEntryID = id;
        this.entryContainerID = entryContainerID;
        this.amount = amount;
        this.title = title;
        this.description = description;
        this.category = category;
        this.payer = payer;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Budget model Constructor which takes in the following parameters:
     * @param entryContainerID - The UUID of the budget the entry belongs to
     * @param amount - The amount of the transaction
     * @param title - The title of the budget entry
     * @param description - The description of the budget entry
     * @param category - The category of the budget entry
     * @param payer - The payer of the transaction
     */
    public BudgetEntry(UUID entryContainerID, double amount, String title, String description, Category category, String payer){
        this.budgetEntryID = UUID.randomUUID();
        this.entryContainerID = entryContainerID;
        this.amount = amount;
        this.title = title;
        this.description = description;
        this.category = category;
        this.payer = payer;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Getters and Setters
     */
    public UUID getId() {
        return budgetEntryID;
    }

    public void setId(UUID id) {
        this.budgetEntryID = id;
    }

    public UUID getEntryContainerID() {
        return entryContainerID;
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

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
