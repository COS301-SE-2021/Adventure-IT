package com.adventureit.budgetservice;
import com.adventureit.budgetservice.Controllers.BudgetController;
import com.adventureit.budgetservice.Entity.Budget;
import com.adventureit.budgetservice.Entity.BudgetEntry;
import com.adventureit.budgetservice.Repository.BudgetRepository;
import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.ArrayList;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BudgetServiceIntegrationTests {
    @Autowired
    private BudgetController budgetController;
    @Autowired
    private BudgetRepository budgetRepository;

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
    @Description("Ensure that the populate function works")
    public void httpPopulate_returnResponse(){
        Assertions.assertEquals(this.restTemplate.getForObject("http://localhost:" + port + "/budget/populate", String.class),"Mock budgets populated \n");
    }

    @Test
    @Description("Ensure that the create function works")
    public void httpCreate_returnResponse(){
        Assertions.assertEquals(this.restTemplate.getForObject("http://localhost:" + port + "/budget/mockCreate/{name}", String.class,"Budget 1"),"Budget Successfully created");
    }

//    @Test
//    @Description("Ensure that the create function works")
//    public void httpView_returnResponse(){
//        UUID budgetID = UUID.randomUUID();
//        Assertions.assertEquals(this.restTemplate.getForObject("http://localhost:" + port + "/budget/mockCreate/{id}", Budget.class, budgetID).getId(),budgetID);
//    }
}