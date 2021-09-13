package com.adventureit.budgetservice.controllers;

import com.adventureit.budgetservice.entity.Budget;
import com.adventureit.budgetservice.entity.BudgetEntry;
import com.adventureit.budgetservice.exception.BudgetNotFoundException;
import com.adventureit.budgetservice.graph.Edge;
import com.adventureit.budgetservice.graph.Node;
import com.adventureit.budgetservice.repository.BudgetRepository;
import com.adventureit.budgetservice.service.BudgetServiceImplementation;
import com.adventureit.shareddtos.budget.requests.*;
import com.adventureit.shareddtos.budget.responses.*;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/budget")
public class BudgetController {
	BudgetServiceImplementation budgetServiceImplementation;
	BudgetRepository budgetRepository;

	public BudgetController(BudgetServiceImplementation budgetServiceImplementation, BudgetRepository budgetRepository){
		this.budgetServiceImplementation = budgetServiceImplementation;
		this.budgetRepository = budgetRepository;
	}

	@GetMapping("/viewBudgetsByAdventure/{id}")
	public List<BudgetResponseDTO> viewBudgetsByAdventure(@PathVariable UUID id) {
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
	public List<ViewBudgetResponse> viewBudget(@PathVariable UUID id) {
		return budgetServiceImplementation.viewBudget(id);
	}

	@GetMapping("/softDelete/{id}/{userID}")
	public String softDelete(@PathVariable UUID id, @PathVariable UUID userID) {
		SoftDeleteRequest request = new SoftDeleteRequest(id, userID);
		budgetServiceImplementation.softDelete(request);
		return "Budget successfully moved to bin.";
	}

	@GetMapping("/viewTrash/{id}")
	public List<BudgetResponseDTO> viewTrash(@PathVariable UUID id) {
		return budgetServiceImplementation.viewTrash(id);
	}

	@GetMapping("/restoreBudget/{id}/{userID}")
	public String restoreBudget(@PathVariable UUID id, @PathVariable UUID userID) {
		return budgetServiceImplementation.restoreBudget(id,userID);
	}

	@GetMapping("/hardDelete/{id}/{userID}")
	public String hardDelete(@PathVariable UUID id, @PathVariable UUID userID) {
		HardDeleteResponse response = budgetServiceImplementation.hardDelete(id, userID);
		return response.getMessage();
	}

	@PostMapping("/create")
	public String createBudget(@RequestBody CreateBudgetRequest req) {
		CreateBudgetResponse response = budgetServiceImplementation.createBudget(req.getName(), req.getDescription() ,req.getCreatorID(),req.getAdventureID());
		return response.getMessage();
	}

	@PostMapping("/addUTOExpense")
	public String addUTOExpense(@RequestBody AddUTOExpenseEntryRequest req) {
		AddUTOExpenseEntryResponse response = budgetServiceImplementation.addUTOExpenseEntry(req.getEntryContainerID(),req.getAmount(),req.getTitle(),req.getDescription(),req.getCategory(),req.getPayer(),req.getPayee());
		return response.getMessage();
	}

	@PostMapping("/addUTUExpense")
	public String addUTUExpense(@RequestBody AddUTUExpenseEntryRequest req) {
		AddUTUExpenseEntryResponse response = budgetServiceImplementation.addUTUExpenseEntry(req.getEntryContainerID(),req.getAmount(),req.getTitle(),req.getDescription(),req.getCategory(),req.getPayer(),req.getPayee());
		return response.getMessage();
	}

	@PostMapping("/editBudget")
	public String editBudget(@RequestBody EditBudgetRequest req) {
		EditBudgetResponse response = budgetServiceImplementation.editBudget(req);
		return response.getMessage();
	}

	@GetMapping("/removeEntry/{id}")
	public String removeEntry(@PathVariable UUID id) {
		RemoveEntryResponse response = budgetServiceImplementation.removeEntry(id);
		return response.getMessage();
	}

	@GetMapping("/calculateExpense/{budgetID}/{userName}")
	public double calculateExpense(@PathVariable UUID budgetID, @PathVariable String userName) {
		return budgetServiceImplementation.calculateExpensesPerUser(budgetID, userName);
	}

	@GetMapping("/getEntriesPerCategory/{id}")
	public List<Integer> getEntriesPerCategory(@PathVariable UUID id) {
		return budgetServiceImplementation.getEntriesPerCategory(id);
	}

	@GetMapping("/getReportList/{id}")
	public List<String> getReportList(@PathVariable UUID id) {
		return budgetServiceImplementation.getReportList(id);
	}

	@GetMapping("/generateIndividualReport/{id}/{userName}")
	public Object generateIndividualReport(@PathVariable UUID id, @PathVariable String userName) {
		try {
			return budgetServiceImplementation.generateIndividualReport(userName,id);
		}
		catch(JSONException e){
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
	}

	@GetMapping("/getBudgetByBudgetId/{budgetId}")
	public BudgetResponseDTO getBudgetByBudgetId(@PathVariable UUID budgetId) {
		return budgetServiceImplementation.getBudgetByBudgetContainerId(budgetId);
	}

	@GetMapping("/getBudgetByBudgetEntryId/{budgetId}")
	public BudgetResponseDTO getBudgetByBudgetEntryId(@PathVariable UUID budgetId) {
		return budgetServiceImplementation.getBudgetByBudgetEntryId(budgetId);
	}

	@GetMapping("/kevinTest")
	public List<Edge> test() {
		List<Edge> edges = budgetServiceImplementation.kevTest();
		for (int i = 0;i<edges.size();i++){
			System.out.println("UUID: "+edges.get(i).getEntryId());
			System.out.println(edges.get(i).getPayer().getName()+" pays "+edges.get(i).getAmount()+ " to "+edges.get(i).getPayee().getName());
		}
		return edges;
	}

}
