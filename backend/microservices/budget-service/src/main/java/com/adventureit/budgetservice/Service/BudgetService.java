package com.adventureit.budgetservice.Service;

import com.adventureit.budgetservice.Requests.*;
import com.adventureit.budgetservice.Responses.*;

public interface BudgetService {

    public CreateBudgetResponse createBudget(CreateBudgetRequest req) throws Exception;
    public ViewBudgetResponse viewBudget(ViewBudgetRequest req) throws Exception;
    public AddIncomeEntryResponse addIncomeEntry(AddIncomeEntryRequest req) throws Exception;
    public RemoveIncomeEntryResponse removeIncomeEntry(RemoveIncomeEntryRequest req) throws Exception;
    public AddExpenseEntryResponse addExpenseEntry(AddExpenseEntryRequest req) throws Exception;
    public RemoveExpenseEntryResponse removeExpenseEntry(RemoveExpenseEntryRequest req) throws Exception;
    public EditBudgetResponse editBudget(EditBudgetRequest req) throws Exception;
    public SoftDeleteResponse softDelete(SoftDeleteRequest req) throws Exception;
    public HardDeleteResponse hardDelete(HardDeleteRequest req) throws Exception;
    public ViewTrashResponse viewTrash() throws Exception;

}
