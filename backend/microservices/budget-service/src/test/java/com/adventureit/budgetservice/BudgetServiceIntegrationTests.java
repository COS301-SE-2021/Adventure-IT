package com.adventureit.budgetservice;
import com.adventureit.budgetservice.Controllers.BudgetController;
import com.adventureit.budgetservice.Entity.Budget;
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
    /*@Autowired
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
    }*/


//    @Test
//    @Description("Ensure that the create function works")
//    public void httpView_returnResponse(){
//        UUID budgetID = UUID.randomUUID();
//        Assertions.assertEquals(this.restTemplate.getForObject("http://localhost:" + port + "/budget/mockCreate/{id}", Budget.class, budgetID).getId(),budgetID);
//    }

//    @Test
//    @Description("Ensure that the view function works")
//    public void httpView_returnResponse(){
//        UUID id = UUID.randomUUID();
//        Budget budget1 = new Budget(id,"Test Budget 1",new ArrayList<>());
//        budgetRepository.save(budget1);
//        Budget response = this.restTemplate.getForObject("http://localhost:" + port + "/budget/viewBudget/{id}", Budget.class, id);
//        Assertions.assertEquals(response.getName(), budget1.getName());
//    }

//    @Test
//    @Description("Ensure that the softDelete function works")
//    public void httpSoftDelete_returnResponse(){
//        UUID id = UUID.randomUUID();
//        Budget budget1 = new Budget(id,"Test Budget 2",new ArrayList<>());
//        budgetRepository.save(budget1);
//        Assertions.assertEquals(this.restTemplate.getForObject("http://localhost:" + port + "/budget/softDelete/{id}", String.class, id), "Budget successfully moved to bin.");
//    }

//    @Test
//    @Description("Ensure that the viewTrash function works")
//    public void httpViewTrash_returnResponse(){
//        UUID id = UUID.randomUUID();
//        Budget budget1 = new Budget(id,"Test Budget 3",new ArrayList<>());
//        budget1.setDeleted(true);
//        budgetRepository.save(budget1);
//        this.restTemplate.getForObject("http://localhost:" + port + "/budget/viewTrash",String.class);
//    }
}