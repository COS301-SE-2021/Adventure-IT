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

    public BudgetEntry(UUID id,double amount,String title,String description){
        this.id = id;
        this.amount = amount;
        this.title = title;
        this.description = description;
//        this.attachments = attachments;
    }

    public BudgetEntry() {

    }

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
}
