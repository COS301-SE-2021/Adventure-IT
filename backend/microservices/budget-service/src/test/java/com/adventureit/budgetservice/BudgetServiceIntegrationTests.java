package com.adventureit.budgetservice;
import com.adventureit.budgetservice.Controllers.BudgetController;
import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BudgetServiceIntegrationTests {
    @Autowired
    private BudgetController budgetController;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
//    @Description("Ensure that the Budget Controller loads")
    public void budgetControllerLoads() throws Exception {
        Assertions.assertNotNull(budgetController);
    }

    @Test
//    @Description("Ensure that the controller is accepting traffic and responding")
    public void httpTest_returnResponse(){
        Assertions.assertEquals(this.restTemplate.getForObject("http://localhost:" + port + "/budget/test", String.class),"Budget Controller is functional \n");
    }
}