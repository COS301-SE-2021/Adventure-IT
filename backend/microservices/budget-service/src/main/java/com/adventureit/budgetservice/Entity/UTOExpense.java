package com.adventureit.budgetservice.Entity;

import javax.persistence.Entity;
import java.util.UUID;

@Entity
public class UTOExpense extends BudgetEntry{
    private String payee;

    /**
     * Expense model Constructor which takes in the following parameters:
     * @param id Expense id
     * @param amount amount used in specific entry
     * @param title title of entry
     * @param description short description of entry
     */
    public UTOExpense(UUID id, UUID entryContainerID, double amount, String title, String description, Category category, String payer, String payee){
        super(id,entryContainerID,amount,title,description, category, payer);
        this.payee = payee;
    }

    public UTOExpense(UUID entryContainerID, double amount, String title, String description, Category category, String payer, String payee){
        super(entryContainerID,amount,title,description, category, payer);
        this.payee = payee;
    }

    /**
     * Default Constructor
     */
    public UTOExpense() {

    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }
}
