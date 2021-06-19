package com.adventureit.budgetservice.Entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Budget {
    @Id
    private UUID id;
    private UUID adventureID;
    @OneToMany (fetch=FetchType.EAGER)
    List<BudgetEntry> transactions;
    boolean deleted;

    /**
     * Default Constructor
     */
    public Budget(){}

    /**
     * Budget model Constructor which takes in the following parameters:
     * @param id Budget id
     * @param adventureID id of Adventure the Budget belongs to
     * @param transactions List of all Entries
     */
    public Budget(UUID id,UUID adventureID,ArrayList<BudgetEntry> transactions){
        this.id = id;
        this.adventureID = adventureID;
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

    public void setAdventureID(UUID adventureID) {
        this.adventureID = adventureID;
    }

    public UUID getAdventureID() {
        return adventureID;
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
