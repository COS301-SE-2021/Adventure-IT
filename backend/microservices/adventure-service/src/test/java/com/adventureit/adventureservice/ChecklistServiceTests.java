package com.adventureit.adventureservice;

import com.adventureit.adventureservice.Requests.CreateAdventureRequest;
import com.adventureit.adventureservice.Requests.GetAdventureByUUIDRequest;
import com.adventureit.adventureservice.Responses.GetAdventureByUUIDResponse;
import com.adventureit.adventureservice.Service.AdventureServiceImplementation;
import com.adventureit.userservice.Exceptions.InvalidRequestException;
import com.adventureit.userservice.Requests.GetUserByUUIDRequest;
import com.adventureit.userservice.Responses.GetUserByUUIDResponse;
import com.adventureit.userservice.Service.UserServiceImplementation;
import com.adventureit.adventureservice.Requests.CreateChecklistRequest;
import com.adventureit.adventureservice.Requests.RemoveChecklistRequest;
import com.adventureit.adventureservice.Responses.CreateChecklistResponse;
import com.adventureit.adventureservice.Responses.RemoveChecklistResponse;
import com.adventureit.adventureservice.Service.ChecklistService;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

@SpringBootTest
public class ChecklistServiceTests {

    private ChecklistService checklistService = new ChecklistService();
    private UserServiceImplementation userServiceImplementation = new UserServiceImplementation();

   @Test
   @Description("Tests whether or not a new checklist can be added when providing a valid user id and valid adventure id")
    public void addChecklist_validRequest(){
       final UUID validUserID = UUID.fromString("933c0a14-a837-4789-991a-15006778f465");
       final UUID validAdventureID = UUID.fromString("e9b19e5c-4197-4f88-814a-5e51ff305f7b");
       CreateChecklistRequest req = new CreateChecklistRequest("Test Checklist", "This is a checklist created for testing purposes", validUserID, validAdventureID);;
       CreateChecklistResponse res = null;
       try {
           res = this.checklistService.createChecklist(req);
       }
       catch(Exception e) {
           assertNull(e);
       }
       assertNotNull(res);
       assertEquals(res.getSuccess(), true);
   }

    @Test
    @Description("Tests whether or not the correct exception is thrown when a null User ID is passed in the request when creating a new checklist")
    public void addChecklistTest_invalidRequest_nullUserID(){
        final UUID invalidUserID = null;
        final UUID validAdventureID = UUID.fromString("e9b19e5c-4197-4f88-814a-5e51ff305f7b");
        CreateChecklistRequest req = new CreateChecklistRequest("Test Checklist", "This is a checklist created for testing purposes", invalidUserID, validAdventureID);;
        Throwable thrown = assertThrows(InvalidRequestException.class, ()->this.checklistService.createChecklist(req));
        assertEquals(thrown.getMessage(),"Error: Malformed CreateChecklistRequest (one or more null values)");
    }

    @Test
    @Description("Tests whether or not the correct exception is thrown when a null Adventure ID is passed in the request when creating a new checklist")
    public void addChecklistTest_invalidRequest_nullAdventureID(){
        final UUID validUserID = UUID.fromString("933c0a14-a837-4789-991a-15006778f465");
        final UUID invalidAdventureID = null;
        CreateChecklistRequest req = new CreateChecklistRequest("Test Checklist", "This is a checklist created for testing purposes", validUserID, invalidAdventureID);;
        Throwable thrown = assertThrows(InvalidRequestException.class, ()->this.checklistService.createChecklist(req));
        assertEquals(thrown.getMessage(),"Error: Malformed CreateChecklistRequest (one or more null values)");
    }

    @Test
    @Description("Tests whether or not the correct exception is thrown when a null request is passed when creating a new checklist")
    public void addChecklistTest_invalidRequest_nullReq(){
        CreateChecklistRequest req = null;
        Throwable thrown = assertThrows(InvalidRequestException.class, ()->this.checklistService.createChecklist(req));
        assertEquals(thrown.getMessage(),"Error: Malformed CreateChecklistRequest (one or more null values)");
    }

   @Test
   @Description("Tests whether or not a checklist can be deleted when providing a valid user id, valid adventure id and valid checklistID")
    public void removeChecklistTest(){
       final long validChecklistID = 1;
       final UUID validUserID = UUID.fromString("933c0a14-a837-4789-991a-15006778f465");
       final UUID validAdventureID = UUID.fromString("e9b19e5c-4197-4f88-814a-5e51ff305f7b");
       RemoveChecklistRequest req = new RemoveChecklistRequest(validChecklistID, validUserID, validAdventureID);
       RemoveChecklistResponse res = null;
       try{
            res = this.checklistService.removeChecklist(req);
       }
       catch(Exception e){
           assertNull(e);
       }
       assertNotNull(res);
       assertEquals(res.isSuccess(), true);
   }

    @Test
    @Description("Tests whether or not the correct exception is thrown when a null Checklist ID is passed in the request when removing an existing checklist")
    public void removeChecklistTest_invalidRequest_nullChecklistID(){
        final Long invalidChecklistID = null;
        final UUID validUserID = UUID.fromString("933c0a14-a837-4789-991a-15006778f465");
        final UUID validAdventureID = UUID.fromString("e9b19e5c-4197-4f88-814a-5e51ff305f7b");
        RemoveChecklistRequest req = new RemoveChecklistRequest(invalidChecklistID, validUserID, validAdventureID);
        Throwable thrown = assertThrows(InvalidRequestException.class, ()->this.checklistService.removeChecklist(req));
        assertEquals(thrown.getMessage(),"Error: Malformed RemoveChecklistRequest (one or more null values)");
    }

    @Test
    @Description("Tests whether or not the correct exception is thrown when a null User ID is passed in the request when removing an existing checklist")
    public void removeChecklistTest_invalidRequest_nullUserID(){
        final long validChecklistID = 1;
        final UUID invalidUserID = null;
        final UUID validAdventureID = UUID.fromString("e9b19e5c-4197-4f88-814a-5e51ff305f7b");
        RemoveChecklistRequest req = new RemoveChecklistRequest(validChecklistID, invalidUserID, validAdventureID);
        Throwable thrown = assertThrows(InvalidRequestException.class, ()->this.checklistService.removeChecklist(req));
        assertEquals(thrown.getMessage(),"Error: Malformed RemoveChecklistRequest (one or more null values)");
    }

    @Test
    @Description("Tests whether or not the correct exception is thrown when a null Adventure ID is passed in the request when removing an existing checklist")
    public void removeChecklistTest_invalidRequest_nullAdventureID(){
        final long validChecklistID = 1;
        final UUID validUserID = UUID.fromString("933c0a14-a837-4789-991a-15006778f465");
        final UUID invalidAdventureID = null;
        RemoveChecklistRequest req = new RemoveChecklistRequest(validChecklistID, validUserID, invalidAdventureID);
        Throwable thrown = assertThrows(InvalidRequestException.class, ()->this.checklistService.removeChecklist(req));
        assertEquals(thrown.getMessage(),"Error: Malformed RemoveChecklistRequest (one or more null values)");
    }

    @Test
    @Description("Tests whether or not the correct exception is thrown when a null request is passed when removing an existing checklist")
    public void removeChecklistTest_invalidRequest_nullReq(){
        RemoveChecklistRequest req = null;
        Throwable thrown = assertThrows(InvalidRequestException.class, ()->this.checklistService.removeChecklist(req));
        assertEquals(thrown.getMessage(),"Error: Malformed RemoveChecklistRequest (one or more null values)");
    }

   @Test
   @Description("Tests whether or not the Checklist service integrates with the User service")
   public void UserIntegrationTest(){
       final UUID validUserID = UUID.fromString("933c0a14-a837-4789-991a-15006778f465");
       GetUserByUUIDRequest req = new GetUserByUUIDRequest(validUserID);
       GetUserByUUIDResponse res = userServiceImplementation.GetUserByUUID(req);
       assertNotNull(res);
       assertEquals(res.getUser().getFirstname(), "Kevin");
       assertEquals(res.getUser().getLastname(), "Potter");
       assertEquals(res.getUser().getEmail(), "u19024143@tuks.co.za");
       assertEquals(res.getUser().getPhoneNumber(), "0794083122");
       assertEquals(res.getUser().getUserID(), UUID.fromString("933c0a14-a837-4789-991a-15006778f465"));

   }

}
