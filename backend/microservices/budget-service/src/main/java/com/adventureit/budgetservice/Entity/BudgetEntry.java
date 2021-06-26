package com.adventureit.budgetservice.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.UUID;

@Entity
public class BudgetEntry {
    @Id
    private UUID id;
    double amount;
    String title;
    String description;
//    ArrayList<Media> attachments;

    /**
     * BudgetEntry model Constructor which takes in the following parameters:
     * @param id BudgetEntry id
     * @param amount amount used in specific entry
     * @param title title of entry
     * @param description short description of entry
     */
    public BudgetEntry(UUID id,double amount,String title,String description){
        this.id = id;
        this.amount = amount;
        this.title = title;
        this.description = description;
//        this.attachments = attachments;
    }

    /**
     * Default Constructors
     */
    public BudgetEntry() {

    }

    /**
     * Getters and Setters
     */
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }
}