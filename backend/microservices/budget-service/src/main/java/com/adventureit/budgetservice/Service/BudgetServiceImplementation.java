package com.adventureit.budgetservice.Service;

import com.adventureit.adventureservice.Repository.AdventureRepository;
import com.adventureit.budgetservice.Entity.Budget;
import com.adventureit.budgetservice.Entity.BudgetEntry;
import com.adventureit.budgetservice.Entity.Expense;
import com.adventureit.budgetservice.Entity.Income;
import com.adventureit.budgetservice.Repository.BudgetEntryRepository;
import com.adventureit.budgetservice.Repository.BudgetRepository;
import com.adventureit.budgetservice.Requests.*;
import com.adventureit.budgetservice.Responses.*;
import org.springframework.beans.factory.annotation.Autowired;

public class BudgetServiceImplementation implements BudgetService {
    @Autowired
    BudgetRepository budgetRepository;
    AdventureRepository adventureRepository;
    BudgetEntryRepository budgetEntryRepository;

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
    public AddIncomeEntryResponse addIncomeEntry(AddIncomeEntryRequest req) throws Exception {
        if(budgetRepository.findBudgetById(req.getBudgetID()) == null){
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
        if(!budget.CheckIfEntryExists(budget.getTransactions(),req.getId())){
            throw new Exception("Income Entry does not exist.");
        }
        BudgetEntry budgetEntry = new Income(req.getId(),req.getAmount(),req.getTitle(),req.getDescription());
        budgetEntryRepository.save(budgetEntry);
        budget.getTransactions().add(budgetEntry);
        budgetRepository.save(budget);

        return new AddIncomeEntryResponse(true);
    }

    @Override
    public RemoveIncomeEntryResponse removeIncomeEntry(RemoveIncomeEntryRequest req) throws Exception {
        if(budgetRepository.findBudgetById(req.getBudgetID()) == null){
            throw new Exception("Budget does not exist.");
        }
        if(req.getId() == null){
            throw new Exception("Income Entry not successfully added");
        }
        Budget budget = budgetRepository.findBudgetById(req.getBudgetID());
        if(!budget.CheckIfEntryExists(budget.getTransactions(),req.getId())){
            throw new Exception("Income Entry does not exist.");
        }
        budget.getTransactions().removeIf(transaction -> transaction.getId() == req.getId());
        budgetRepository.save(budget);
        budgetEntryRepository.delete(budgetEntryRepository.findBudgetEntryById(req.getId()));
        return new RemoveIncomeEntryResponse(true);
    }

    @Override
    public AddExpenseEntryResponse addExpenseEntry(AddExpenseEntryRequest req) throws Exception {
        if(req.getId() == null){
            throw new Exception("Expense Entry not successfully added");
        }
        if(req.getBudgetID() == null){
            throw new Exception("Expense Entry not successfully added");
        }
        if(req.getAmount() == 0.0){
            throw new Exception("Expense Entry not successfully added");
        }
        if(req.getTitle() == null || req.getTitle() == "" ){
            throw new Exception("Expense Entry not successfully added");
        }
        if(req.getDescription() == null || req.getDescription() == "" ){
            throw new Exception("Expense Entry not successfully added");
        }
        Budget budget = budgetRepository.findBudgetById(req.getBudgetID());
        if(!budget.CheckIfEntryExists(budget.getTransactions(),req.getId())){
            throw new Exception("Expense Entry does not exist.");
        }

        BudgetEntry budgetEntry = new Expense(req.getId(),req.getAmount(),req.getTitle(),req.getDescription());
        budgetEntryRepository.save(budgetEntry);
        budget.getTransactions().add(budgetEntry);
        budgetRepository.save(budget);

        return new AddExpenseEntryResponse(true);
    }

    @Override
    public RemoveExpenseEntryResponse removeExpenseEntry(RemoveExpenseEntryRequest req) throws Exception {
        if(budgetRepository.findBudgetById(req.getBudgetID()) == null){
            throw new Exception("Budget does not exist.");
        }
        if(req.getId() == null){
            throw new Exception("Expense Entry not successfully added");
        }
        Budget budget = budgetRepository.findBudgetById(req.getBudgetID());
        if(!budget.CheckIfEntryExists(budget.getTransactions(),req.getId())){
            throw new Exception("Expense Entry does not exist.");
        }
        budget.getTransactions().removeIf(transaction -> transaction.getId() == req.getId());
        budgetRepository.save(budget);
        budgetEntryRepository.delete(budgetEntryRepository.findBudgetEntryById(req.getId()));
        return new RemoveExpenseEntryResponse(true);
    }

    @Override
    public EditBudgetResponse editBudget(EditBudgetRequest req) throws Exception {
        if(req.getId() == null){
            throw new Exception("Budget not successfully edited");
        }
        if(req.getBudgetID() == null){
            throw new Exception("Budget does not exist");
        }
        if(req.getTitle() == null){
            throw new Exception("Budget not successfully edited");
        }
        if(req.getDescription() == null){
            throw new Exception("Budget not successfully edited");
        }
        Budget budget = budgetRepository.findBudgetById(req.getBudgetID());
        if(!budget.CheckIfEntryExists(budget.getTransactions(),req.getId())){
            throw new Exception("Expense Entry does not exist.");
        }
        BudgetEntry entry = budgetEntryRepository.findBudgetEntryById(req.getId());
        int x = budget.getTransactions().indexOf(entry);
        if(req.getAmount() != 0.0){
            entry.setAmount(req.getAmount());
        }
        if(req.getDescription() != ""){
               entry.setDescription(req.getDescription());
        }
        if(req.getTitle() != ""){
            entry.setTitle(req.getTitle());
        }
        budget.getTransactions().set(x,entry);
        budgetRepository.save(budget);
        budgetEntryRepository.save(entry);
        return new EditBudgetResponse(true);
    }
}
