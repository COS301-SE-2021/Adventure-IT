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

    public Budget(){}

    public Budget(UUID id,UUID adventureID,ArrayList<BudgetEntry> transactions){
        this.id = id;
        this.adventureID = adventureID;
        this.transactions = transactions;
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
}
