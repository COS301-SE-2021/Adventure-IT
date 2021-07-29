package com.adventureit.budgetservice.Controllers;

import com.adventureit.budgetservice.Entity.Budget;
import com.adventureit.budgetservice.Entity.BudgetEntry;
import com.adventureit.budgetservice.Repository.BudgetRepository;
import com.adventureit.budgetservice.Requests.*;
import com.adventureit.budgetservice.Responses.*;
import com.adventureit.budgetservice.Service.BudgetServiceImplementation;
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
				list.add(new BudgetResponseDTO(b.getId(),b.getName(),b.getCreatorID(),b.getAdventureID(),b.getEntries(),b.getLimit(),b.isDeleted(),b.getDescription()));
			}
		}
		return list;
	}

	@GetMapping("/viewBudget/{id}")
	public BudgetResponseDTO viewBudget(@PathVariable UUID id) throws Exception {
		return budgetServiceImplementation.viewBudget(id);

	}
//
	@GetMapping("/softDelete/{id}")
	public String softDelete(@PathVariable UUID id) throws Exception {
		SoftDeleteRequest request = new SoftDeleteRequest(id);
		budgetServiceImplementation.softDelete(request);
		return "Budget successfully moved to bin.";
	}
//
	@GetMapping("/viewTrash")
	public List<BudgetResponseDTO> viewTrash() throws Exception {
		return budgetServiceImplementation.viewTrash();
	}

	@GetMapping("/restoreBudget/{id}")
	public String restoreBudget(@PathVariable UUID id) throws Exception {
		return budgetServiceImplementation.restoreBudget(id);
	}

	@GetMapping("/expenseTotal/{id}")
	public double getExpensesTotal(@PathVariable UUID id) throws Exception {
		return budgetServiceImplementation.calculateExpenses(id);
	}

	@PostMapping("/create")
	public String createBudget(@RequestBody CreateBudgetRequest req) throws Exception {
		CreateBudgetResponse response = budgetServiceImplementation.createBudget(req.getId(),req.getName(), req.getDescription() ,req.getCreatorID(),req.getAdventureID(), req.getLimit());
		return response.getMessage();
	}


}
