package com.adventureit.budgetservice.Requests;

import com.adventureit.budgetservice.Entity.BudgetEntry;


import java.util.ArrayList;
import java.util.UUID;

public class CreateBudgetRequest {
    private UUID id;
    private String name;
    ArrayList<BudgetEntry> transactions;

    public CreateBudgetRequest() {
    }

    public CreateBudgetRequest(UUID id, String name, ArrayList<BudgetEntry> transactions) {
        this.id = id;
        this.name = name;
        this.transactions = transactions;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<BudgetEntry> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<BudgetEntry> transactions) {
        this.transactions = transactions;
    }
}
