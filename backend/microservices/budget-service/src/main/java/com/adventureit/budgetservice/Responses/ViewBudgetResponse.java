package com.adventureit.budgetservice.Responses;

import com.adventureit.budgetservice.Entity.Budget;
import com.adventureit.budgetservice.Entity.BudgetEntry;

import java.util.ArrayList;

public class ViewBudgetResponse {
    Budget budget;
    boolean success;

    public ViewBudgetResponse(){}

    public ViewBudgetResponse(Budget budget, boolean success){
        this.budget = budget;
        this.success = success;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
