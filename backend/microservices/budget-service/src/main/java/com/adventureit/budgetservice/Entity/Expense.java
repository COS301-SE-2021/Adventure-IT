package com.adventureit.budgetservice.Entity;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.UUID;

@Entity
public class Expense extends BudgetEntry{
    public Expense(UUID id, double amount, String title, String description){
        super(id,amount,title,description);
    }

    public Expense() {

    }
}
