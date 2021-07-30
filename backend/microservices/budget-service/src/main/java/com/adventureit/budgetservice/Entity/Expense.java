package com.adventureit.budgetservice.Entity;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.UUID;

@Entity
public class Expense extends BudgetEntry{
    /**
     * Expense model Constructor which takes in the following parameters:
     * @param id Expense id
     * @param amount amount used in specific entry
     * @param title title of entry
     * @param description short description of entry
     */
    public Expense(UUID id, UUID entryContainerID, double amount, String title, String description){
        super(id,entryContainerID,amount,title,description);
    }

    /**
     * Default Constructor
     */
    public Expense() {

    }
}
