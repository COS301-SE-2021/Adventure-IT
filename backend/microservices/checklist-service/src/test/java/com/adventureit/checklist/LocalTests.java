//package com.adventureit.checklist;
//
//import com.adventureit.checklist.Repository.ChecklistEntryRepository;
//import com.adventureit.checklist.Repository.ChecklistRepository;
//import com.adventureit.checklist.Service.ChecklistServiceImplementation;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.UUID;
//
//@SpringBootTest
//public class LocalTests {
//    @Autowired
//    ChecklistRepository checklistRepository;
//    @Autowired
//    ChecklistEntryRepository checklistEntryRepository;
//    @Autowired
//    ChecklistServiceImplementation checklistServiceImplementation;
//
//    @Test
//    public void createTest() throws Exception {
//        checklistServiceImplementation.createChecklist("Mock 1","vsvs", UUID.randomUUID(),UUID.randomUUID(),UUID.randomUUID());
//    }
//
//    @Test
//    public void addTest() throws Exception {
//        checklistServiceImplementation.addChecklistEntry("Mock Entry 1",UUID.randomUUID(),UUID.fromString("25ad6e05-7b4d-494a-8d35-4393f343b315"));
//    }
//}
