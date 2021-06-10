package com.adventureit.adventureservice;

import com.adventureit.adventureservice.Entity.Entry;
import com.adventureit.adventureservice.Entity.EntryContainer;
import com.adventureit.adventureservice.Requests.CreateAdventureRequest;

import com.adventureit.adventureservice.Requests.RemoveChecklistRequest;

import com.adventureit.adventureservice.Responses.CreateAdventureResponse;
import com.adventureit.adventureservice.Responses.RemoveChecklistResponse;
import com.adventureit.adventureservice.Service.AdventureServiceImplementation;
import com.adventureit.userservice.Entities.User;
import com.adventureit.userservice.Requests.GetUserByUUIDRequest;
import com.adventureit.userservice.Responses.GetUserByUUIDResponse;
import com.adventureit.userservice.Service.UserServiceImplementation;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CreateAdventureTests {

    private UserServiceImplementation userServiceImplementation = new UserServiceImplementation();
    private AdventureServiceImplementation adventureServiceImplementation = new AdventureServiceImplementation();

    @Test
    @Description("Tests whether or not a new adventure can be created when providing no null fields")
    public void createAdventureNoNullReturnsTrue(){
        CreateAdventureRequest req = new CreateAdventureRequest("Adventure1",UUID.fromString("e9b19e5c-4197-4f88-814a-5e51ff305f7b"),new User(), new ArrayList<String>(),new ArrayList<EntryContainer>());
        CreateAdventureResponse res = adventureServiceImplementation.createAdventure(req);
        assertEquals(res.isSuccess(), true);
    }

    @Test
    @Description("Tests whether or not a new adventure can be created when providing a null name")
    public void createAdventureNameNullReturnsFalse(){
        String name = null;
        CreateAdventureRequest req = new CreateAdventureRequest(name,UUID.fromString("e9b19e5c-4197-4f88-814a-5e51ff305f7b"),new User(), new ArrayList<String>(),new ArrayList<EntryContainer>());
        CreateAdventureResponse res = adventureServiceImplementation.createAdventure(req);
        assertEquals(res.isSuccess(), false);
    }

    @Test
    @Description("Tests whether or not a new adventure can be created when providing a null UUID")
    public void createAdventureUUIDNullReturnsFalse(){
        UUID id = null;
        CreateAdventureRequest req = new CreateAdventureRequest("Adventure1",id,new User(), new ArrayList<String>(),new ArrayList<EntryContainer>());
        CreateAdventureResponse res = adventureServiceImplementation.createAdventure(req);
        assertEquals(res.isSuccess(), false);
    }

    @Test
    @Description("Tests whether or not a new adventure can be created when providing a null Owner")
    public void createAdventureOwnerNullReturnsFalse(){
        User owner = null;
        CreateAdventureRequest req = new CreateAdventureRequest("Adventure1",UUID.fromString("e9b19e5c-4197-4f88-814a-5e51ff305f7b"),owner, new ArrayList<String>(),new ArrayList<EntryContainer>());
        CreateAdventureResponse res = adventureServiceImplementation.createAdventure(req);
        assertEquals(res.isSuccess(), false);
    }

    @Test
    @Description("Tests whether or not a new adventure can be created when providing a null group")
    public void createAdventureGroupNullReturnsFalse(){
        ArrayList<String> group = null;
        CreateAdventureRequest req = new CreateAdventureRequest("Adventure1",UUID.fromString("e9b19e5c-4197-4f88-814a-5e51ff305f7b"),new User(), group,new ArrayList<EntryContainer>());
        CreateAdventureResponse res = adventureServiceImplementation.createAdventure(req);
        assertEquals(res.isSuccess(), false);
    }

    @Test
    @Description("Tests whether or not a new adventure can be created when providing a null container")
    public void createAdventureContainerNullReturnsFalse(){
        ArrayList<EntryContainer> containers = null;
        CreateAdventureRequest req = new CreateAdventureRequest("Adventure1",UUID.fromString("e9b19e5c-4197-4f88-814a-5e51ff305f7b"),new User(), new ArrayList<String>() ,containers);
        CreateAdventureResponse res = adventureServiceImplementation.createAdventure(req);
        assertEquals(res.isSuccess(), false);
    }
}
