package com.adventureit.budgetservice.responses;

import com.adventureit.budgetservice.entity.Budget;

import java.util.ArrayList;
import java.util.List;

public class ViewTrashResponse {
    boolean success;
    List<Budget> budgets;

    public ViewTrashResponse(boolean success, ArrayList<Budget> budgets){
        this.budgets = budgets;
        this.success = success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setBudgets(ArrayList<Budget> budgets) {
        this.budgets = budgets;
    }

    public List<Budget> getBudgets() {
        return budgets;
    }
}
