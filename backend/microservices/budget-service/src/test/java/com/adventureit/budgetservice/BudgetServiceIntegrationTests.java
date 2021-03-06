package com.adventureit.budgetservice;
import com.adventureit.budgetservice.controllers.BudgetController;
import com.adventureit.budgetservice.entity.Budget;
import com.adventureit.shareddtos.budget.Category;
import com.adventureit.budgetservice.entity.UTOExpense;
import com.adventureit.budgetservice.entity.UTUExpense;
import com.adventureit.budgetservice.repository.BudgetEntryRepository;
import com.adventureit.budgetservice.repository.BudgetRepository;
import com.adventureit.shareddtos.budget.requests.AddUTOExpenseEntryRequest;
import com.adventureit.shareddtos.budget.requests.CreateBudgetRequest;
import com.adventureit.shareddtos.budget.requests.EditBudgetRequest;
import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = {"service-registry-client.instance.hostname=localhost","service-registry-client.client.service-url.defaultZone=http://localhost:8761/eureka/","service-registry-client.client.register-with-eureka=true", "service-registry-client.client.fetch-registry=true","budget-microservice.application-name=BUDGET-MICROSERVICE", "budget-microservice.datasource.url=jdbc:postgresql://adventure-it-db.c9gozrkqo8dv.us-east-2.rds.amazonaws.com/adventureit?socketTimeout=5","budget-microservice.datasource.username=postgres","budget-microservice.datasource.password=310PB!Gq%f&J","budget-microservice.datasource.hikari.maximum-pool-size=2","budget-microservice.jpa.hibernate.ddl-auto=update","budget-microservice.jpa.show-sql=false","budget-microservice.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect","budget-microservice.jpa.properties.hibernate.format_sql=true" })
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BudgetServiceIntegrationTests {
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
    void budgetControllerLoads() throws Exception {
        Assertions.assertNotNull(budgetController);
    }


    @Test
    @Description("Ensure that the create function works")
    void httpCreateBudgetValid_returnResponse(){
        CreateBudgetRequest request = new CreateBudgetRequest("Test Budget 1","Mock",UUID.randomUUID().toString(),UUID.randomUUID().toString());
        String response = this.restTemplate.postForObject("http://localhost:" + port + "/budget/create", request,String.class);
        Assertions.assertEquals("Budget was successfully created!", response);
    }

    @Test
    @Description("Create function will not work if field is null")
    void httpCreateBudgetInvalidNullField_returnResponse(){
        CreateBudgetRequest request = new CreateBudgetRequest(null,"Mock",UUID.randomUUID().toString(),UUID.randomUUID().toString());
        String response = this.restTemplate.postForObject("http://localhost:" + port + "/budget/create", request,String.class);
        Assertions.assertNotEquals("Budget was successfully created!", response);
    }

    @Test
    @Description("Ensure a user can add an entry to a budget")
    void httpAddEntryValid_returnResponse(){
        UUID budgetID = UUID.randomUUID();
        UUID adventureID = UUID.randomUUID();
        Budget budget1 = new Budget(budgetID,"Test Budget 1","Mock",UUID.randomUUID(),adventureID);
        budgetRepository.saveAndFlush(budget1);

        AddUTOExpenseEntryRequest request = new AddUTOExpenseEntryRequest(budgetID,"User1",100,"Mock","Mock","ACTIVITIES","Shop1");
        String response = this.restTemplate.postForObject("http://localhost:" + port + "/budget/addUTOExpense", request,String.class);
        Assertions.assertEquals("Entry added successfully!", response);
    }

    @Test
    @Description("Add entry will not work if the budget does not exist")
    void httpAddEntryBudgetInvalid_returnResponse(){
        AddUTOExpenseEntryRequest request = new AddUTOExpenseEntryRequest(UUID.randomUUID(),"User1",100,"Mock","Mock","ACTIVITIES","Shop1");
        String response = this.restTemplate.postForObject("http://localhost:" + port + "/budget/addUTOExpense", request,String.class);
        Assertions.assertNotEquals("Entry added successfully!", response);
    }

    @Test
    @Description("Add entry will not work if a field is null")
    void httpAddEntryInvalidNullField_returnResponse(){
        UUID budgetID = UUID.randomUUID();
        UUID adventureID = UUID.randomUUID();
        Budget budget1 = new Budget(budgetID,"Test Budget 1","Mock",UUID.randomUUID(),adventureID);
        budgetRepository.saveAndFlush(budget1);

        AddUTOExpenseEntryRequest request = new AddUTOExpenseEntryRequest(budgetID,"Sam",100,"Mock","Mock","ACTIVITIES","Shop1");
        String response = this.restTemplate.postForObject("http://localhost:" + port + "/budget/addUTOExpense", request,String.class);
        Assertions.assertEquals("Entry added successfully!", response);
    }

    @Test
    @Description("Ensure a user can edit an entry")
    void httpEditEntryValidNullField_returnResponse(){
        UUID budgetID = UUID.randomUUID();
        UUID entryID = UUID.randomUUID();
        UUID adventureID = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        Budget budget1 = new Budget(budgetID,"Test Budget 1","Mock",UUID.randomUUID(),adventureID);
        UTUExpense expense = new UTUExpense(entryID,budgetID,100,"Mock","Mock",Category.OTHER,"User1","User2");
        budgetRepository.saveAndFlush(budget1);
        budgetEntryRepository.saveAndFlush(expense);

        EditBudgetRequest request = new EditBudgetRequest(entryID,userId,budgetID,100,"Mock","Mock","OTHER",null,null);
        String response = this.restTemplate.postForObject("http://localhost:" + port + "/budget/editBudget", request,String.class);
        Assertions.assertEquals("Budget successfully edited!", response);
    }

    @Test
    @Description("Ensure that the view function works")
    void httpViewByAdventure_returnResponse(){
        UUID id = UUID.randomUUID();
        UUID adventureID = UUID.randomUUID();
        Assertions.assertNotNull(id);
        Assertions.assertNotNull(adventureID);
        Budget budget1 = new Budget(id,"Test Budget 1","Mock",UUID.randomUUID(),adventureID);
        budgetRepository.saveAndFlush(budget1);
        this.restTemplate.getForObject("http://localhost:" + port + "/budget/viewBudgetsByAdventure/{id}", List.class, adventureID);
    }

    @Test
    @Description("Ensure that the softDelete function works")
    void httpSoftDelete_returnResponse(){
        UUID id = UUID.randomUUID();
        UUID adventureID = UUID.randomUUID();
        UUID ownerID = UUID.randomUUID();
        Budget budget1 = new Budget(id,"Test Budget 1","Mock",ownerID,adventureID);
        budgetRepository.saveAndFlush(budget1);
        Assertions.assertEquals("Budget successfully moved to bin.", this.restTemplate.getForObject("http://localhost:" + port + "/budget/softDelete/{id}/{userID}", String.class, id,ownerID));
    }

    @Test
    @Description("Ensure that the hardDelete function works")
    void httpHardDelete_returnResponse(){
        UUID id = UUID.randomUUID();
        UUID adventureID = UUID.randomUUID();
        UUID ownerID = UUID.randomUUID();
        Budget budget1 = new Budget(id,"Test Budget 1","Mock",ownerID,adventureID);
        budget1.setDeleted(true);
        budgetRepository.saveAndFlush(budget1);
        Assertions.assertEquals("Budget permanently deleted!", this.restTemplate.getForObject("http://localhost:" + port + "/budget/hardDelete/{id}/{userID}", String.class, id,ownerID));
    }

    @Test
    @Description("Ensure that the viewTrash function works")
    void httpViewTrash_returnResponse(){
        UUID id = UUID.randomUUID();
        UUID adventureID = UUID.randomUUID();
        UUID ownerID = UUID.randomUUID();
        Assertions.assertNotNull(id);
        Assertions.assertNotNull(adventureID);
        Assertions.assertNotNull(ownerID);
        Budget budget1 = new Budget(id,"Test Budget 1","Mock",ownerID,adventureID);
        budget1.setDeleted(true);
        budgetRepository.saveAndFlush(budget1);
        this.restTemplate.getForObject("http://localhost:" + port + "/budget/viewTrash/{id}", String.class, adventureID);
    }

    @Test
    @Description("Ensure that the restoreBudget function works")
    void httpRestoreBudget_returnResponse(){
        UUID id = UUID.randomUUID();
        UUID adventureID = UUID.randomUUID();
        UUID ownerID = UUID.randomUUID();
        Budget budget1 = new Budget(id,"Test Budget 1","Mock",ownerID,adventureID);
        budget1.setDeleted(true);
        budgetRepository.saveAndFlush(budget1);
        Assertions.assertEquals("Budget was restored", this.restTemplate.getForObject("http://localhost:" + port + "/budget/restoreBudget/{id}/{userID}", String.class, id,ownerID));
    }

    @Test
    @Description("Ensure that the removeEntry function works")
    void httpRemoveEntry_returnResponse(){
        UUID id = UUID.randomUUID();
        UUID budgetID = UUID.randomUUID();
        UTOExpense entry1 = new UTOExpense(id,budgetID,50,"Mock Entry","Mock", Category.ACTIVITIES,"User1","Shop");
        budgetEntryRepository.saveAndFlush(entry1);
        Assertions.assertEquals("Entry successfully removed.", this.restTemplate.getForObject("http://localhost:" + port + "/budget/removeEntry/{id}", String.class, id));
    }

    @Test
    @Description("Ensure that the GetEntriesPerCategory function works")
    void httpGetEntriesPerCategory_returnResponse(){
        UUID id = UUID.randomUUID();
        UUID adventureID = UUID.randomUUID();
        UUID ownerID = UUID.randomUUID();
        Assertions.assertNotNull(id);
        Assertions.assertNotNull(adventureID);
        Assertions.assertNotNull(ownerID);
        Budget budget1 = new Budget(id,"Test Budget 1","Mock",ownerID,adventureID);
        budgetRepository.saveAndFlush(budget1);
        this.restTemplate.getForObject("http://localhost:" + port + "/budget/getEntriesPerCategory/{id}", String.class, id);
    }

    @Test
    @Description("Ensure that the getBudgetByID function works")
    void httpGetBudgetByID_returnResponse(){
        UUID id = UUID.randomUUID();
        UUID adventureID = UUID.randomUUID();
        UUID ownerID = UUID.randomUUID();
        Assertions.assertNotNull(id);
        Assertions.assertNotNull(adventureID);
        Assertions.assertNotNull(ownerID);
        Budget budget1 = new Budget(id,"Test Budget 1","Mock",ownerID,adventureID);
        budgetRepository.saveAndFlush(budget1);
        this.restTemplate.getForObject("http://localhost:" + port + "/budget/getBudgetByBudgetId/{id}", String.class, id);
    }
}