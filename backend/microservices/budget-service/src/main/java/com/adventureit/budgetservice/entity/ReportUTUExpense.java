package com.adventureit.budgetservice.entity;

import com.adventureit.shareddtos.budget.Category;

import javax.persistence.Entity;
import java.util.UUID;

@Entity
public class ReportUTUExpense extends ReportBudgetEntity{
    String payee;

    /**
     * Income model Constructor which takes in the following parameters:
     * @param id Expense id
     * @param amount amount used in specific entry
     * @param title title of entry
     * @param description short description of entry
     */
    public ReportUTUExpense(UUID id, UUID entryContainerID, double amount, String title, String description, Category category, String payer, String payee){
        super(id,entryContainerID,amount,title,description, category, payer);
        this.payee = payee;
    }

    public ReportUTUExpense(UUID entryContainerID, double amount, String title, String description, Category category, String payer, String payee){
        super(entryContainerID,amount,title,description, category, payer);
        this.payee = payee;
    }

    /**
     * Default Constructor
     */
    public ReportUTUExpense() {}

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }
}
