package com.adventureit.budgetservice.Entity;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.UUID;

@Entity
public class Income extends BudgetEntry {
    /**
     * Income model Constructor which takes in the following parameters:
     * @param id Expense id
     * @param amount amount used in specific entry
     * @param title title of entry
     * @param description short description of entry
     */
    public Income(UUID id, UUID entryContainerID, double amount, String title, String description){
        super(id,entryContainerID,amount,title,description);
    }

    /**
     * Default Constructor
     */
    public Income() {}
}
