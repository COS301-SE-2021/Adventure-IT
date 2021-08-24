package com.adventureit.budgetservice.responses;

import com.adventureit.budgetservice.entity.Budget;

import java.util.ArrayList;

public class ViewTrashResponse {
    boolean success;
    ArrayList<Budget> budgets;

    public ViewTrashResponse(boolean success, ArrayList<Budget> budgets){
        this.budgets = budgets;
        this.success = success;
    }

    public ViewTrashResponse(){}

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setBudgets(ArrayList<Budget> budgets) {
        this.budgets = budgets;
    }

    public ArrayList<Budget> getBudgets() {
        return budgets;
    }
}
