package com.adventureit.budgetservice.Service;

import com.adventureit.adventureservice.Repository.AdventureRepository;
import com.adventureit.budgetservice.Entity.Budget;
import com.adventureit.budgetservice.Entity.BudgetEntry;
import com.adventureit.budgetservice.Repository.BudgetRepository;
import com.adventureit.budgetservice.Requests.AddIncomeEntryRequest;
import com.adventureit.budgetservice.Requests.CreateBudgetRequest;
import com.adventureit.budgetservice.Requests.ViewBudgetRequest;
import com.adventureit.budgetservice.Responses.AddIncomeEntryResponse;
import com.adventureit.budgetservice.Responses.CreateBudgetResponse;
import com.adventureit.budgetservice.Responses.ViewBudgetResponse;
import org.springframework.beans.factory.annotation.Autowired;

public class BudgetServiceImplementation implements BudgetService {
    @Autowired
    BudgetRepository budgetRepository;
    AdventureRepository adventureRepository;

    @Override
    public CreateBudgetResponse createBudget(CreateBudgetRequest req) throws Exception {
        if(budgetRepository.findBudgetById(req.getId()) != null){
            throw new Exception("Budget already exists.");
        }
        if(req.getAdventureID() != null){
            throw new Exception("Adventure ID not provided.");
        }
        if(adventureRepository.findByID(req.getId()) != null){
            throw new Exception("Adventure does not exist.");
        }
        if(req.getId() == null){
            throw new Exception("Budget was not created.");
        }
        if(req.getTransactions() == null){
            throw new Exception("Budget was not created");
        }

        budgetRepository.save(new Budget(req.getId(),req.getAdventureID(),req.getTransactions()));
        return new CreateBudgetResponse(true);
    }

    @Override
    public ViewBudgetResponse viewBudget(ViewBudgetRequest req) throws Exception {
        if(budgetRepository.findBudgetById(req.getId()) == null){
            throw new Exception("Budget does not exist.");
        }
        if(req.getId() == null){
            throw new Exception("Budget id was not provided.");
        }

        return new ViewBudgetResponse(budgetRepository.findBudgetById(req.getId()),true);
    }

    @Override
    public AddIncomeEntryResponse AddIncomeEntry(AddIncomeEntryRequest req) throws Exception {
        if(budgetRepository.findBudgetById(req.getId()) == null){
            throw new Exception("Budget does not exist.");
        }
        if(req.getId() == null){
            throw new Exception("Income Entry not successfully added");
        }
        if(req.getBudgetID() == null){
            throw new Exception("Income Entry not successfully added");
        }
        if(req.getAmount() == 0.0){
            throw new Exception("Income Entry not successfully added");
        }
        if(req.getTitle() == null || req.getTitle() == "" ){
            throw new Exception("Income Entry not successfully added");
        }
        if(req.getDescription() == null || req.getDescription() == "" ){
            throw new Exception("Income Entry not successfully added");
        }

        Budget budget = budgetRepository.findBudgetById(req.getBudgetID());
        budget.getTransactions().add(new BudgetEntry(req.getId(),req.getAmount(),req.getTitle(),req.getDescription()));
        budgetRepository.save(budget);

        return new AddIncomeEntryResponse(true);
    }
}
