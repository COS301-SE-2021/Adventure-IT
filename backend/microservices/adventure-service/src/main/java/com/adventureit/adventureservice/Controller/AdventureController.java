package com.adventureit.adventureservice.Controller;

import com.adventureit.adventureservice.Requests.AddUserToAdventureRequest;
import com.adventureit.adventureservice.Requests.CreateAdventureRequest;
import com.adventureit.adventureservice.Requests.GetAdventureByUUIDRequest;
import com.adventureit.adventureservice.Responses.AddUserToAdventureResponse;
import com.adventureit.adventureservice.Responses.CreateAdventureResponse;
import com.adventureit.adventureservice.Responses.GetAdventureByUUIDResponse;
import com.adventureit.adventureservice.Service.AdventureServiceImplementation;
import com.adventureit.userservice.Exceptions.InvalidRequestException;
import com.adventureit.userservice.Exceptions.InvalidUserEmailException;
import com.adventureit.userservice.Exceptions.InvalidUserPasswordException;
import com.adventureit.userservice.Exceptions.InvalidUserPhoneNumberException;
import com.adventureit.userservice.Requests.RegisterUserRequest;
import com.adventureit.userservice.Responses.RegisterUserResponse;
import com.adventureit.userservice.Service.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class AdventureController {
    private final AdventureServiceImplementation service;

    @Autowired
    public AdventureController(AdventureServiceImplementation service){
        this.service = service;
    }

    @PostMapping(value = "api/createAdventure", consumes = "application/json", produces = "application/json")
    public CreateAdventureResponse createAdventure(@RequestBody CreateAdventureRequest req){
        return service.createAdventure(req);
    }

    @PostMapping(value = "api/getAdventureByUUID", consumes = "application/json", produces = "application/json")
    public GetAdventureByUUIDResponse getAdventureByUUID(@RequestBody GetAdventureByUUIDRequest req){
        return service.getAdventureByUUID(req);
    }

    @PostMapping(value = "api/AddUserToAdventure", consumes = "application/json", produces = "application/json")
    public AddUserToAdventureResponse addUserToAdventure(@RequestBody AddUserToAdventureRequest req){
        return service.AddUserToAdventure(req);
    }
}
