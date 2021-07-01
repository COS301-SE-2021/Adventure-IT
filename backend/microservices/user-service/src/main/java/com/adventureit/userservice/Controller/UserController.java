package com.adventureit.userservice.Controller;

import com.adventureit.userservice.Exceptions.InvalidRequestException;
import com.adventureit.userservice.Exceptions.InvalidUserEmailException;
import com.adventureit.userservice.Exceptions.InvalidUserPasswordException;
import com.adventureit.userservice.Exceptions.InvalidUserPhoneNumberException;
import com.adventureit.userservice.Requests.RegisterUserRequest;
import com.adventureit.userservice.Responses.RegisterUserResponse;
import com.adventureit.userservice.Service.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/** This class implements the functionality of the UserAPI interface.*/
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserServiceImplementation service;

    @Autowired
    public UserController(UserServiceImplementation service){
        this.service = service;
    }


    /**
     * Register User that is mapped from our mock controller for testing purposes
     * @param req will take in a RegisterUserRequest object
     * @return a RegisterUserResponse object in json format to front end
     * @throws InvalidUserEmailException if the user email is invalid
     * @throws InvalidUserPhoneNumberException if the user phone number is invalid
     * @throws InvalidUserPasswordException if the user password is invalid
     * @throws InvalidRequestException if the request body is null
     */
    @PostMapping(value = "api/RegisterUser", consumes = "application/json", produces = "application/json")
    public RegisterUserResponse RegisterUser(@RequestBody RegisterUserRequest req) throws InvalidUserEmailException, InvalidUserPhoneNumberException, InvalidUserPasswordException, InvalidRequestException {
        return service.RegisterUser(req);
    }

    @GetMapping(value="/test")
    public String test(){
        return "User controller is working";
    }

    @GetMapping(value = "/updatePicture/{path}/{id}")
    public String updatePicture(@PathVariable String path, @PathVariable UUID id) throws Exception {
        File f = new File(path);
        byte[] content = Files.readAllBytes(f.toPath());
        MockMultipartFile file = new MockMultipartFile("Profile Picture", f.getName(), "jpg", content);
        return service.updateProfilePicture(file, id);
    }
}
