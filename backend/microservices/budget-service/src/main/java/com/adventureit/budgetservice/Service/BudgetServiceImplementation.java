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

import java.util.List;
import java.util.UUID;


@Service("BudgetServiceImplementation")
public class BudgetServiceImplementation implements BudgetService {
    @Autowired
    private BudgetRepository budgetRepository;
    @Autowired
    private BudgetEntryRepository budgetEntryRepository;

    @Autowired
    public BudgetServiceImplementation(BudgetRepository budgetRepository, BudgetEntryRepository budgetEntryRepository) {
        this.budgetRepository = budgetRepository;
        this.budgetEntryRepository = budgetEntryRepository;
    }

    @Override
    public CreateBudgetResponse createBudget(UUID id, String name, String description, UUID creatorID, UUID adventureID, double limit) throws Exception {
        if (budgetRepository.findBudgetByBudgetID(id) != null) {
            throw new Exception("Budget already exists.");
        }
        if (name == null) {
            throw new Exception("Budget name not provided.");
        }
        if (id == null) {
            throw new Exception("Budget ID not provided.");
        }
        if (limit == 0) {
            throw new Exception("Limit cannot be 0");
        }

        budgetRepository.save(new Budget(id, name, description, creatorID, adventureID, limit));
        return new CreateBudgetResponse(true);
    }

    @Override
    public BudgetResponseDTO viewBudget(UUID id) throws Exception {
        if (budgetRepository.findBudgetByBudgetIDAndDeletedEquals(id, false) == null) {
            throw new Exception("Budget does not exist.");
        }
        if (id == null) {
            throw new Exception("Budget ID was not provided.");
        }

        Budget b = budgetRepository.findBudgetByBudgetIDAndDeletedEquals(id, false);
        return new BudgetResponseDTO(b.getBudgetId(), b.getName(), b.getCreatorID(), b.getAdventureID(), b.getBudgetLimit(), b.isDeleted(), b.getDescription());

    }

    @Override
//  @Transactional
    public AddIncomeEntryResponse addIncomeEntry(UUID id, UUID entryContainerID, double amount, String title, String description) throws Exception {
        if (budgetRepository.findBudgetByBudgetID(entryContainerID) == null) {
            throw new Exception("Budget does not exist.");
        }
        if (id == null) {
            throw new Exception("Income Entry ID not provided");
        }
        if (entryContainerID == null) {
            throw new Exception("Budget ID not provided");
        }
        if (amount == 0.0) {
            throw new Exception("Amount not provided");
        }
        if (title == null || title.equals("")) {
            throw new Exception("Title not provided");
        }
        if (description == null || description.equals("")) {
            throw new Exception("Description not provided");
        }
        Budget budget = budgetRepository.findBudgetByBudgetID(entryContainerID);
        BudgetEntry entry = budgetEntryRepository.findBudgetEntryByBudgetEntryIDAndEntryContainerID(id, entryContainerID);
        if (entry != null) {
            throw new Exception("Income Entry already exists.");
        }

        BudgetEntry budgetEntry = new Income(id, entryContainerID, amount, title, description);
        budgetEntryRepository.save(budgetEntry);
        budgetRepository.save(budget);
        return new AddIncomeEntryResponse(true);
    }

//    @Override
//    @Transactional
//    public String insertEntry(UUID id, UUID containerID) throws Exception {
//        if(id == null){
//            throw new Exception("ID not provided");
//        }
//        if(containerID == null){
//            throw new Exception("Budget ID not provided");
//        }
//
//        Budget budget = budgetRepository.findBudgetById(containerID);
//        BudgetEntry budgetEntry = budgetEntryRepository.findBudgetEntryById(id);
//        budget.getEntries().add(budgetEntry);
//        budgetRepository.save(budget);
//        return "Entry successfully added";
//    }

    @Override
    public RemoveEntryResponse removeEntry(UUID id, UUID entryContainerID) throws Exception {
        if (budgetRepository.findBudgetByBudgetID(entryContainerID) == null) {
            throw new Exception("Budget does not exist.");
        }
        if (id == null) {
            throw new Exception("Entry ID not provided.");
        }
        Budget budget = budgetRepository.findBudgetByBudgetID(entryContainerID);
        BudgetEntry entry = budgetEntryRepository.findBudgetEntryByBudgetEntryIDAndEntryContainerID(id, entryContainerID);
        if (entry == null) {
            throw new Exception("Entry does not exist.");
        }

        budgetRepository.save(budget);
        budgetEntryRepository.delete(entry);
        return new RemoveEntryResponse(true);
    }

    @Override
//    @Transactional
    public AddExpenseEntryResponse addExpenseEntry(UUID id, UUID entryContainerID, double amount, String title, String description) throws Exception {
        if (budgetRepository.findBudgetByBudgetID(entryContainerID) == null) {
            throw new Exception("Budget does not exist.");
        }
        if (id == null) {
            throw new Exception("Expense Entry ID not provided");
        }
        if (entryContainerID == null) {
            throw new Exception("Budget ID not provided");
        }
        if (amount == 0.0) {
            throw new Exception("Amount not provided");
        }
        if (title == null || title.equals("")) {
            throw new Exception("Title not provided.");
        }
        if (description == null || description.equals("")) {
            throw new Exception("Description not provided.");
        }
        Budget budget = budgetRepository.findBudgetByBudgetID(entryContainerID);
        BudgetEntry entry = budgetEntryRepository.findBudgetEntryByBudgetEntryIDAndEntryContainerID(id, entryContainerID);
        if (entry != null) {
            throw new Exception("Expense Entry already exists.");
        }

        if ((calculateExpenses(entryContainerID) + amount) > budget.getBudgetLimit()) {
            throw new Exception("Budget exceeding limit");
        }

        BudgetEntry budgetEntry = new Expense(id, entryContainerID, amount, title, description);
        budgetEntryRepository.save(budgetEntry);
        budgetRepository.save(budget);
        return new AddExpenseEntryResponse(true);
    }

    /**
     * @param req EditBudgetRequest object
     * @return EditBudgetResponse Object which will indicate whether
     * the request was successful or if an error occurred and return a message
     */
    @Override
    public EditBudgetResponse editBudget(EditBudgetRequest req) throws Exception {
        if (budgetRepository.findBudgetByBudgetID(req.getBudgetID()) == null) {
            throw new Exception("Budget does not exist.");
        }
        if (req.getId() == null) {
            throw new Exception("Entry ID not provided.");
        }
        if (req.getBudgetID() == null) {
            throw new Exception("Budget ID not provided");
        }
        if (req.getTitle() == null) {
            throw new Exception("Title Field is null.");
        }
        if (req.getDescription() == null) {
            throw new Exception("Description Field is null.");
        }

        Budget budget = budgetRepository.findBudgetByBudgetID(req.getBudgetID());
        BudgetEntry entry = budgetEntryRepository.findBudgetEntryByBudgetEntryIDAndEntryContainerID(req.getId(), req.getBudgetID());

        if (entry == null) {
            throw new Exception("Entry does not exist.");
        }

        if (req.getAmount() != 0.0) {
            entry.setAmount(req.getAmount());
        }
        if (!req.getDescription().equals("")) {
            entry.setDescription(req.getDescription());
        }
        if (!req.getTitle().equals("")) {
            entry.setTitle(req.getTitle());
        }

        budgetEntryRepository.save(entry);
        return new EditBudgetResponse(true);
    }

    /**
     * @param req SoftDeleteRequest object
     * @return SoftDeleteResponse Object which will indicate whether
     * the request was successful or if an error occurred and return a message
     */
    @Override
    public SoftDeleteResponse softDelete(SoftDeleteRequest req) throws Exception {
        if (req.getId() == null) {
            throw new Exception("Budget ID not provided.");
        }

        Budget budget = budgetRepository.findBudgetByBudgetIDAndDeletedEquals(req.getId(), false);

        if (budget == null) {
            throw new Exception("Budget does not exist.");
        }

        budget.setDeleted(true);
        budgetRepository.save(budget);
        return new SoftDeleteResponse(true);
    }

    /**
     * @param req HardDeleteRequest object
     * @return HardDeleteResponse Object which will indicate whether
     * the request was successful or if an error occurred and return a message
     */
    @Override
    public HardDeleteResponse hardDelete(HardDeleteRequest req) throws Exception {
        if (req.getId() == null) {
            throw new Exception("Budget ID not provided.");
        }

        Budget budget = budgetRepository.findBudgetByBudgetID(req.getId());

        if (budget==null || budget.isDeleted()==false) {
            throw new Exception("Budget is not in trash.");
        }

        budgetEntryRepository.removeAllByEntryContainerID(req.getId());
        budgetRepository.delete(budget);

        return new HardDeleteResponse(true);
    }

    /**
     * @return ViewTrashResponse Object which will indicate whether
     * the request was successful or if an error occurred and return all Budget objects in the trash
     */
    @Override
    public List<BudgetResponseDTO> viewTrash(UUID id) throws Exception {
        List<Budget> budgets = budgetRepository.findAllByAdventureID(id);
        List<BudgetResponseDTO> list = new ArrayList<>();
        for (Budget b : budgets) {
            if (b.isDeleted()) {
                list.add(new BudgetResponseDTO(b.getBudgetId(), b.getName(), b.getCreatorID(), b.getAdventureID(), b.getBudgetLimit(), b.isDeleted(), b.getDescription()));
            }
        }
        return list;
    }


    public String restoreBudget(UUID id) throws Exception {
        if (budgetRepository.findBudgetByBudgetID(id) == null) {
            throw new Exception("Budget does not exist.");
        }

        Budget budget = budgetRepository.findBudgetByBudgetID(id);
        budget.setDeleted(false);
        budgetRepository.save(budget);
        return "Budget was restored";
    }

    @Override
    public String calculateBudget(UUID id) throws Exception {
        if (id == null) {
            throw new Exception("ID not provided");
        }

        Budget budget = budgetRepository.findBudgetByBudgetID(id);
        if (budget == null) {
            throw new Exception("Budget does not exist.");
        }

        double sum = 0.0;
        List<BudgetEntry> entries = budgetEntryRepository.findBudgetEntryByEntryContainerID(id);

        for (BudgetEntry entry : entries) {

            if (entry instanceof Income) {
                sum += entry.getAmount();
            } else {
                sum -= entry.getAmount();
            }
        }

        return Double.toString(sum);
    }

    @Override
    public double calculateExpenses(UUID id) {
        Budget budget = budgetRepository.findBudgetByBudgetID(id);
        double sum = 0.0;
        BudgetEntry budgetEntry;

        List<BudgetEntry> entries = budgetEntryRepository.findBudgetEntryByEntryContainerID(id);

        for (BudgetEntry entry : entries) {
            if (entry instanceof Expense) {
                sum += entry.getAmount();
            }
        }

        return sum;
    }


    @Override
    public void mockPopulate() {
        final UUID mockBudgetID1 = UUID.fromString("d53a7090-45f1-4eb2-953a-2258841949f8");
        final UUID mockBudgetID2 = UUID.fromString("26356837-f076-41ec-85fa-f578df7e3717");
        final UUID mockBudgetID3 = UUID.fromString("2bb5e28c-90de-4830-ae83-f4f459898e6a");

        final UUID mockEntryID1 = UUID.fromString("4c31ac61-832e-454c-8efb-a3fc16ef97a0");
        final UUID mockEntryID2 = UUID.fromString("200959c2-7bd9-4c43-ae1c-c3e6776e3b33");
        final UUID mockEntryID3 = UUID.fromString("0c4dfedd-9a07-42ed-a178-b4e7656a956c");

        final UUID mockAdventureID1 = UUID.fromString("948f3e05-4bca-49ba-8955-fb936992fe02");
        final UUID mockAdventureID2 = UUID.fromString("948f3e05-4bca-49ba-8955-fb936992fe02");
        final UUID mockAdventureID3 = UUID.fromString("948f3e05-4bca-49ba-8955-fb936992fe02");

        final UUID mockCreatorID1 = UUID.fromString("b99521e3-a7e9-45b8-a18e-421af8bbca15");
        final UUID mockCreatorID2 = UUID.fromString("5de93a3f-3deb-443b-823e-cacb2600ac71");
        final UUID mockCreatorID3 = UUID.fromString("eccc917a-091c-496e-9936-15f8f3889959");

        BudgetEntry mockEntry1 = new Income(mockEntryID1, mockBudgetID1, 200.0, "Mock Entry 1", "Mock Income Entry");
        BudgetEntry mockEntry2 = new Expense(mockEntryID2, mockBudgetID2, 300.0, "Mock Entry 2", "Mock Expense Entry");
        BudgetEntry mockEntry3 = new Expense(mockEntryID3, mockBudgetID3, 600.0, "Mock Entry 3", "Mock Expense Entry");

        Budget budget1 = new Budget(mockBudgetID1, "Mock Budget 1", "Description for mock budget 1", mockCreatorID1, mockAdventureID1, 10000);
        Budget budget2 = new Budget(mockBudgetID2, "Mock Budget 2", "Description for mock budget 2", mockCreatorID2, mockAdventureID2, 10000);
        Budget budget3 = new Budget(mockBudgetID3, "Mock Budget 3", "Description for mock budget 3", mockCreatorID3, mockAdventureID3, 10000);

        this.budgetRepository.save(budget1);
        this.budgetRepository.save(budget2);
        this.budgetRepository.save(budget3);

        budgetEntryRepository.save(mockEntry1);
        budgetEntryRepository.save(mockEntry2);
        budgetEntryRepository.save(mockEntry3);


    }


    @Override
    public void mockPopulateTrash() {
        final UUID mockBudgetID1 = UUID.fromString("86224c30-fb96-4b02-9aca-ca7b61c6bede");
        final UUID mockBudgetID2 = UUID.fromString("83a2bb60-69c9-486f-bb55-8a3e55cb891d");
        final UUID mockBudgetID3 = UUID.fromString("ab500ee3-a069-4a89-a5b3-3aa9e10330e6");

        Budget budget1 = new Budget(mockBudgetID1, "Mock Deleted Budget 1", "Description for mock budget 1", UUID.randomUUID(), UUID.fromString("3c8a5381-1f54-4ac3-8d29-7e122f05a858"), 5000);
        Budget budget2 = new Budget(mockBudgetID2, "Mock Deleted Budget 2", "Description for mock budget 2", UUID.randomUUID(), UUID.fromString("3c8a5381-1f54-4ac3-8d29-7e122f05a858"), 5000);
        Budget budget3 = new Budget(mockBudgetID3, "Mock Deleted Budget 3", "Description for mock budget 3", UUID.randomUUID(), UUID.fromString("3c8a5381-1f54-4ac3-8d29-7e122f05a858"), 5000);

        budget1.setDeleted(true);
        budget2.setDeleted(true);
        budget3.setDeleted(true);

        this.budgetRepository.save(budget1);
        this.budgetRepository.save(budget2);
        this.budgetRepository.save(budget3);
    }

    @Override
    public void mockCreateBudget(String name) {
        final UUID mockBudgetID = UUID.fromString("4f5c23e8-b552-47ae-908c-859e9cb94580");
        //Budget budget = new Budget(mockBudgetID,name, "hello hello hello",UUID.randomUUID(),UUID.fromString("ad8e9b74-b4be-464e-a538-0cb78e9c2f8b"),6000);
        //budgetRepository.save(budget);
    }


}
