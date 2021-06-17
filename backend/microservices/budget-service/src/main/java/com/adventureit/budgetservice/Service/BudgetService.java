package com.adventureit.budgetservice.Service;

import com.adventureit.budgetservice.Requests.CreateBudgetRequest;
import com.adventureit.budgetservice.Responses.CreateBudgetResponse;

public interface BudgetService {

    public CreateBudgetResponse createBudget(CreateBudgetRequest req) throws Exception;

}
