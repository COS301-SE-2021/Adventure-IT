package com.adventureit.budgetservice.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.UUID;

@Entity
public class Budget {
    @Id
    private UUID id;
    private UUID adventureID;
    @OneToMany
    ArrayList<BudgetEntry> transactions;
    boolean deleted;

    public Budget(){}

    public Budget(UUID id,UUID adventureID,ArrayList<BudgetEntry> transactions){
        this.id = id;
        this.adventureID = adventureID;
        this.transactions = transactions;
        deleted = false;
    }

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

    public ArrayList<BudgetEntry> getTransactions() {
        return transactions;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public boolean CheckIfEntryExists(ArrayList<BudgetEntry> trans, UUID id) {
        boolean result = false;
        for (BudgetEntry b : trans) {
            if (b.getId().equals(id)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public BudgetEntry getEntry(ArrayList<BudgetEntry> trans, UUID id) {
        BudgetEntry result = null;
        for (BudgetEntry b : trans) {
            if (b.getId().equals(id)) {
                result = b;
                break;
            }
        }
        return result;
    }
}
