package com.adventureit.budgetservice.Controllers;

import com.adventureit.budgetservice.Entity.Budget;
import com.adventureit.budgetservice.Requests.*;
import com.adventureit.budgetservice.Responses.*;
import com.adventureit.budgetservice.Service.BudgetServiceImplementation;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/budget")
public class BudgetController {
	@Autowired
	BudgetServiceImplementation budgetServiceImplementation;

	@GetMapping("/test")
	String test(){
		return "Budget Controller is functioning";
	}

	@PostMapping("/create")
	public String createBudget(@RequestBody CreateBudgetRequest req) throws Exception {
		CreateBudgetResponse response = budgetServiceImplementation.createBudget(req);
		return response.getMessage();
	}

	@PostMapping("/addIncome")
	String addIncomeEntry(@RequestBody AddIncomeEntryRequest req) throws Exception {
		AddIncomeEntryResponse response = budgetServiceImplementation.addIncomeEntry(req);
		return response.getMessage();
	}

	@PostMapping("/addExpense")
	String addExpenseEntry(@RequestBody AddExpenseEntryRequest req) throws Exception {
		AddExpenseEntryResponse response = budgetServiceImplementation.addExpenseEntry(req);
		return response.getMessage();
	}

	@PostMapping("/removeEntry")
	String removeEntry(@RequestBody RemoveEntryRequest req) throws Exception {
		RemoveEntryResponse response = budgetServiceImplementation.removeEntry(req);
		return response.getMessage();
	}

	@PostMapping("/view")
	Budget viewBudget(@RequestBody ViewBudgetRequest req) throws Exception {
		ViewBudgetResponse response = budgetServiceImplementation.viewBudget(req);
		return response.getBudget();
	}

	@PostMapping("/edit")
	String editBudget(@RequestBody EditBudgetRequest req) throws Exception {
		EditBudgetResponse response = budgetServiceImplementation.editBudget(req);
		return response.getMessage();
	}

	@PostMapping("/softDelete")
	String softDelete(@RequestBody SoftDeleteRequest req) throws Exception {
		SoftDeleteResponse response = budgetServiceImplementation.softDelete(req);
		return response.getMessage();
	}

	@PostMapping("/hardDelete")
	String hardDelete(@RequestBody HardDeleteRequest req) throws Exception {
		HardDeleteResponse response = budgetServiceImplementation.hardDelete(req);
		return response.getMessage();
	}

	@GetMapping("/viewTrash")
	List<Budget> viewTrash() throws Exception {
		ViewTrashResponse response = budgetServiceImplementation.viewTrash();
		return response.getBudgets();
	}
}
