//package com.adventureit.adventureservice;
//
//import com.adventureit.adventureservice.Repository.ChecklistRepository;
//import com.adventureit.adventureservice.Service.ChecklistService;
//import jdk.jfr.Description;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.util.UUID;
//
//// unit testing
//public class ChecklistServiceTests {
//    // TODO: Add mocking for Adventure Service
//    // private AdventureRepository adventureRepository = Mockito.mock(AdventureRepository.class);
//    // private AdventureServiceImpl adventureServiceImpl = new AdventureServiceImpl();
//    private ChecklistRepository checklistRepository = Mockito.mock(ChecklistRepository.class);
//    private ChecklistService checklistService = new ChecklistService();
//
//    // Mocking an adventure
//    // Adventure mockAdventure1 = new Adventure();
//    // Checklist mockChecklist1 = new Checklist();
//    // Adventure mockAdventure2 = new Adventure().addChecklist(mockChecklist1);
//
//    @Test
//    @Description("Test whether a checklist is added to an adventure")
//    public void addChecklist(){
//        // TODO: Uncomment test
//        // try {
//        //      Mockito.when(adventureRepository.findByUUID(mockAdventure1.getID)).thenReturn(mockAdventure1);
//        //      Checklist persistedChecklist = checklistService.addChecklist(someUserID, mockAdventure1.getUUID, "test checklist", "this is to test whether or not the checklist is actually created");
//        //      Assertions.assertEquals(persistedChecklist, adventureServiceImpl.getAdventureByUUID(mockAdventure1.getUUID().getChecklist(0)));
//        // }
//        // catch(exception e){
//        //      assertNull(e);
//        // }
//    }
//
//    @Test
//    @Description("Test whether a checklist is added to an adventure")
//    public void removeChecklist(){
//        // TODO: Uncomment test
//        // try {
//        //      Mockito.when(adventureRepository.findByUUID(mockAdventure2.getUUID())).thenReturn(mockAdventure2);
//        //      Mockito.when(checklistRepository.findByID(mockChecklist1.getID())).thenReturn(mockChecklist1);
//        //      checklistService.removeChecklist(mockChecklist1.getID(), someUserID, mockAdventure2.getUUID());
//        //      assertEquals(0, mockAdventure2.getAllChecklists());
//        // }
//        // catch(exception e){
//        //      assertNull(e);
//        // }
//    }
//
//}
