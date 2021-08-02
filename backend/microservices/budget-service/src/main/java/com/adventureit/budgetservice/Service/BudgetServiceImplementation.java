package com.adventureit.budgetservice.Service;


import com.adventureit.budgetservice.Entity.*;
import com.adventureit.budgetservice.Repository.BudgetEntryRepository;
import com.adventureit.budgetservice.Repository.BudgetRepository;
import com.adventureit.budgetservice.Requests.*;
import com.adventureit.budgetservice.Responses.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service("BudgetServiceImplementation")
public class BudgetServiceImplementation implements BudgetService {
    @Autowired
    private BudgetRepository budgetRepository;
    @Autowired
    private BudgetEntryRepository budgetEntryRepository;

    @Autowired
    public BudgetServiceImplementation(BudgetRepository budgetRepository, BudgetEntryRepository budgetEntryRepository ) {
        this.budgetRepository = budgetRepository;
        this.budgetEntryRepository = budgetEntryRepository;
    }

    @Override

    public CreateBudgetResponse createBudget(UUID id,String name, String description,UUID creatorID, UUID adventureID) throws Exception {
        if(budgetRepository.findBudgetByBudgetID(id) != null){

            throw new Exception("Budget already exists.");
        }
        if (name == null) {
            throw new Exception("Budget name not provided.");
        }
        if (id == null) {
            throw new Exception("Budget ID not provided.");
        }

        budgetRepository.save(new Budget(id,name, description ,creatorID,adventureID));
        return new CreateBudgetResponse(true);
    }

    @Override
    public List<ViewBudgetResponse> viewBudget(UUID id) throws Exception {
        if (budgetRepository.findBudgetByBudgetIDAndDeletedEquals(id, false) == null) {
            throw new Exception("Budget does not exist.");
        }
        if (id == null) {
            throw new Exception("Budget ID was not provided.");
        }

        List<BudgetEntry> entries = budgetEntryRepository.findBudgetEntryByEntryContainerID(id);
        List<ViewBudgetResponse> list = new ArrayList<>();

        entries.sort(new Comparator<BudgetEntry>() {
            @Override
            public int compare(BudgetEntry o1, BudgetEntry o2) {
                return o1.getTimestamp().compareTo(o2.getTimestamp());
            }
        });

        for (BudgetEntry entry:entries) {
            if(entry instanceof UTUExpense){
                list.add(new ViewBudgetResponse(entry.getId(),entry.getEntryContainerID(),entry.getAmount(),entry.getTitle(),entry.getDescription(),entry.getCategory(),((UTUExpense) entry).getPayee()));
            }
            else{
                list.add(new ViewBudgetResponse(entry.getId(),entry.getEntryContainerID(),entry.getAmount(),entry.getTitle(),entry.getDescription(),entry.getCategory(),((UTOExpense) entry).getPayee()));
            }
        }

        return list;
    }

    @Override
//  @Transactional
    public AddUTUExpenseEntryResponse addUTUExpenseEntry(UUID id, UUID entryContainerID, double amount, String title, String description, Category category,List<String> payers, String payeeID) throws Exception {
        if(budgetRepository.findBudgetByBudgetID(entryContainerID) == null){

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
            throw new Exception("Entry already exists.");
        }


        BudgetEntry budgetEntry = new UTUExpense(id,entryContainerID,amount,title,description,category, payers, payeeID);

        budgetEntryRepository.save(budgetEntry);
        budgetRepository.save(budget);
        return new AddUTUExpenseEntryResponse(true);
    }


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

    public AddUTOExpenseEntryResponse addUTOExpenseEntry(UUID id, UUID entryContainerID, double amount, String title, String description,Category category,List<String> payers, String payee) throws Exception {
        if(budgetRepository.findBudgetByBudgetID(entryContainerID) == null){

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

        BudgetEntry budgetEntry = new UTOExpense(id,entryContainerID,amount,title,description,category,payers,payee);

        budgetEntryRepository.save(budgetEntry);
        budgetRepository.save(budget);
        return new AddUTOExpenseEntryResponse(true);
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
        if(req.getCategory() != null){
            entry.setCategory(req.getCategory());
        }
        if(req.getPayers() != null){
            entry.setPayers(req.getPayers());
        }

        if(entry instanceof UTUExpense){
            if(req.getPayee() != null && !req.getPayee().equals("")){
                ((UTUExpense) entry).setPayee(req.getPayee());
            }
        }
        else{
            if(req.getPayee() != null && !req.getPayee().equals("")){
                ((UTOExpense) entry).setPayee(req.getPayee());
            }
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

     * @param id
     *

     * @return HardDeleteResponse Object which will indicate whether
     * the request was successful or if an error occurred and return a message
     */
    @Override
    public HardDeleteResponse hardDelete(UUID id) throws Exception {
        if(id == null){
            throw new Exception("Budget ID not provided.");
        }

        Budget budget = budgetRepository.findBudgetByBudgetIDAndDeletedEquals(id,true);


        if (budget==null || !budget.isDeleted()) {
            throw new Exception("Budget is not in trash.");
        }

        List<BudgetEntry> entries = budgetEntryRepository.findBudgetEntryByEntryContainerID(id);

        budgetRepository.delete(budget);

        for (BudgetEntry b:entries) {
            budgetEntryRepository.delete(b);
        }

        return new HardDeleteResponse(true);
    }

    /**
     * @return ViewTrashResponse Object which will indicate whether
     * the request was successful or if an error occurred and return all Budget objects in the trash
     */
    @Override
    public List<BudgetResponseDTO> viewTrash(UUID id) throws Exception {

        List<Budget> budgets = budgetRepository.findAllByDeletedEquals(true);
        List<BudgetResponseDTO> list = new ArrayList<>();
        for (Budget b:budgets) {
            if(id == b.getAdventureID()){
                list.add(new BudgetResponseDTO(b.getBudgetId(),b.getName(),b.getCreatorID(),b.getAdventureID(),b.isDeleted(), b.getDescription()));

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
    public double calculateExpensesPerUser(UUID budgetID, String userName) throws Exception {
        if(budgetRepository.findBudgetByBudgetID(budgetID) == null){
            throw new Exception("Budget does not exist");

        }

        double sum = 0.0;
        List <BudgetEntry> entries = budgetEntryRepository.findBudgetEntryByEntryContainerID(budgetID);

        for (BudgetEntry entry:entries) {
            if(entry instanceof UTOExpense){
                if(entry.getPayers().contains(userName)){
                    sum += (entry.getAmount()/(entry.getPayers().size()));
                }
            }
            else if (entry instanceof UTUExpense){
                if(entry.getPayers().contains(userName)){
                    sum += (entry.getAmount()/(entry.getPayers().size()));
                }
                UTUExpense expense = (UTUExpense) entry;
                if(expense.getPayee().equals(userName)){
                    sum -= entry.getAmount();
                }
            }
        }

        return sum;
    }

    @Override
    public List<Integer> getEntriesPerCategory(UUID budgetID) throws Exception {
        if(budgetRepository.findBudgetByBudgetID(budgetID) == null){
            throw new Exception("Budget does not exist");
        }

        List<BudgetEntry> budgetEntries = budgetEntryRepository.findBudgetEntryByEntryContainerID(budgetID);
        List<Integer> integers = new ArrayList<>(List.of(0,0,0,0,0));


        for (BudgetEntry entry:budgetEntries) {
            if(entry.getCategory() == Category.Accommodation){
                integers.set(0,integers.get(0) + 1);
            }
            if(entry.getCategory() == Category.Activities){
                integers.set(1,integers.get(1) + 1);
            }
            if(entry.getCategory() == Category.Food){
                integers.set(2,integers.get(2) + 1);
            }
            if(entry.getCategory() == Category.Other){
                integers.set(3,integers.get(3) + 1);
            }
            if(entry.getCategory() == Category.Transport){
                integers.set(4,integers.get(4) + 1);

            }
        }

        return integers;
    }

    @Override
    public JSONObject generateReport(UUID id) throws Exception {
        if(budgetRepository.findBudgetByBudgetID(id) == null){
            throw new Exception("Budget does not exist");
        }

        List<String> usernames = getReportList(id);
        JSONObject jsonObject = new JSONObject();
        Collection<JSONObject> items = new ArrayList<JSONObject>();

        for (String name:usernames) {
            items.add(generateIndividualReport(name, id));
        }

        jsonObject.put("Report", new JSONArray(items));
        return jsonObject;
    }

    @Override
    public List<String> getReportList(UUID id) {
        List<BudgetEntry> entries = budgetEntryRepository.findBudgetEntryByEntryContainerID(id);
        List<String> names = new ArrayList<>();
        List<String> list = new ArrayList<>();

        for (BudgetEntry entry:entries) {
            names = entry.getPayers();
            for (String name:names) {
                if(!list.contains(name)){
                    list.add(name);
                }
            }
        }

        return list;
    }

    @Override
    public JSONObject generateIndividualReport(String userName, UUID id) throws JSONException {
        List<BudgetEntry> entries = budgetEntryRepository.findBudgetEntryByEntryContainerID(id);
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("User", userName);
        jsonObject.put("Payments",new JSONObject());

        for (BudgetEntry entry:entries) {

            if(entry instanceof UTOExpense){
                if(entry.getPayers().contains(userName)){
                    String payee = ((UTOExpense) entry).getPayee();
                    if(!jsonObject.getJSONObject("Payments").has(payee)){
                        jsonObject.getJSONObject("Payments").put(payee,(entry.getAmount()/entry.getPayers().size()));
                    }
                    else{
                        double temp = jsonObject.getJSONObject("Payments").getDouble(payee);
                        jsonObject.getJSONObject("Payments").put(payee, (temp + (entry.getAmount()/entry.getPayers().size())));
                    }
                }
            }
            else{
                if(entry.getPayers().contains(userName)){
                    String payee = ((UTUExpense) entry).getPayee();
                    if(!jsonObject.getJSONObject("Payments").has(payee)){
                        jsonObject.getJSONObject("Payments").put(payee,(entry.getAmount()/entry.getPayers().size()));
                    }
                    else{
                        double temp = jsonObject.getJSONObject("Payments").getDouble(payee);
                        jsonObject.getJSONObject("Payments").put(payee, (temp + (entry.getAmount()/entry.getPayers().size())));
                    }
                }
                else if(((UTUExpense) entry).getPayee().equals(userName)){
                    for (String payer:entry.getPayers()) {
                        if(!jsonObject.getJSONObject("Payments").has(payer)){
                            jsonObject.getJSONObject("Payments").put(payer,(-entry.getAmount()/entry.getPayers().size()));
                        }
                        else{
                            double temp = jsonObject.getJSONObject("Payments").getDouble(payer);
                            jsonObject.getJSONObject("Payments").put(payer, (temp - (entry.getAmount()/entry.getPayers().size())));
                        }
                    }
                }
            }
        }

        return jsonObject;
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


        BudgetEntry mockEntry1 = new UTUExpense(mockEntryID1,mockBudgetID1,200.0,"Mock Entry 1","Mock Entry", Category.Accommodation,new ArrayList<>(List.of("User Name 1")),"User Name 2");
        BudgetEntry mockEntry2 = new UTOExpense(mockEntryID2,mockBudgetID2,300.0,"Mock Entry 2","Mock Entry",Category.Transport,new ArrayList<>(List.of("User Name 3")),"Shuttle Service");
        BudgetEntry mockEntry3 = new UTOExpense(mockEntryID3,mockBudgetID3,600.0,"Mock Entry 3","Mock Entry",Category.Activities,new ArrayList<>(List.of("User Name 4")),"Paintball course");

        Budget budget1 = new Budget(mockBudgetID1, "Mock Budget 1", "Description for mock budget 1",mockCreatorID1,mockAdventureID1);
        Budget budget2 = new Budget(mockBudgetID2, "Mock Budget 2", "Description for mock budget 2",mockCreatorID2,mockAdventureID2);
        Budget budget3 = new Budget(mockBudgetID3, "Mock Budget 3", "Description for mock budget 3", mockCreatorID3,mockAdventureID3);


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


        Budget budget1 = new Budget(mockBudgetID1,"Mock Deleted Budget 1",  "Description for mock budget 1",UUID.randomUUID(),UUID.fromString("ad8e9b74-b4be-464e-a538-0cb78e9c2f8b"));
        Budget budget2 = new Budget(mockBudgetID2,"Mock Deleted Budget 2", "Description for mock budget 2",UUID.randomUUID(),UUID.fromString("7166264c-0874-42b6-8c82-d0df91e66375"));
        Budget budget3 = new Budget(mockBudgetID3,"Mock Deleted Budget 3", "Description for mock budget 3",UUID.randomUUID(),UUID.fromString("fe944b32-0102-499f-bbb6-1af673e8d6c3"));


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
