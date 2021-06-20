package com.adventureit.budgetservice.Service;

import com.adventureit.adventureservice.Entity.Adventure;
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
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


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

    /**
     * @param req CreateBudgetRequest object
     *
     * @return CreateBudgetResponse Object which will indicate whether
     * creation was successful or if an error occurred
     */
    @Override
    public CreateBudgetResponse createBudget(CreateBudgetRequest req) throws Exception {
        if(budgetRepository.findBudgetById(req.getId()) != null){
            throw new Exception("Budget already exists.");
        }
        if(req.getName() == null){
            throw new Exception("Budget name not provided.");
        }
        if(req.getId() == null){
            throw new Exception("Budget ID not provided.");
        }
        if(req.getTransactions() == null){
            throw new Exception("Transactions Array is null");
        }

        budgetRepository.save(new Budget(req.getId(),req.getName(),req.getTransactions()));
        return new CreateBudgetResponse(true);
    }

    /**
     * @param req ViewBudgetRequest object
     *
     * @return ViewBudgetResponse Object which will indicate whether
     * the request was successful or if an error occurred and return the Budget object
     */
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

    /**
     * @param req AddIncomeEntryRequest object
     *
     * @return AddIncomeEntryResponse Object which will indicate whether
     * the request was successful or if an error occurred and return a message
     */
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

    /**
     * @param req RemoveEntryRequest object
     *
     * @return RemoveEntryResponse Object which will indicate whether
     * the request was successful or if an error occurred and return a message
     */
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

    /**
     * @param req AddExpenseEntryRequest object
     *
     * @return AddExpenseEntryResponse Object which will indicate whether
     * the request was successful or if an error occurred and return a message
     */
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

    /**
     * @param req EditBudgetRequest object
     *
     * @return EditBudgetResponse Object which will indicate whether
     * the request was successful or if an error occurred and return a message
     */
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

    /**
     * @param req SoftDeleteRequest object
     *
     * @return SoftDeleteResponse Object which will indicate whether
     * the request was successful or if an error occurred and return a message
     */
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

    /**
     * @param req HardDeleteRequest object
     *
     * @return HardDeleteResponse Object which will indicate whether
     * the request was successful or if an error occurred and return a message
     */
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

    /**
     * @return  ViewTrashResponse Object which will indicate whether
     * the request was successful or if an error occurred and return all Budget objects in the trash
     */
    @Override
    public ViewTrashResponse viewTrash() throws Exception {
        return new ViewTrashResponse(true,budgetRepository.findAllByDeletedEquals(true));
    }

    @Override
    public void mockPopulate(){
        final UUID mockBudgetID1 = UUID.fromString("d53a7090-45f1-4eb2-953a-2258841949f8");
        final UUID mockBudgetID2 = UUID.fromString("26356837-f076-41ec-85fa-f578df7e3717");
        final UUID mockBudgetID3 = UUID.fromString("2bb5e28c-90de-4830-ae83-f4f459898e6a");
        final UUID mockBudgetID4 = UUID.fromString("1b4534b4-65e6-4dc7-9961-65743940c86f");
        final UUID mockBudgetID5 = UUID.fromString("27f68e13-c8b9-4db8-915b-766e71efc16a");
        final UUID mockBudgetID6 = UUID.fromString("dcee3250-c653-4cd4-9edc-f77bd6b6eb3f");

        final UUID mockEntryID1 = UUID.fromString("4c31ac61-832e-454c-8efb-a3fc16ef97a0");
        final UUID mockEntryID2 = UUID.fromString("200959c2-7bd9-4c43-ae1c-c3e6776e3b33");

        Income mockEntry1 = new Income(mockEntryID1,200.0,"Mock Entry 1","Mock Income Entry");
        Expense mockEntry2 = new Expense(mockEntryID2,300.0,"Mock Entry 2","Mock Expense Entry");

        this.budgetEntryRepository.save(mockEntry1);
        this.budgetEntryRepository.save(mockEntry2);

        ArrayList<BudgetEntry> mockEntries = new ArrayList<BudgetEntry>(Arrays.asList(mockEntry1,mockEntry2));

        this.budgetRepository.save(new Budget(mockBudgetID1, "Mock Budget 1",mockEntries));
        this.budgetRepository.save(new Budget(mockBudgetID2, "Mock Budget 2",mockEntries));
        this.budgetRepository.save(new Budget(mockBudgetID3, "Mock Budget 3",mockEntries));
        this.budgetRepository.save(new Budget(mockBudgetID4, "Mock Budget 4",mockEntries));
        this.budgetRepository.save(new Budget(mockBudgetID5, "Mock Budget 5",mockEntries));
        this.budgetRepository.save(new Budget(mockBudgetID6, "Mock Budget 6",mockEntries));
    }

    @Override
    public void mockCreateBudget(String name){
        final UUID mockBudgetID = UUID.fromString("4f5c23e8-b552-47ae-908c-859e9cb94580");
        budgetRepository.save(new Budget(mockBudgetID,name,new ArrayList<BudgetEntry>()));
    }
}
