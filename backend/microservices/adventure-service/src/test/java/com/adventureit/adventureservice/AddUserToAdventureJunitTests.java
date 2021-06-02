package com.adventureit.adventureservice;

import com.adventureit.adventureservice.Requests.AddUserToAdventureRequest;
import com.adventureit.adventureservice.Responses.AddUserToAdventureResponse;
import com.adventureit.adventureservice.Service.AdventureServiceImplementation;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Description;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AddUserToAdventureJunitTests {
    private AdventureServiceImplementation adventure = new AdventureServiceImplementation();
    UUID userID = UUID.fromString("933c0a14-a837-4789-991a-15006778f465");
    UUID advID = UUID.fromString("933c0a14-a837-4789-991a-15006778f123");
    //test request, response + service

    @Test
    @Description("This test tests whether the correct information will be retrieved through the request object")
    void TestAddUserToAdventureRequest()
    {
        AddUserToAdventureRequest req = new AddUserToAdventureRequest(userID, advID);
        assertNotNull(req);
        assertEquals(userID, req.getUserID());
        assertEquals(advID, req.getAdventureID());
    }

    @Test
    @Description("This test tests whether the response object returned carries the correct information")
    void TestAddUserToAdventureResponse()
    {
        AddUserToAdventureRequest req = new AddUserToAdventureRequest(userID, advID);
        assertNotNull(req);
        AddUserToAdventureResponse res = adventure.AddUserToAdventure(req);
        assertEquals(true, res.isSuccess());
        //assertEquals(,res.getMessage());
    }
}
