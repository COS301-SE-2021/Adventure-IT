package com.adventureit.budgetservice.Service;

import com.adventureit.adventureservice.Entity.Entry;
import com.adventureit.budgetservice.Requests.*;
import com.adventureit.budgetservice.Responses.*;

import java.util.List;
import java.util.UUID;

public interface BudgetService {

    CreateBudgetResponse createBudget(UUID id,String name, String description,UUID creatorID, UUID adventureID, double limit) throws Exception;
    BudgetResponseDTO viewBudget(UUID id) throws Exception;
    AddIncomeEntryResponse addIncomeEntry(UUID id, UUID entryContainerID, double amount, String title, String description) throws Exception;
    RemoveEntryResponse removeEntry(UUID id, UUID entryContainerID) throws Exception;
    AddExpenseEntryResponse addExpenseEntry(UUID id, UUID entryContainerID, double amount, String title, String description) throws Exception;
//    public String insertEntry(UUID id, UUID containerID) throws Exception;
    EditBudgetResponse editBudget(EditBudgetRequest req) throws Exception;
    SoftDeleteResponse softDelete(SoftDeleteRequest req) throws Exception;
    HardDeleteResponse hardDelete(HardDeleteRequest req) throws Exception;
    List<BudgetResponseDTO> viewTrash() throws Exception;
    String restoreBudget(UUID id) throws Exception;
    String calculateBudget(UUID id) throws Exception;
    void mockPopulate();
    void mockCreateBudget(String name);
    void mockPopulateTrash();
    double calculateExpenses(UUID id);

}
