package com.adventureit.budgetservice.service;


import com.adventureit.budgetservice.entity.*;
import com.adventureit.budgetservice.exception.*;
import com.adventureit.budgetservice.graph.BudgetGraph;
import com.adventureit.budgetservice.graph.Edge;
import com.adventureit.budgetservice.repository.BudgetEntryRepository;
import com.adventureit.budgetservice.repository.BudgetRepository;
import com.adventureit.shareddtos.budget.Category;
import com.adventureit.shareddtos.budget.requests.EditBudgetRequest;
import com.adventureit.shareddtos.budget.requests.SoftDeleteRequest;
import com.adventureit.shareddtos.budget.responses.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service("BudgetServiceImplementation")
public class BudgetServiceImplementation implements BudgetService {
    private final BudgetRepository budgetRepository;
    private final BudgetEntryRepository budgetEntryRepository;

    public BudgetServiceImplementation(BudgetRepository budgetRepository, BudgetEntryRepository budgetEntryRepository ) {
        this.budgetRepository = budgetRepository;
        this.budgetEntryRepository = budgetEntryRepository;
    }

    private static final String BUDGET_ID_NOT_PROVIDED = "Budget ID not provided";
    private static final String BUDGET_PERMISSION = "User is not creator of budget";
    private static final String NOT_FOUND = " not found";


    @Override

    public CreateBudgetResponse createBudget(String name, String description, UUID creatorID, UUID adventureID) {
        if (name == null) {
            throw new MalformedBudgetRequestException("Budget name not provided.");
        }

        budgetRepository.save(new Budget(name, description ,creatorID,adventureID));
        return new CreateBudgetResponse(true);
    }

    @Override
    public List<ViewBudgetResponse> viewBudget(UUID id) {
        if (budgetRepository.findBudgetByBudgetIDAndDeletedEquals(id, false) == null) {
            throw new BudgetNotFoundException("Budget does not exist.");
        }
        if (id == null) {
            throw new MalformedBudgetRequestException("No budget ID was provided");
        }


        List<BudgetEntry> entries = budgetEntryRepository.findBudgetEntryByEntryContainerID(id);
        List<ViewBudgetResponse> list = new ArrayList<>();

        entries.sort(Comparator.comparing(BudgetEntry::getTimestamp));

        for (BudgetEntry entry:entries) {
            if(entry instanceof UTUExpense){
                list.add(new ViewBudgetResponse(entry.getId(),entry.getEntryContainerID(),entry.getPayer(),entry.getAmount(),entry.getTitle(),entry.getDescription(),entry.getCategory(),((UTUExpense) entry).getPayee()));
            }
            else{
                list.add(new ViewBudgetResponse(entry.getId(),entry.getEntryContainerID(),entry.getPayer(),entry.getAmount(),entry.getTitle(),entry.getDescription(),entry.getCategory(),((UTOExpense) entry).getPayee()));
            }
        }

        return list;
    }

    @Override
    @Transactional
    public AddUTUExpenseEntryResponse addUTUExpenseEntry(UUID entryContainerID, double amount, String title, String description, Category category, String payer, String payeeID) {
        verifyBudgetRequestForm(entryContainerID, amount, title, description);

        Budget budget = budgetRepository.findBudgetByBudgetID(entryContainerID);

        BudgetEntry budgetEntry = new UTUExpense(entryContainerID,amount,title,description,category, payer, payeeID);

        budgetEntryRepository.save(budgetEntry);
        budgetRepository.save(budget);
        List <BudgetEntry> list = budgetEntryRepository.findBudgetEntryByEntryContainerID(entryContainerID);
        BudgetGraph graph = new BudgetGraph();
        graph.generateGraph(list);
        List<Edge> list2 = graph.summarizeGraph();
        for (int i =0 ; i<list2.size();i++){
            if(list2.get(i).getAmount()==0){
                budgetEntryRepository.removeBudgetEntryByBudgetEntryID(list2.get(i).getEntryId());
            }else{
                BudgetEntry entry = budgetEntryRepository.findBudgetEntryByBudgetEntryID(list2.get(i).getEntryId());
                entry.setAmount(list2.get(i).getAmount());
                budgetEntryRepository.save(entry);
            }
        }
        return new AddUTUExpenseEntryResponse(true);
    }


    @Override
    public RemoveEntryResponse removeEntry(UUID id) {
        if (id == null) {
            throw new MalformedBudgetRequestException("Entry ID not provided.");
        }

        BudgetEntry entry = budgetEntryRepository.findBudgetEntryByBudgetEntryID(id);
        if (entry == null) {
            throw new BudgetEntryNotFoundException("Budget Entry with ID " + id + NOT_FOUND);
        }

        budgetEntryRepository.delete(entry);
        return new RemoveEntryResponse(true);
    }

    @Override
    public BudgetResponseDTO getBudgetByBudgetEntryId(UUID budgetId){
        BudgetEntry entry = budgetEntryRepository.findBudgetEntryByBudgetEntryID(budgetId);
        Budget budget = budgetRepository.findBudgetByBudgetID(entry.getEntryContainerID());
        return new BudgetResponseDTO(budget.getBudgetId(),budget.getName(),budget.getCreatorID(),budget.getAdventureID(),budget.isDeleted(),budget.getDescription());
    }

    @Override
    public BudgetResponseDTO getBudgetByBudgetContainerId(UUID budgetId){
        Budget budget = budgetRepository.findBudgetByBudgetID(budgetId);
        return new BudgetResponseDTO(budget.getBudgetId(),budget.getName(),budget.getCreatorID(),budget.getAdventureID(),budget.isDeleted(),budget.getDescription());
    }

    @Override
    @Transactional
    public AddUTOExpenseEntryResponse addUTOExpenseEntry(UUID entryContainerID, double amount, String title, String description,Category category,String payer, String payee) {
        verifyBudgetRequestForm(entryContainerID, amount, title, description);

        Budget budget = budgetRepository.findBudgetByBudgetID(entryContainerID);

        BudgetEntry budgetEntry = new UTOExpense(entryContainerID,amount,title,description,category,payer,payee);

        budgetEntryRepository.save(budgetEntry);
        budgetRepository.save(budget);
        List <BudgetEntry> list = budgetEntryRepository.findBudgetEntryByEntryContainerID(entryContainerID);
        BudgetGraph graph = new BudgetGraph();
        graph.generateGraph(list);
        List<Edge> list2 = graph.summarizeGraph();
        for (int i =0 ; i<list2.size();i++){
            if(list2.get(i).getAmount()==0){
                budgetEntryRepository.removeBudgetEntryByBudgetEntryID(list2.get(i).getEntryId());
            }else{
                BudgetEntry entry = budgetEntryRepository.findBudgetEntryByBudgetEntryID(list2.get(i).getEntryId());
                entry.setAmount(list2.get(i).getAmount());
                budgetEntryRepository.save(entry);
            }
        }

        return new AddUTOExpenseEntryResponse(true);
    }

    /**
     * @param req EditBudgetRequest object
     * @return EditBudgetResponse Object which will indicate whether
     * the request was successful or if an error occurred and return a message
     */
    @Override
    public EditBudgetResponse editBudget(EditBudgetRequest req) {
        if (budgetRepository.findBudgetByBudgetID(req.getBudgetID()) == null) {
            throw new BudgetNotFoundException(getBudgetNotFoundExceptionString(req.getBudgetID().toString()));
        }
        else if (req.getId() == null) {
            throw new MalformedBudgetRequestException("Entry ID not provided.");
        }
        if (req.getBudgetID() == null) {
            throw new MalformedBudgetRequestException(BUDGET_ID_NOT_PROVIDED);
        }
        if (req.getTitle() == null) {
            throw new MalformedBudgetRequestException("Title Field is null.");
        }
        if (req.getDescription() == null) {
            throw new MalformedBudgetRequestException("Description Field is null.");
        }

        BudgetEntry entry = budgetEntryRepository.findBudgetEntryByBudgetEntryID(req.getId());

        if (entry == null) {
            throw new BudgetEntryNotFoundException("Entry for Budget with id " + req.getId().toString() + NOT_FOUND);
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
        if(req.getPayer() != null && !req.getPayer().equals("")){
            entry.setPayer(req.getPayer());
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
    public SoftDeleteResponse softDelete(SoftDeleteRequest req) {
        if (req.getId() == null) {
            throw new MalformedBudgetRequestException("Budget ID not provided.");
        }

        Budget budget = budgetRepository.findBudgetByBudgetIDAndDeletedEquals(req.getId(), false);

        if (budget == null) {
            throw new BudgetNotFoundException(getBudgetNotFoundExceptionString(req.getId().toString()));
        }

        if(!req.getUserID().equals(budget.getCreatorID())){
            throw new BudgetPermissionException(BUDGET_PERMISSION);
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
    public HardDeleteResponse hardDelete(UUID id, UUID userID) {
        if(id == null){
            throw new MalformedBudgetRequestException("Budget ID not provided.");
        }

        Budget budget = budgetRepository.findBudgetByBudgetIDAndDeletedEquals(id,true);

        if (budget==null || !budget.isDeleted()) {
            throw new BudgetNotInTrashException("Budget is not in trash.");
        }
        if(!userID.equals(budget.getCreatorID())){
            throw new BudgetPermissionException(BUDGET_PERMISSION);
        }

        List<BudgetEntry> entries = budgetEntryRepository.findBudgetEntryByEntryContainerID(id);

        budgetRepository.delete(budget);

        budgetEntryRepository.deleteAll(entries);

        return new HardDeleteResponse(true);
    }

    /**
     * @return ViewTrashResponse Object which will indicate whether
     * the request was successful or if an error occurred and return all Budget objects in the trash
     */
    @Override
    public List<BudgetResponseDTO> viewTrash(UUID id) {

        List<Budget> budgets = budgetRepository.findAllByAdventureID(id);
        if(budgets.isEmpty()){
            throw new BudgetNotFoundException(getBudgetNotFoundExceptionString(id.toString()));
        }
        List<BudgetResponseDTO> list = new ArrayList<>();
        for (Budget b:budgets) {
            if(b.isDeleted()){
                list.add(new BudgetResponseDTO(b.getBudgetId(),b.getName(),b.getCreatorID(),b.getAdventureID(),b.isDeleted(), b.getDescription()));
            }
        }
        return list;
    }


    public String restoreBudget(UUID id,UUID userID) {
        if (budgetRepository.findBudgetByBudgetID(id) == null) {
            throw new BudgetNotFoundException(getBudgetNotFoundExceptionString(id.toString()));
        }

        Budget budget = budgetRepository.findBudgetByBudgetID(id);

        if(!budget.getCreatorID().equals(userID)){
            throw new BudgetPermissionException(BUDGET_PERMISSION);
        }

        budget.setDeleted(false);
        budgetRepository.save(budget);
        return "Budget was restored";
    }

    @Override
    public double calculateExpensesPerUser(UUID budgetID, String userName) {
        if(budgetRepository.findBudgetByBudgetID(budgetID) == null){
            throw new BudgetEntryNotFoundException(getBudgetNotFoundExceptionString(budgetID.toString()));

        }

        double sum = 0.0;
        List <BudgetEntry> entries = budgetEntryRepository.findBudgetEntryByEntryContainerID(budgetID);

        for (BudgetEntry entry:entries) {
            if(entry instanceof UTOExpense){
                if(entry.getPayer().equals(userName)){
                    sum += entry.getAmount();
                }
            }
            else if (entry instanceof UTUExpense){
                if(entry.getPayer().equals(userName)){
                    sum += entry.getAmount();
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
    public List<Integer> getEntriesPerCategory(UUID adventureID) {
        List<Budget> budgets = budgetRepository.findAllByAdventureID(adventureID);
        List<BudgetEntry> budgetEntries = new ArrayList<>();
        List<BudgetEntry> temp;
        List<Integer> integers = new ArrayList<>(List.of(0,0,0,0,0));

        for (Budget budget:budgets) {
            if(!budget.isDeleted()){
                temp = budgetEntryRepository.findBudgetEntryByEntryContainerID(budget.getBudgetId());
                if(temp != null){
                    budgetEntries.addAll(temp);
                }
            }
        }

        if(budgetEntries.isEmpty()){
            for (BudgetEntry entry:budgetEntries) {
                if(entry.getCategory() == Category.ACCOMMODATION){
                    integers.set(0,integers.get(0) + 1);
                }
                if(entry.getCategory() == Category.ACTIVITIES){
                    integers.set(1,integers.get(1) + 1);
                }
                if(entry.getCategory() == Category.FOOD){
                    integers.set(2,integers.get(2) + 1);
                }
                if(entry.getCategory() == Category.OTHER){
                    integers.set(3,integers.get(3) + 1);
                }
                if(entry.getCategory() == Category.TRANSPORT){
                    integers.set(4,integers.get(4) + 1);

                }
            }
        }

        return integers;
    }

    @Override
    public List<String> getReportList(UUID id) {
        List<BudgetEntry> entries = budgetEntryRepository.findBudgetEntryByEntryContainerID(id);
        List<String> list = new ArrayList<>();

        for (BudgetEntry entry:entries) {
            if(!list.contains(entry.getPayer())) {
                list.add(entry.getPayer());
            }
        }

        return list;
    }

    @Override
    public List<ReportResponseDTO> generateIndividualReport(String userName, UUID id) throws JSONException {
        List<BudgetEntry> entries = budgetEntryRepository.findBudgetEntryByEntryContainerID(id);
        JSONObject jsonObject = new JSONObject();

        for (BudgetEntry entry:entries) {

            if(entry instanceof UTOExpense){
                if(entry.getPayer().equals(userName)){
                    String payee = ((UTOExpense) entry).getPayee();
                    if(!jsonObject.has(payee)){
                        jsonObject.put(payee,entry.getAmount());
                    }
                    else{
                        double temp = jsonObject.getDouble(payee);
                        jsonObject.put(payee, (temp + entry.getAmount()));
                    }
                }
            }
            else{
                if(entry.getPayer().equals(userName)){
                    String payee = ((UTUExpense) entry).getPayee();
                    if(!jsonObject.has(payee)){
                        jsonObject.put(payee,entry.getAmount());
                    }
                    else{
                        double temp = jsonObject.getDouble(payee);
                        jsonObject.put(payee, (temp + entry.getAmount()));
                    }
                }
                else if(((UTUExpense) entry).getPayee().equals(userName)){
                    if(!jsonObject.has(entry.getPayer())){
                        jsonObject.put(entry.getPayer(),(-entry.getAmount()));
                    }
                    else{
                        double temp = jsonObject.getDouble(entry.getPayer());
                        jsonObject.put(entry.getPayer(), (temp - entry.getAmount()));
                    }
                }
            }
        }

        List<ReportResponseDTO> list = new ArrayList<>();

        for (Iterator<Object> it = jsonObject.keys(); it.hasNext(); ) {
            Object key = it.next();
            Object value = jsonObject.get(key.toString());
            list.add(new ReportResponseDTO(key.toString(),Double.parseDouble(value.toString())));
        }

        return list;
    }



    private String getBudgetNotFoundExceptionString(String id){
        return "Budget with ID " + id + NOT_FOUND;
    }

    private void verifyBudgetRequestForm(UUID entryContainerID, double amount, String title, String description){
        if(budgetRepository.findBudgetByBudgetID(entryContainerID) == null){

            throw new BudgetNotFoundException(getBudgetNotFoundExceptionString(entryContainerID.toString()));
        }
        if (entryContainerID == null) {
            throw new MalformedBudgetRequestException(BUDGET_ID_NOT_PROVIDED);
        }
        if (amount == 0.0) {
            throw new MalformedBudgetRequestException("Amount not provided");
        }
        if (title == null || title.equals("")) {
            throw new MalformedBudgetRequestException("Title not provided");
        }
        if (description == null || description.equals("")) {
            throw new MalformedBudgetRequestException("Description not provided");
        }
    }

}
