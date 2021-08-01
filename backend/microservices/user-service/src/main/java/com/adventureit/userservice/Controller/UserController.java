package com.adventureit.userservice.Controller;

import com.adventureit.userservice.Entities.Users;
import com.adventureit.userservice.Exceptions.InvalidRequestException;
import com.adventureit.userservice.Exceptions.InvalidUserEmailException;
import com.adventureit.userservice.Exceptions.InvalidUserPasswordException;
import com.adventureit.userservice.Exceptions.InvalidUserPhoneNumberException;
import com.adventureit.userservice.Requests.LoginUserRequest;
import com.adventureit.userservice.Requests.RegisterUserRequest;
import com.adventureit.userservice.Requests.SaveFriendRequest;
import com.adventureit.userservice.Requests.UpdatePictureRequest;
import com.adventureit.userservice.Responses.GetUserByUUIDDTO;
import com.adventureit.userservice.Responses.LoginUserDTO;
import com.adventureit.userservice.Responses.RegisterUserResponse;
import com.adventureit.userservice.Service.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

import java.util.UUID;

/** This class implements the functionality of the UserAPI interface.*/
@CrossOrigin("*")
@RestController
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

    @GetMapping(value="/user/test")
    public String test(){
        return "User controller is working";
    }

    @PostMapping(value = "api/updatePicture", consumes = "application/json", produces = "application/json")
    public String updatePicture(@RequestBody UpdatePictureRequest req) throws Exception {
        File f = new File(req.getPath());
        byte[] content = Files.readAllBytes(f.toPath());
        MockMultipartFile file = new MockMultipartFile("Profile Picture", f.getName(), "jpg", content);
        return service.updateProfilePicture(file, req.getId());
    }


    @GetMapping(value="/api/ConfirmToken")
    public String ConfirmToken(@RequestParam("token") String token){
        return service.confirmToken(token);
    }

    @PostMapping(value = "api/LoginUser", consumes = "application/json", produces = "application/json")
    public LoginUserDTO Login(@RequestBody LoginUserRequest req) throws InvalidUserEmailException, InvalidUserPhoneNumberException, InvalidUserPasswordException, InvalidRequestException {
        return service.LoginUser(req);
    }

    @GetMapping(value="api/GetUser/{id}")
    public GetUserByUUIDDTO getUserByUUID(@PathVariable UUID id){
        return service.GetUserByUUID(id);
    }

    @PostMapping(value = "api/saveFriend")
    public String saveFriend(@RequestBody SaveFriendRequest req) throws Exception {
        return service.saveFriends(req.getID1(),req.getID2());
    }

    @GetMapping(value="api/GetFriends/{id}")
    public List<UUID> getFriends(@PathVariable UUID id){
        return service.getFriends(id);
    }

}
