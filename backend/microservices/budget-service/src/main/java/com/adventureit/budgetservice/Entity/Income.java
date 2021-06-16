package com.adventureit.budgetservice.Entity;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.UUID;

@Entity
public class Income extends BudgetEntry {
    public Income(UUID id, double amount, String title, String description){
        super(id,amount,title,description);
    }

    public Income() {}
}
