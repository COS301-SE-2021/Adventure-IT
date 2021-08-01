package com.adventureit.budgetservice.Entity;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class UTUExpense extends BudgetEntry {
    UUID payeeID;

    /**
     * Income model Constructor which takes in the following parameters:
     * @param id Expense id
     * @param amount amount used in specific entry
     * @param title title of entry
     * @param description short description of entry
     */
    public UTUExpense(UUID id, UUID entryContainerID, double amount, String title, String description, Category category, List<UUID> payers, UUID payeeID){
        super(id,entryContainerID,amount,title,description, category, payers);
        this.payeeID = payeeID;
    }

    /**
     * Default Constructor
     */
    public UTUExpense() {}

    public UUID getPayeeID() {
        return payeeID;
    }

    public void setPayeeID(UUID payeeID) {
        this.payeeID = payeeID;
    }
}
