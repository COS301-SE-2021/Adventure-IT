package com.adventureit.checklist;

import com.adventureit.checklist.Controllers.ChecklistController;
import com.adventureit.checklist.Entity.Checklist;
import com.adventureit.checklist.Entity.ChecklistEntry;
import com.adventureit.checklist.Repository.ChecklistEntryRepository;
import com.adventureit.checklist.Repository.ChecklistRepository;
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
public class ChecklistServiceIntegrationTests {
    @Autowired
    private ChecklistController checklistController;
    @Autowired
    private ChecklistRepository checklistRepository;
    @Autowired
    private ChecklistEntryRepository checklistEntryRepository;

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

    @Test
    @Description("Ensure that the view function works")
    public void httpViewByAdventure_returnResponse(){
        UUID id = UUID.randomUUID();
        UUID adventureID = UUID.randomUUID();
        Checklist checklist = new Checklist("Test Checklist","Mock",id,UUID.randomUUID(),adventureID);
        checklistRepository.saveAndFlush(checklist);
        this.restTemplate.getForObject("http://localhost:" + port + "/checklist/viewChecklistsByAdventure/{id}", List.class, id);
    }

    @Test
    @Description("Ensure that the softDelete function works")
    public void httpSoftDelete_returnResponse(){
        UUID id = UUID.randomUUID();
        UUID adventureID = UUID.randomUUID();
        UUID ownerID = UUID.randomUUID();
        Checklist checklist = new Checklist("Test Checklist","Mock",id,ownerID,adventureID);
        checklistRepository.saveAndFlush(checklist);
        Assertions.assertEquals(this.restTemplate.getForObject("http://localhost:" + port + "/checklist/softDelete/{id}/{userID}", String.class, id,ownerID), "Checklist moved to bin");
    }

    @Test
    @Description("Ensure that the hardDelete function works")
    public void httpHardDelete_returnResponse(){
        UUID id = UUID.randomUUID();
        UUID adventureID = UUID.randomUUID();
        UUID ownerID = UUID.randomUUID();
        Checklist checklist = new Checklist("Test Checklist","Mock",id,ownerID,adventureID);
        checklist.setDeleted(true);
        checklistRepository.saveAndFlush(checklist);
        Assertions.assertEquals(this.restTemplate.getForObject("http://localhost:" + port + "/checklist/hardDelete/{id}/{userID}", String.class, id,ownerID), "Checklist deleted");
    }

    @Test
    @Description("Ensure that the viewTrash function works")
    public void httpViewTrash_returnResponse(){
        UUID id = UUID.randomUUID();
        UUID adventureID = UUID.randomUUID();
        UUID ownerID = UUID.randomUUID();
        Checklist checklist = new Checklist("Test Checklist","Mock",id,ownerID,adventureID);
        checklist.setDeleted(true);
        checklistRepository.saveAndFlush(checklist);
        this.restTemplate.getForObject("http://localhost:" + port + "/checklist/viewTrash/{id}", String.class, adventureID);
    }

    @Test
    @Description("Ensure that the restoreChecklist function works")
    public void httpRestoreChecklist_returnResponse(){
        UUID id = UUID.randomUUID();
        UUID adventureID = UUID.randomUUID();
        UUID ownerID = UUID.randomUUID();
        Checklist checklist = new Checklist("Test Checklist","Mock",id,ownerID,adventureID);
        checklist.setDeleted(true);
        checklistRepository.saveAndFlush(checklist);
        Assertions.assertEquals(this.restTemplate.getForObject("http://localhost:" + port + "/checklist/restoreChecklist/{id}/{userID}", String.class, id,ownerID), "Checklist was restored");
    }

    @Test
    @Description("Ensure that the removeEntry function works")
    public void httpRemoveEntry_returnResponse(){
        UUID id = UUID.randomUUID();
        UUID checklistID = UUID.randomUUID();
        ChecklistEntry entry = new ChecklistEntry("Mock",id,checklistID);
        checklistEntryRepository.saveAndFlush(entry);
        Assertions.assertEquals(this.restTemplate.getForObject("http://localhost:" + port + "/checklist/removeEntry/{id}", String.class, id), "Checklist Entry successfully removed");
    }

    @Test
    @Description("Ensure that the markEntry function works")
    public void httpMarkEntry_returnResponse(){
        UUID id = UUID.randomUUID();
        UUID checklistID = UUID.randomUUID();
        ChecklistEntry entry = new ChecklistEntry("Mock",id,checklistID);
        checklistEntryRepository.saveAndFlush(entry);
        this.restTemplate.getForObject("http://localhost:" + port + "/checklist/markEntry/{id}", String.class, id);
    }

    @Test
    @Description("Ensure that the getChecklistByID function works")
    public void httpGetChecklistByID_returnResponse(){
        UUID id = UUID.randomUUID();
        UUID adventureID = UUID.randomUUID();
        UUID ownerID = UUID.randomUUID();
        Checklist checklist = new Checklist("Test Checklist","Mock",id,ownerID,adventureID);
        checklistRepository.saveAndFlush(checklist);
        this.restTemplate.getForObject("http://localhost:" + port + "/checklist/getChecklist/{id}", String.class, id);
    }
}