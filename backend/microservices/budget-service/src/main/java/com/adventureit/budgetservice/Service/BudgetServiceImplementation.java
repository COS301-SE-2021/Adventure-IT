package com.adventureit.budgetservice.Service;

import com.adventureit.budgetservice.Entity.Budget;
import com.adventureit.budgetservice.Entity.BudgetEntry;
import com.adventureit.budgetservice.Entity.Expense;
import com.adventureit.budgetservice.Entity.Income;
import com.adventureit.budgetservice.Repository.BudgetEntryRepository;
import com.adventureit.budgetservice.Repository.BudgetRepository;
import com.adventureit.budgetservice.Requests.*;
import com.adventureit.budgetservice.Responses.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service()
public class BudgetServiceImplementation implements BudgetService {
    @Autowired
    private BudgetRepository budgetRepository;
    @Autowired
    private BudgetEntryRepository budgetEntryRepository;

    @Autowired
    public BudgetServiceImplementation(BudgetRepository budgetRepository, BudgetEntryRepository budgetEntryRepository){
        this.budgetRepository = budgetRepository;
        this.budgetEntryRepository = budgetEntryRepository;
    }

    @Override
    public CreateBudgetResponse createBudget(CreateBudgetRequest req) throws Exception {
        if(budgetRepository.findBudgetById(req.getId()) != null){
            throw new Exception("Budget already exists.");
        }
        if(req.getAdventureID() == null){
            throw new Exception("Adventure ID not provided.");
        }
        if(req.getId() == null){
            throw new Exception("Budget ID not provided.");
        }
        if(req.getTransactions() == null){
            throw new Exception("Transactions Array is null");
        }

        budgetRepository.save(new Budget(req.getId(),req.getAdventureID(),req.getTransactions()));
        return new CreateBudgetResponse(true);
    }

    @Override
    public ViewBudgetResponse viewBudget(ViewBudgetRequest req) throws Exception {
        if(budgetRepository.findBudgetByIdAndDeletedEquals(req.getId(),false) == null){
            throw new Exception("Budget does not exist.");
        }
        if(req.getId() == null){
            throw new Exception("Budget ID was not provided.");
        }

        return new ViewBudgetResponse(budgetRepository.findBudgetById(req.getId()),true);
    }

    @Override
    public AddIncomeEntryResponse addIncomeEntry(AddIncomeEntryRequest req) throws Exception {
        if(budgetRepository.findBudgetById(req.getBudgetID()) == null){
            throw new Exception("Budget does not exist.");
        }
        if(req.getId() == null){
            throw new Exception("Income Entry ID not provided");
        }
        if(req.getBudgetID() == null){
            throw new Exception("Budget ID not provided");
        }
        if(req.getAmount() == 0.0){
            throw new Exception("Amount not provided");
        }
        if(req.getTitle() == null || req.getTitle().equals("")){
            throw new Exception("Title not provided");
        }
        if(req.getDescription() == null || req.getDescription().equals("")){
            throw new Exception("Description not provided");
        }
        Budget budget = budgetRepository.findBudgetById(req.getBudgetID());
        if(budget.CheckIfEntryExists(budget.getTransactions(),req.getId())){
            throw new Exception("Income Entry already exists.");
        }

        BudgetEntry budgetEntry = new Income(req.getId(),req.getAmount(),req.getTitle(),req.getDescription());
        budgetEntryRepository.save(budgetEntry);
        budget.getTransactions().add(budgetEntry);
        budgetRepository.save(budget);
        return new AddIncomeEntryResponse(true);
    }

    @Override
    public RemoveEntryResponse removeEntry(RemoveEntryRequest req) throws Exception {
        if(budgetRepository.findBudgetById(req.getBudgetID()) == null){
            throw new Exception("Budget does not exist.");
        }
        if(req.getId() == null){
            throw new Exception("Entry ID not provided.");
        }
        Budget budget = budgetRepository.findBudgetById(req.getBudgetID());
        if(!budget.CheckIfEntryExists(budget.getTransactions(),req.getId())){
            throw new Exception("Entry does not exist.");
        }

        BudgetEntry budgetEntry = budget.getEntry(budget.getTransactions(),req.getId());
        budget.getTransactions().remove(budgetEntry);
        budgetRepository.save(budget);
        budgetEntryRepository.delete(budgetEntry);
        return new RemoveEntryResponse(true);
    }

    @Override
    public AddExpenseEntryResponse addExpenseEntry(AddExpenseEntryRequest req) throws Exception {
        if(budgetRepository.findBudgetById(req.getBudgetID()) == null){
            throw new Exception("Budget does not exist.");
        }
        if(req.getId() == null){
            throw new Exception("Expense Entry ID not provided");
        }
        if(req.getBudgetID() == null){
            throw new Exception("Budget ID not provided");
        }
        if(req.getAmount() == 0.0){
            throw new Exception("Amount not provided");
        }
        if(req.getTitle() == null || req.getTitle().equals("")){
            throw new Exception("Title not provided.");
        }
        if(req.getDescription() == null || req.getDescription().equals("")){
            throw new Exception("Description not provided.");
        }
        Budget budget = budgetRepository.findBudgetById(req.getBudgetID());
        if(budget.CheckIfEntryExists(budget.getTransactions(),req.getId())){
            throw new Exception("Expense Entry already exists.");
        }

        BudgetEntry budgetEntry = new Expense(req.getId(),req.getAmount(),req.getTitle(),req.getDescription());
        budgetEntryRepository.save(budgetEntry);
        budget.getTransactions().add(budgetEntry);
        budgetRepository.save(budget);
        return new AddExpenseEntryResponse(true);
    }

    @Override
    public EditBudgetResponse editBudget(EditBudgetRequest req) throws Exception {
        if(budgetRepository.findBudgetById(req.getBudgetID()) == null){
            throw new Exception("Budget does not exist.");
        }
        if(req.getId() == null){
            throw new Exception("Entry ID not provided.");
        }
        if(req.getBudgetID() == null){
            throw new Exception("Budget ID not provided");
        }
        if(req.getTitle() == null){
            throw new Exception("Title Field is null.");
        }
        if(req.getDescription() == null){
            throw new Exception("Description Field is null.");
        }

        Budget budget = budgetRepository.findBudgetById(req.getBudgetID());

        if(!budget.CheckIfEntryExists(budget.getTransactions(),req.getId())){
            throw new Exception("Entry does not exist.");
        }

        BudgetEntry entry = budgetEntryRepository.findBudgetEntryById(req.getId());
        int x = budget.getIndex(budget.getTransactions(),req.getId());

        if(req.getAmount() != 0.0){
            entry.setAmount(req.getAmount());
        }
        if(!req.getDescription().equals("")){
               entry.setDescription(req.getDescription());
        }
        if(!req.getTitle().equals("")){
            entry.setTitle(req.getTitle());
        }

        budget.getTransactions().set(x,entry);
        budgetRepository.save(budget);
        budgetEntryRepository.save(entry);
        return new EditBudgetResponse(true);
    }

    @Override
    public SoftDeleteResponse softDelete(SoftDeleteRequest req) throws Exception {
        if(req.getId() == null){
            throw new Exception("Budget ID not provided.");
        }

        Budget budget = budgetRepository.findBudgetByIdAndDeletedEquals(req.getId(),false);

        if(budget == null){
            throw new Exception("Budget does not exist.");
        }

        budget.setDeleted(true);
        budgetRepository.save(budget);
        return new SoftDeleteResponse(true);
    }

    @Override
    public HardDeleteResponse hardDelete(HardDeleteRequest req) throws Exception {
        if(req.getId() == null){
            throw new Exception("Budget ID not provided.");
        }

        Budget budget = budgetRepository.findBudgetByIdAndDeletedEquals(req.getId(),true);

        if(budget == null){
            throw new Exception("Budget is not in trash.");
        }

        ArrayList<BudgetEntry> entries = new ArrayList<>(budget.getTransactions());
        budgetRepository.delete(budget);
        for (BudgetEntry b : entries) {
            budgetEntryRepository.delete(b);
        }

        return new HardDeleteResponse(true);
    }

    @Override
    public ViewTrashResponse viewTrash() throws Exception {
        return new ViewTrashResponse(true,budgetRepository.findAllByDeletedEquals(true));
    }
}
