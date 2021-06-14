package com.adventureit.adventureservice;

import com.adventureit.adventureservice.Repository.AdventureRepository;
import com.adventureit.userservice.Service.UserServiceImplementation;
import main.java.com.adventureit.adventureservice.Repository.ItineraryRepository
import com.adventureit.adventureservice.Requests.CreateItineraryRequest;
import com.adventureit.adventureservice.Requests.GetAdventureByUUIDRequest;
import com.adventureit.adventureservice.Requests.RemoveItineraryRequest;
import com.adventureit.adventureservice.Responses.CreateItineraryResponse;
import com.adventureit.adventureservice.Responses.GetAdventureByUUIDResponse;
import com.adventureit.adventureservice.Responses.RemoveItineraryResponse;
import com.adventureit.adventureservice.Service.AdventureServiceImplementation;
import com.adventureit.adventureservice.Service.ItineraryServiceImplementation;
import com.adventureit.userservice.Exceptions.InvalidRequestException;
import com.adventureit.userservice.Requests.GetUserByUUIDRequest;
import com.adventureit.userservice.Responses.GetUserByUUIDResponse;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ItineraryServiceJUnitTests {

    private AdventureRepository adventureRepository = Mockito.mock(AdventureRepository.class);
    private AdventureServiceImplementation adventureServiceImpl = new AdventureServiceImplementation();
    private ItineraryRepository ItineraryRepository = Mockito.mock(ItineraryRepository.class);
    private ItineraryServiceImplementation ItineraryService = new ItineraryServiceImplementation();
    private UserRepository UserRepository = Mockito.mock(UserRepository.class);
    private UserServiceImplementation UserService = new UserServiceImplementation();


    @Test
    @Description("Tests whether or not a new itinerary can be added when providing a valid user id and valid adventure id")
    public void addItineraryTest() {
        final UUID validUserID = UUID.fromString("933c0a14-a837-4789-991a-15006778f465");
        final UUID validAdventureID = UUID.fromString("e9b19e5c-4197-4f88-814a-5e51ff305f7b");
        CreateItineraryRequest req = new CreateItineraryRequest("Test Itinerary", "This is an itinerary created for testing purposes", validUserID, validAdventureID);

        try {
            CreateItineraryResponse res = this.ItineraryService.createItinerary(req);
            assertNotNull(res);
            assertEquals(res.isSuccess(), true);
        } catch (Exception e) {

        }


    }


    @Test
    @Description("Tests whether or not an itinerary can be deleted when providing a valid user id, valid adventure id and valid itineraryID")
    public void removeItineraryTest() {
        final long validItineraryID = 1;
        final UUID validUserID = UUID.fromString("933c0a14-a837-4789-991a-15006778f465");
        final UUID validAdventureID = UUID.fromString("e9b19e5c-4197-4f88-814a-5e51ff305f7b");
        RemoveItineraryRequest req = new RemoveItineraryRequest(validItineraryID, validUserID, validAdventureID);
        try {
            RemoveItineraryResponse res = this.ItineraryService.removeItinerary(req);
            assertNotNull(res);
            assertEquals(res.isSuccess(), true);
        } catch (Exception e) {

        }

    }

    @Test
    @Description ("Request is null")
    public void NullRequestForCreate()
    {
        CreateItineraryRequest req = null;
        Throwable thrown = assertThrows(InvalidRequestException.class , ()-> ItineraryService.createItinerary(req));
        assertNull(req);
        assertEquals("Error! Bad Request", thrown.getMessage());
    }

    @Test
    @Description ("Title for createItinerary is null")
    public void NullTitleForCreate()
    {
        final UUID validUserID = UUID.fromString("933c0a14-a837-4789-991a-15006778f465");
        final UUID validAdventureID = UUID.fromString("e9b19e5c-4197-4f88-814a-5e51ff305f7b");
        CreateItineraryRequest req = new CreateItineraryRequest(null,"description",validAdventureID,validUserID);
        Throwable thrown = assertThrows(InvalidRequestException.class , ()-> ItineraryService.createItinerary(req));
        assertEquals("Error! Bad Request", thrown.getMessage());
    }

    @Test
    @Description ("Description for createItinerary is null")
    public void NullDescriptionForCreate()
    {
        final UUID validUserID = UUID.fromString("933c0a14-a837-4789-991a-15006778f465");
        final UUID validAdventureID = UUID.fromString("e9b19e5c-4197-4f88-814a-5e51ff305f7b");
        CreateItineraryRequest req = new CreateItineraryRequest("Title",null,validAdventureID,validUserID);
        Throwable thrown = assertThrows(InvalidRequestException.class , ()-> ItineraryService.createItinerary(req));
        assertEquals("Error! Bad Request", thrown.getMessage());
    }

    @Test
    @Description ("Adventure id for createItinerary is null")
    public void NullAdventureIDForCreate()
    {
        final UUID validUserID = UUID.fromString("933c0a14-a837-4789-991a-15006778f465");
        final UUID validAdventureID = UUID.fromString("e9b19e5c-4197-4f88-814a-5e51ff305f7b");
        CreateItineraryRequest req = new CreateItineraryRequest("Title","description",null,validUserID);
        Throwable thrown = assertThrows(InvalidRequestException.class , ()-> ItineraryService.createItinerary(req));
        assertEquals("Error! Bad Request", thrown.getMessage());
    }

    @Test
    @Description ("user id for creatorID for createItinerary is null")
    public void NullUserIDForCreate()
    {
        final UUID validUserID = UUID.fromString("933c0a14-a837-4789-991a-15006778f465");
        final UUID validAdventureID = UUID.fromString("e9b19e5c-4197-4f88-814a-5e51ff305f7b");
        CreateItineraryRequest req = new CreateItineraryRequest("Title","description",validAdventureID,null);
        Throwable thrown = assertThrows(InvalidRequestException.class , ()-> ItineraryService.createItinerary(req));
        assertEquals("Error! Bad Request", thrown.getMessage());
    }

    @Test
    @Description ("Request for createItinerary is null")
    public void NullRequestForRemove()
    {
        RemoveItineraryRequest req = null;
        Throwable thrown = assertThrows(InvalidRequestException.class , ()-> ItineraryService.removeItinerary(req));
        assertNull(req);
        assertEquals("Error! Bad Request", thrown.getMessage());
    }

    @Test
    @Description ("Adventure id for removeItinerary is null")
    public void NullAdventureIDForRemove()
    {
        final UUID validUserID = UUID.fromString("933c0a14-a837-4789-991a-15006778f465");
        final UUID validAdventureID = UUID.fromString("e9b19e5c-4197-4f88-814a-5e51ff305f7b");
        RemoveItineraryRequest req = new RemoveItineraryRequest(1,null,validUserID);
        Throwable thrown = assertThrows(InvalidRequestException.class , ()-> ItineraryService.removeItinerary(req));
        assertEquals("Error! Bad Request", thrown.getMessage());
    }

    @Test
    @Description ("User id for removeItinerary is null")
    public void NullUserIDForRemove()
    {
        final UUID validUserID = UUID.fromString("933c0a14-a837-4789-991a-15006778f465");
        final UUID validAdventureID = UUID.fromString("e9b19e5c-4197-4f88-814a-5e51ff305f7b");
        RemoveItineraryRequest req = new RemoveItineraryRequest(1,validAdventureID,null);
        Throwable thrown = assertThrows(InvalidRequestException.class , ()-> ItineraryService.removeItinerary(req));
        assertEquals("Error! Bad Request", thrown.getMessage());
    }






}


