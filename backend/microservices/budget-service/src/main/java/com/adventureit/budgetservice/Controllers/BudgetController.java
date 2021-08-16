package com.adventureit.budgetservice.Controllers;

import com.adventureit.budgetservice.Entity.Budget;
import com.adventureit.budgetservice.Entity.BudgetEntry;
import com.adventureit.budgetservice.Repository.BudgetRepository;
import com.adventureit.budgetservice.Requests.*;
import com.adventureit.budgetservice.Responses.*;
import com.adventureit.budgetservice.Service.BudgetServiceImplementation;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/budget")
public class BudgetController {
	@Autowired
	BudgetServiceImplementation budgetServiceImplementation;
	
	@Autowired
	BudgetRepository budgetRepository;

	@GetMapping("/test")
	String test(){
		return "Budget Controller is functioning";
	}

	@GetMapping("/populate")
	public String populate(){
		budgetServiceImplementation.mockPopulate();
		return "Mock budgets populated \n";
	}

	@GetMapping("/populateTrash")
	public String populateTrash(){
		budgetServiceImplementation.mockPopulateTrash();
		return "Trash populated \n";
	}

	@GetMapping("/mockCreate/{name}")
	public String createMockBudget(@PathVariable String name){
		budgetServiceImplementation.mockCreateBudget(name);
		return "Budget Successfully created";
	}

	@GetMapping("/viewBudgetsByAdventure/{id}")
	public List<BudgetResponseDTO> viewBudgetsByAdventure(@PathVariable UUID id) throws Exception {
		List<Budget> budgets = budgetRepository.findAllByAdventureID(id);
		List<BudgetResponseDTO> list = new ArrayList<>();
		for (Budget b:budgets) {
			if(!b.isDeleted()){
				list.add(new BudgetResponseDTO(b.getBudgetId(),b.getName(),b.getCreatorID(),b.getAdventureID(),b.isDeleted(),b.getDescription()));
			}
		}
		return list;
	}

	@GetMapping("/viewBudget/{id}")
	public List<ViewBudgetResponse> viewBudget(@PathVariable UUID id) throws Exception {
		return budgetServiceImplementation.viewBudget(id);
	}

	@GetMapping("/softDelete/{id}/{userID}")
	public String softDelete(@PathVariable UUID id, @PathVariable UUID userID) throws Exception {
		SoftDeleteRequest request = new SoftDeleteRequest(id, userID);
		budgetServiceImplementation.softDelete(request);
		return "Budget successfully moved to bin.";
	}

	@GetMapping("/viewTrash/{id}")
	public List<BudgetResponseDTO> viewTrash(@PathVariable UUID id) throws Exception {
		return budgetServiceImplementation.viewTrash(id);
	}

	@GetMapping("/restoreBudget/{id}/{userID}")
	public String restoreBudget(@PathVariable UUID id, @PathVariable UUID userID) throws Exception {
		return budgetServiceImplementation.restoreBudget(id,userID);
	}

	@GetMapping("/hardDelete/{id}/{userID}")
	public String hardDelete(@PathVariable UUID id, @PathVariable UUID userID) throws Exception {
		HardDeleteResponse response = budgetServiceImplementation.hardDelete(id, userID);
		return response.getMessage();
	}

	@PostMapping("/create")
	public String createBudget(@RequestBody CreateBudgetRequest req) throws Exception {
		CreateBudgetResponse response = budgetServiceImplementation.createBudget(req.getName(), req.getDescription() ,req.getCreatorID(),req.getAdventureID());
		return response.getMessage();
	}

	@PostMapping("/addUTOExpense")
	public String addUTOExpense(@RequestBody AddUTOExpenseEntryRequest req) throws Exception {
		AddUTOExpenseEntryResponse response = budgetServiceImplementation.addUTOExpenseEntry(req.getEntryContainerID(),req.getAmount(),req.getTitle(),req.getDescription(),req.getCategory(),req.getPayer(),req.getPayee());
		return response.getMessage();
	}

	@PostMapping("/addUTUExpense")
	public String addUTUExpense(@RequestBody AddUTUExpenseEntryRequest req) throws Exception {
		AddUTUExpenseEntryResponse response = budgetServiceImplementation.addUTUExpenseEntry(req.getEntryContainerID(),req.getAmount(),req.getTitle(),req.getDescription(),req.getCategory(),req.getPayer(),req.getPayee());
		return response.getMessage();
	}

	@PostMapping("/editBudget")
	public String editBudget(@RequestBody EditBudgetRequest req) throws Exception {
		EditBudgetResponse response = budgetServiceImplementation.editBudget(req);
		return response.getMessage();
	}

	@GetMapping("/removeEntry/{id}")
	public String removeEntry(@PathVariable UUID id) throws Exception {
		RemoveEntryResponse response = budgetServiceImplementation.removeEntry(id);
		return response.getMessage();
	}

	@GetMapping("/calculateExpense/{budgetID}/{userName}")
	public double calculateExpense(@PathVariable UUID budgetID, @PathVariable String userName) throws Exception {
		return budgetServiceImplementation.calculateExpensesPerUser(budgetID, userName);
	}

	@GetMapping("/getEntriesPerCategory/{id}")
	public List<Integer> getEntriesPerCategory(@PathVariable UUID id) throws Exception {
		return budgetServiceImplementation.getEntriesPerCategory(id);
	}

	@GetMapping("/getReportList/{id}")
	public List<String> getReportList(@PathVariable UUID id) throws Exception {
		return budgetServiceImplementation.getReportList(id);
	}

	@GetMapping("/generateIndividualReport/{id}/{userName}")
	public List<ReportResponseDTO> generateIndividualReport(@PathVariable UUID id,@PathVariable String userName) throws Exception {
		return budgetServiceImplementation.generateIndividualReport(userName,id);
	}

	@GetMapping("/getBudgetByBudgetId/{budgetId}")
	public BudgetResponseDTO getBudgetByBudgetId(@PathVariable UUID budgetId) {
		return budgetServiceImplementation.getBudgetByBudgetId(budgetId);
	}
}
