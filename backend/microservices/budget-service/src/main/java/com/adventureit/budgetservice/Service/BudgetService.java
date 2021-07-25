package com.adventureit.budgetservice.Service;

import com.adventureit.adventureservice.Entity.Entry;
import com.adventureit.budgetservice.Requests.*;
import com.adventureit.budgetservice.Responses.*;

import java.util.List;
import java.util.UUID;

public interface BudgetService {

    public CreateBudgetResponse createBudget(UUID id,String name, String description,UUID creatorID, UUID adventureID, double limit) throws Exception;
    public BudgetResponseDTO viewBudget(UUID id) throws Exception;
    public AddIncomeEntryResponse addIncomeEntry(UUID id, UUID entryContainerID, double amount, String title, String description) throws Exception;
    public RemoveEntryResponse removeEntry(UUID id, UUID entryContainerID) throws Exception;
    public AddExpenseEntryResponse addExpenseEntry(UUID id, UUID entryContainerID, double amount, String title, String description) throws Exception;
//    public String insertEntry(UUID id, UUID containerID) throws Exception;
    public EditBudgetResponse editBudget(EditBudgetRequest req) throws Exception;
    public SoftDeleteResponse softDelete(SoftDeleteRequest req) throws Exception;
    public HardDeleteResponse hardDelete(HardDeleteRequest req) throws Exception;
    public List<BudgetResponseDTO> viewTrash() throws Exception;
    public String restoreBudget(UUID id) throws Exception;
    public String calculateBudget(UUID id) throws Exception;
    public void mockPopulate();
    public void mockCreateBudget(String name);
    public void mockPopulateTrash();
    public double calculateExpenses(UUID id);

}
