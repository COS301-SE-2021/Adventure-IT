package com.adventureit.budgetservice.Service;

import com.adventureit.budgetservice.Requests.*;
import com.adventureit.budgetservice.Responses.*;

public interface BudgetService {

    public CreateBudgetResponse createBudget(CreateBudgetRequest req) throws Exception;
    public ViewBudgetResponse viewBudget(ViewBudgetRequest req) throws Exception;
    public AddIncomeEntryResponse addIncomeEntry(AddIncomeEntryRequest req) throws Exception;
    public RemoveIncomeEntryResponse removeIncomeEntry(RemoveIncomeEntryRequest req) throws Exception;
    public AddExpenseEntryResponse addExpenseEntry(AddExpenseEntryRequest req) throws Exception;

}
