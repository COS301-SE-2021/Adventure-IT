package com.adventureit.budgetservice.Service;

import com.adventureit.budgetservice.Entity.Category;
import com.adventureit.budgetservice.Requests.*;
import com.adventureit.budgetservice.Responses.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.UUID;

public interface BudgetService {

    CreateBudgetResponse createBudget(String name, String description,UUID creatorID, UUID adventureID) throws Exception;
    List<ViewBudgetResponse> viewBudget(UUID id) throws Exception;
    AddUTUExpenseEntryResponse addUTUExpenseEntry(UUID entryContainerID, double amount, String title, String description, Category category, String payer, String payeeID) throws Exception;
    RemoveEntryResponse removeEntry(UUID id) throws Exception;
    AddUTOExpenseEntryResponse addUTOExpenseEntry(UUID entryContainerID, double amount, String title, String description,Category category,String payer, String payee) throws Exception;
    EditBudgetResponse editBudget(EditBudgetRequest req) throws Exception;
    SoftDeleteResponse softDelete(SoftDeleteRequest req) throws Exception;
    HardDeleteResponse hardDelete(UUID id, UUID userID) throws Exception;
    List<BudgetResponseDTO> viewTrash(UUID id) throws Exception;
    String restoreBudget(UUID id,UUID userID) throws Exception;
    List<ReportResponseDTO> generateIndividualReport(String userName, UUID id) throws JSONException;
    List<String> getReportList(UUID id);
    void mockPopulate();
    void mockCreateBudget(String name);
    void mockPopulateTrash();
    double calculateExpensesPerUser(UUID budgetID, String userName ) throws Exception;
    List<Integer> getEntriesPerCategory(UUID adventureID) throws Exception;

}
