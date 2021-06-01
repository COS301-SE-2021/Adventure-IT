package com.adventureit.userservice.Controller;

import com.adventureit.userservice.Service.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

/** This class implements the functionality of the UserAPI interface.*/
@CrossOrigin("*")
@RestController
public class UserController {

    @Autowired
    private UserServiceImplementation userService;
}
