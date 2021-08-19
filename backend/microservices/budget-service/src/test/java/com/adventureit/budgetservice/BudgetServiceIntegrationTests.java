package com.adventureit.budgetservice;
import com.adventureit.budgetservice.Controllers.BudgetController;
import com.adventureit.budgetservice.Entity.Budget;
import com.adventureit.budgetservice.Entity.Category;
import com.adventureit.budgetservice.Entity.UTOExpense;
import com.adventureit.budgetservice.Entity.UTUExpense;
import com.adventureit.budgetservice.Repository.BudgetEntryRepository;
import com.adventureit.budgetservice.Repository.BudgetRepository;
import com.adventureit.budgetservice.Requests.AddUTOExpenseEntryRequest;
import com.adventureit.budgetservice.Requests.CreateBudgetRequest;
import com.adventureit.budgetservice.Requests.EditBudgetRequest;
import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BudgetServiceIntegrationTests {
    @Autowired
    private BudgetController budgetController;
    @Autowired
    private BudgetRepository budgetRepository;
    @Autowired
    private BudgetEntryRepository budgetEntryRepository;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Description("Ensure that the Budget Controller loads")
    public void budgetControllerLoads() throws Exception {
        Assertions.assertNotNull(budgetController);
    }

    @Test
    @Description("Ensure that the controller is accepting traffic and responding")
    public void httpTest_returnResponse(){
        Assertions.assertEquals(this.restTemplate.getForObject("http://localhost:" + port + "/budget/test", String.class),"Budget Controller is functioning");
    }

    @Test
    @Description("Ensure that the create function works")
    public void httpCreateBudgetValid_returnResponse(){
        CreateBudgetRequest request = new CreateBudgetRequest("Test Budget 1","Mock",UUID.randomUUID().toString(),UUID.randomUUID().toString());
        String response = this.restTemplate.postForObject("http://localhost:" + port + "/budget/create", request,String.class);
        Assertions.assertEquals(response,"Budget was successfully created!" );
    }

    @Test
    @Description("Create function will not work if field is null")
    public void httpCreateBudgetInvalidNullField_returnResponse(){
        CreateBudgetRequest request = new CreateBudgetRequest(null,"Mock",UUID.randomUUID().toString(),UUID.randomUUID().toString());
        String response = this.restTemplate.postForObject("http://localhost:" + port + "/budget/create", request,String.class);
        Assertions.assertNotEquals(response,"Budget was successfully created!");
    }

    @Test
    @Description("Ensure a user can add an entry to a budget")
    public void httpAddEntryValid_returnResponse(){
        UUID budgetID = UUID.randomUUID();
        UUID adventureID = UUID.randomUUID();
        Budget budget1 = new Budget(budgetID,"Test Budget 1","Mock",UUID.randomUUID(),adventureID);
        budgetRepository.saveAndFlush(budget1);

        AddUTOExpenseEntryRequest request = new AddUTOExpenseEntryRequest(budgetID,"User1",100,"Mock","Mock","Activities","Shop1");
        String response = this.restTemplate.postForObject("http://localhost:" + port + "/budget/addUTOExpense", request,String.class);
        Assertions.assertEquals(response,"Entry added successfully!");
    }

    @Test
    @Description("Add entry will not work if the budget does not exist")
    public void httpAddEntryBudgetInvalid_returnResponse(){
        AddUTOExpenseEntryRequest request = new AddUTOExpenseEntryRequest(UUID.randomUUID(),"User1",100,"Mock","Mock","Activities","Shop1");
        String response = this.restTemplate.postForObject("http://localhost:" + port + "/budget/addUTOExpense", request,String.class);
        Assertions.assertNotEquals(response,"Entry added successfully!");
    }

    @Test
    @Description("Add entry will not work if a field is null")
    public void httpAddEntryInvalidNullField_returnResponse(){
        UUID budgetID = UUID.randomUUID();
        UUID adventureID = UUID.randomUUID();
        Budget budget1 = new Budget(budgetID,"Test Budget 1","Mock",UUID.randomUUID(),adventureID);
        budgetRepository.saveAndFlush(budget1);

        AddUTOExpenseEntryRequest request = new AddUTOExpenseEntryRequest(budgetID,null,100,"Mock","Mock","Activities","Shop1");
        String response = this.restTemplate.postForObject("http://localhost:" + port + "/budget/addUTOExpense", request,String.class);
        Assertions.assertEquals(response,"Entry added successfully!");
    }

    @Test
    @Description("Ensure a user can edit an entry")
    public void httpEditEntryValidNullField_returnResponse(){
        UUID budgetID = UUID.randomUUID();
        UUID entryID = UUID.randomUUID();
        UUID adventureID = UUID.randomUUID();
        Budget budget1 = new Budget(budgetID,"Test Budget 1","Mock",UUID.randomUUID(),adventureID);
        UTUExpense expense = new UTUExpense(entryID,budgetID,100,"Mock","Mock",Category.Other,"User1","User2");
        budgetRepository.saveAndFlush(budget1);
        budgetEntryRepository.saveAndFlush(expense);

        EditBudgetRequest request = new EditBudgetRequest(entryID,budgetID,100,"Mock","Mock",null,null);
        String response = this.restTemplate.postForObject("http://localhost:" + port + "/budget/editBudget", request,String.class);
        Assertions.assertEquals(response,"Budget successfully edited!");
    }

    @Test
    @Description("Ensure that the view function works")
    public void httpViewByAdventure_returnResponse(){
        UUID id = UUID.randomUUID();
        UUID adventureID = UUID.randomUUID();
        Budget budget1 = new Budget(id,"Test Budget 1","Mock",UUID.randomUUID(),adventureID);
        budgetRepository.saveAndFlush(budget1);
        this.restTemplate.getForObject("http://localhost:" + port + "/budget/viewBudgetsByAdventure/{id}", List.class, id);
    }

    @Test
    @Description("Ensure that the softDelete function works")
    public void httpSoftDelete_returnResponse(){
        UUID id = UUID.randomUUID();
        UUID adventureID = UUID.randomUUID();
        UUID ownerID = UUID.randomUUID();
        Budget budget1 = new Budget(id,"Test Budget 1","Mock",ownerID,adventureID);
        budgetRepository.saveAndFlush(budget1);
        Assertions.assertEquals(this.restTemplate.getForObject("http://localhost:" + port + "/budget/softDelete/{id}/{userID}", String.class, id,ownerID), "Budget successfully moved to bin.");
    }

    @Test
    @Description("Ensure that the hardDelete function works")
    public void httpHardDelete_returnResponse(){
        UUID id = UUID.randomUUID();
        UUID adventureID = UUID.randomUUID();
        UUID ownerID = UUID.randomUUID();
        Budget budget1 = new Budget(id,"Test Budget 1","Mock",ownerID,adventureID);
        budget1.setDeleted(true);
        budgetRepository.saveAndFlush(budget1);
        Assertions.assertEquals(this.restTemplate.getForObject("http://localhost:" + port + "/budget/hardDelete/{id}/{userID}", String.class, id,ownerID), "Budget permanently deleted!");
    }

    @Test
    @Description("Ensure that the viewTrash function works")
    public void httpViewTrash_returnResponse(){
        UUID id = UUID.randomUUID();
        UUID adventureID = UUID.randomUUID();
        UUID ownerID = UUID.randomUUID();
        Budget budget1 = new Budget(id,"Test Budget 1","Mock",ownerID,adventureID);
        budget1.setDeleted(true);
        budgetRepository.saveAndFlush(budget1);
        this.restTemplate.getForObject("http://localhost:" + port + "/budget/viewTrash/{id}", String.class, adventureID);
    }

    @Test
    @Description("Ensure that the restoreBudget function works")
    public void httpRestoreBudget_returnResponse(){
        UUID id = UUID.randomUUID();
        UUID adventureID = UUID.randomUUID();
        UUID ownerID = UUID.randomUUID();
        Budget budget1 = new Budget(id,"Test Budget 1","Mock",ownerID,adventureID);
        budget1.setDeleted(true);
        budgetRepository.saveAndFlush(budget1);
        Assertions.assertEquals(this.restTemplate.getForObject("http://localhost:" + port + "/budget/restoreBudget/{id}/{userID}", String.class, id,ownerID), "Budget was restored");
    }

    @Test
    @Description("Ensure that the removeEntry function works")
    public void httpRemoveEntry_returnResponse(){
        UUID id = UUID.randomUUID();
        UUID budgetID = UUID.randomUUID();
        UTOExpense entry1 = new UTOExpense(id,budgetID,50,"Mock Entry","Mock", Category.Activities,"User1","Shop");
        budgetEntryRepository.saveAndFlush(entry1);
        Assertions.assertEquals(this.restTemplate.getForObject("http://localhost:" + port + "/budget/removeEntry/{id}", String.class, id), "Entry successfully removed.");
    }

    @Test
    @Description("Ensure that the GetEntriesPerCategory function works")
    public void httpGetEntriesPerCategory_returnResponse(){
        UUID id = UUID.randomUUID();
        UUID adventureID = UUID.randomUUID();
        UUID ownerID = UUID.randomUUID();
        Budget budget1 = new Budget(id,"Test Budget 1","Mock",ownerID,adventureID);
        budgetRepository.saveAndFlush(budget1);
        this.restTemplate.getForObject("http://localhost:" + port + "/budget/getEntriesPerCategory/{id}", String.class, id);
    }

    @Test
    @Description("Ensure that the getBudgetByID function works")
    public void httpGetBudgetByID_returnResponse(){
        UUID id = UUID.randomUUID();
        UUID adventureID = UUID.randomUUID();
        UUID ownerID = UUID.randomUUID();
        Budget budget1 = new Budget(id,"Test Budget 1","Mock",ownerID,adventureID);
        budgetRepository.saveAndFlush(budget1);
        this.restTemplate.getForObject("http://localhost:" + port + "/budget/getBudgetByBudgetId/{id}", String.class, id);
    }
}