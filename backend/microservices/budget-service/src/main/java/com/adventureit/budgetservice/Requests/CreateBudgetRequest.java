package com.adventureit.budgetservice.Requests;

import com.adventureit.budgetservice.Entity.BudgetEntry;


import java.util.ArrayList;
import java.util.UUID;

public class CreateBudgetRequest {
    private UUID id;
    private UUID adventureID;
    ArrayList<BudgetEntry> transactions;

    public CreateBudgetRequest() {
    }

    public CreateBudgetRequest(UUID id, UUID adventureID, ArrayList<BudgetEntry> transactions) {
        this.id = id;
        this.adventureID = adventureID;
        this.transactions = transactions;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getAdventureID() {
        return adventureID;
    }

    public void setAdventureID(UUID adventureID) {
        this.adventureID = adventureID;
    }

    public ArrayList<BudgetEntry> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<BudgetEntry> transactions) {
        this.transactions = transactions;
    }
}
