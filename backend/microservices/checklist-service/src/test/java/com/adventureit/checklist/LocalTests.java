//package com.adventureit.checklist;
//
//import com.adventureit.checklist.Entity.ChecklistEntry;
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
//
//    @Autowired
//    ChecklistEntryRepository checklistEntryRepository;
//
//    @Autowired
//    ChecklistServiceImplementation checklistServiceImplementation;
//
//    @Test
//    public void createEntry(){
//        checklistEntryRepository.save(new ChecklistEntry("Mock", UUID.randomUUID(), UUID.randomUUID()));
//    }
//
//    @Test
//    public void deleteEntry() throws Exception {
//        checklistServiceImplementation.removeChecklistEntry(UUID.fromString("ce0ae290-8366-490c-9865-f6206cf837a7"));
//    }
//}
