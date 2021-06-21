package com.adventureit.budgetservice.Entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Budget {
    @Id
    private UUID id;
    String name;
    @ManyToMany (fetch=FetchType.EAGER)
    List<BudgetEntry> transactions;
    boolean deleted;
    UUID adventureID;

    /**
     * Default Constructor
     */
    public Budget(){}

    /**
     * Budget model Constructor which takes in the following parameters:
     * @param id Budget id
     * @param name Name of the budget
     * @param transactions List of all Entries
     */
    public Budget(UUID id,String name,ArrayList<BudgetEntry> transactions){
        this.id = id;
        this.name = name;
        this.transactions = transactions;
        deleted = false;
    }

    /**
     * Getters and Setters
     */
    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTransactions(ArrayList<BudgetEntry> transactions) {
        this.transactions = transactions;
    }

    public List<BudgetEntry> getTransactions() {
        return transactions;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public UUID getAdventureID() {
        return adventureID;
    }

    public void setAdventureID(UUID adventureID) {
        this.adventureID = adventureID;
    }

    /**
     * Helper functions
     */
    public boolean CheckIfEntryExists(List<BudgetEntry> trans, UUID id) {
        boolean result = false;
        for (BudgetEntry b : trans) {
            if (b.getId().equals(id)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public BudgetEntry getEntry(List<BudgetEntry> trans, UUID id) {
        BudgetEntry result = null;
        for (BudgetEntry b : trans) {
            if (b.getId().equals(id)) {
                result = b;
                break;
            }
        }
        return result;
    }

    public int getIndex(List<BudgetEntry> trans, UUID id) {
        int result = 0;
        for (BudgetEntry b : trans) {
            if (b.getId().equals(id)) {
                break;
            }
            result++;
        }
        return result;
    }

}
