package com.adventureit.budgetservice.Service;

import com.adventureit.budgetservice.Entity.Budget;
import com.adventureit.budgetservice.Repository.BudgetRepository;
import com.adventureit.budgetservice.Requests.CreateBudgetRequest;
import com.adventureit.budgetservice.Responses.CreateBudgetResponse;
import org.springframework.beans.factory.annotation.Autowired;

public class BudgetServiceImplementation implements BudgetService {
    @Autowired
    BudgetRepository budgetRepository;

    @Override
    public CreateBudgetResponse createBudget(CreateBudgetRequest req) throws Exception {
        if(budgetRepository.findByID(req.getId()) != null){
            throw new Exception("Budget already exists.");
        }
        if(req.getId() == null){
            throw new Exception("Budget was not created.");
        }
        if(req.getTransactions() == null){
            throw new Exception("Budget was not created");

        }

        budgetRepository.save(new Budget(req.getId(),req.getTransactions()));
        return new CreateBudgetResponse(true);
    }
}
