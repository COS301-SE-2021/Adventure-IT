package com.adventureit.shareddtos.budget.responses;

import com.adventureit.shareddtos.budget.BudgetDTO;


import java.util.List;

public class ViewTrashResponse {
    boolean success;
    List<BudgetDTO> budgets;

    public ViewTrashResponse(boolean success, List<BudgetDTO> budgets){
        this.budgets = budgets;
        this.success = success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setBudgets(List<BudgetDTO> budgets) {
        this.budgets = budgets;
    }

    public List<BudgetDTO> getBudgets() {
        return budgets;
    }
}
