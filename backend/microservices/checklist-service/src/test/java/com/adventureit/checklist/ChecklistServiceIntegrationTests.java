package com.adventureit.checklist;

import com.adventureit.checklist.Controllers.ChecklistController;
import com.adventureit.checklist.Repository.ChecklistRepository;
import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChecklistServiceIntegrationTests {
    @Autowired
    private ChecklistController checklistController;
    @Autowired
    private ChecklistRepository checklistRepository;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Description("Ensure that the Checklist Controller loads")
    public void checklistControllerLoads() throws Exception {
        Assertions.assertNotNull(checklistController);
    }

    @Test
    @Description("Ensure that the controller is accepting traffic and responding")
    public void httpTest_returnResponse(){
        Assertions.assertEquals(this.restTemplate.getForObject("http://localhost:" + port + "/checklist/test", String.class),"Checklist Controller is functional");
    }
}
