package com.adventureit.userservice.Controller;

import com.adventureit.userservice.Exceptions.InvalidRequestException;
import com.adventureit.userservice.Exceptions.InvalidUserEmailException;
import com.adventureit.userservice.Exceptions.InvalidUserPasswordException;
import com.adventureit.userservice.Exceptions.InvalidUserPhoneNumberException;
import com.adventureit.userservice.Requests.RegisterUserRequest;
import com.adventureit.userservice.Responses.RegisterUserResponse;
import com.adventureit.userservice.Service.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/** This class implements the functionality of the UserAPI interface.*/
@CrossOrigin("*")
@RestController
public class UserController {


    private UserServiceImplementation service;

    @Autowired
    public UserController(UserServiceImplementation service){
        this.service = service;
    }


    @PostMapping(value = "api/RegisterUser", consumes = "application/json", produces = "application/json")
    public RegisterUserResponse RegisterUser(@RequestBody RegisterUserRequest req) throws InvalidUserEmailException, InvalidUserPhoneNumberException, InvalidUserPasswordException, InvalidRequestException {
        return service.RegisterUser(req);
    }
}
