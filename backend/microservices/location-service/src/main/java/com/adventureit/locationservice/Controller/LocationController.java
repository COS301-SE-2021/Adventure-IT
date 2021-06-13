package com.adventureit.locationservice.Controller;


import com.adventureit.userservice.Requests.RegisterUserRequest;
import com.adventureit.userservice.Responses.RegisterUserResponse;
import com.adventureit.userservice.Service.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/** This class implements the functionality of the UserAPI interface.*/
@CrossOrigin("*")
@RestController
public class LocationController {

    private final LocationServiceImplementation service;

    @Autowired
    public LocationController(LocationServiceImplementation service){
        this.service = service;
    }


}
