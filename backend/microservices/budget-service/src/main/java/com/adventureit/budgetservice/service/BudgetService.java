package com.adventureit.budgetservice.service;

import com.adventureit.shareddtos.budget.Category;
import com.adventureit.shareddtos.budget.requests.EditBudgetRequest;
import com.adventureit.shareddtos.budget.requests.SoftDeleteRequest;
import com.adventureit.shareddtos.budget.responses.*;
import org.json.JSONException;

import java.util.List;
import java.util.UUID;

public interface BudgetService {

    BudgetResponseDTO getBudgetByBudgetContainerId(UUID budgetId);
    CreateBudgetResponse createBudget(String name, String description, UUID creatorID, UUID adventureID);
    List<ViewBudgetResponse> viewBudget(UUID id);
    AddUTUExpenseEntryResponse addUTUExpenseEntry(UUID entryContainerID, double amount, String title, String description, Category category, String payer, String payeeID);
    RemoveEntryResponse removeEntry(UUID id);
    AddUTOExpenseEntryResponse addUTOExpenseEntry(UUID entryContainerID, double amount, String title, String description,Category category,String payer, String payee);
    EditBudgetResponse editBudget(EditBudgetRequest req);
    SoftDeleteResponse softDelete(SoftDeleteRequest req);
    HardDeleteResponse hardDelete(UUID id, UUID userID);
    List<BudgetResponseDTO> viewTrash(UUID id);
    String restoreBudget(UUID id,UUID userID);
    List<ReportResponseDTO> generateIndividualReport(String userName, UUID id) throws JSONException;
    List<String> getReportList(UUID id);
    void mockPopulate();
    void mockPopulateTrash();
    double calculateExpensesPerUser(UUID budgetID, String userName );
    List<Integer> getEntriesPerCategory(UUID adventureID);
    BudgetResponseDTO getBudgetByBudgetEntryId(UUID budgetId);
}
