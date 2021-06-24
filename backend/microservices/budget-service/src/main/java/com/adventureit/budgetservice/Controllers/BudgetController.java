package com.adventureit.budgetservice.Controllers;

import com.adventureit.budgetservice.Entity.Budget;
import com.adventureit.budgetservice.Entity.BudgetEntry;
import com.adventureit.budgetservice.Requests.*;
import com.adventureit.budgetservice.Responses.*;
import com.adventureit.budgetservice.Service.BudgetServiceImplementation;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/budget")
public class BudgetController {
	@Autowired
	BudgetServiceImplementation budgetServiceImplementation;
	
//	@Autowired
//	BudgetRepository budgetRepository;


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

//	@GetMapping("/viewBudgetsByAdventure/{id}")
//	public List<Budget> viewBudgetsByAdventure(@PathVariable UUID id) throws Exception {
//		return budgetRepository.findAllByAdventureID(id);
//	}

	@GetMapping("/viewBudget/{id}")
	public Budget viewBudget(@PathVariable UUID id) throws Exception {
		ViewBudgetRequest request = new ViewBudgetRequest(id);
		ViewBudgetResponse response = budgetServiceImplementation.viewBudget(request);
		return response.getBudget();
	}

	@GetMapping("/softDelete/{id}")
	public String softDelete(@PathVariable UUID id) throws Exception {
		SoftDeleteRequest request = new SoftDeleteRequest(id);
		budgetServiceImplementation.softDelete(request);
		return "Budget successfully moved to bin.";
	}

	@GetMapping("/viewTrash/{id}")
	public List<Budget> viewTrash(@PathVariable UUID id) throws Exception {
		ViewTrashResponse response = budgetServiceImplementation.viewTrash(id);
		return response.getBudgets();
	}

	@GetMapping("/restoreBudget/{id}")
	public String restoreBudget(@PathVariable UUID id) throws Exception {
		return budgetServiceImplementation.restoreBudget(id);
	}

//	@PostMapping("/create")
//	public String createBudget(@RequestBody CreateBudgetRequest req) throws Exception {
//		CreateBudgetResponse response = budgetServiceImplementation.createBudget(req);
//		return response.getMessage();
//	}
//
//	@PostMapping("/addIncome")
//	String addIncomeEntry(@RequestBody AddIncomeEntryRequest req) throws Exception {
//		AddIncomeEntryResponse response = budgetServiceImplementation.addIncomeEntry(req);
//		return response.getMessage();
//	}
//
//	@PostMapping("/addExpense")
//	String addExpenseEntry(@RequestBody AddExpenseEntryRequest req) throws Exception {
//		AddExpenseEntryResponse response = budgetServiceImplementation.addExpenseEntry(req);
//		return response.getMessage();
//	}
//
//	@PostMapping("/removeEntry")
//	String removeEntry(@RequestBody RemoveEntryRequest req) throws Exception {
//		RemoveEntryResponse response = budgetServiceImplementation.removeEntry(req);
//		return response.getMessage();
//	}
//
//	@PostMapping("/view")
//	Budget viewBudget(@RequestBody ViewBudgetRequest req) throws Exception {
//		ViewBudgetResponse response = budgetServiceImplementation.viewBudget(req);
//		return response.getBudget();
//	}
//
//	@PostMapping("/edit")
//	String editBudget(@RequestBody EditBudgetRequest req) throws Exception {
//		EditBudgetResponse response = budgetServiceImplementation.editBudget(req);
//		return response.getMessage();
//	}
//
//	@PostMapping("/softDelete")
//	String softDelete(@RequestBody SoftDeleteRequest req) throws Exception {
//		SoftDeleteResponse response = budgetServiceImplementation.softDelete(req);
//		return response.getMessage();
//	}
//
//	@PostMapping("/hardDelete")
//	String hardDelete(@RequestBody HardDeleteRequest req) throws Exception {
//		HardDeleteResponse response = budgetServiceImplementation.hardDelete(req);
//		return response.getMessage();
//	}
//
//	@GetMapping("/viewTrash")
//	List<Budget> viewTrash() throws Exception {
//		ViewTrashResponse response = budgetServiceImplementation.viewTrash();
//		return response.getBudgets();
//	}
}
