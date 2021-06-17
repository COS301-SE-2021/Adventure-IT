package com.adventureit.budgetservice.Service;

import com.adventureit.budgetservice.Requests.CreateBudgetRequest;
import com.adventureit.budgetservice.Requests.ViewBudgetRequest;
import com.adventureit.budgetservice.Responses.CreateBudgetResponse;
import com.adventureit.budgetservice.Responses.ViewBudgetResponse;

public interface BudgetService {

    public CreateBudgetResponse createBudget(CreateBudgetRequest req) throws Exception;

    public ViewBudgetResponse viewBudget(ViewBudgetRequest req) throws Exception;

}
