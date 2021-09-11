package com.adventureit.userservice.controller;

import com.adventureit.userservice.exceptions.InvalidRequestException;
import com.adventureit.userservice.exceptions.InvalidUserEmailException;
import com.adventureit.userservice.exceptions.InvalidUserPasswordException;
import com.adventureit.userservice.exceptions.InvalidUserPhoneNumberException;
import com.adventureit.userservice.requests.EditUserProfileRequest;
import com.adventureit.userservice.requests.RegisterUserRequest;
import com.adventureit.userservice.requests.UpdatePictureRequest;
import com.adventureit.userservice.responses.*;
import com.adventureit.userservice.service.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

/** This class implements the functionality of the UserAPI interface.*/
@CrossOrigin("*")
@RestController
@RequestMapping("user")
public class UserController {

    private final UserServiceImplementation service;

    @Autowired
    public UserController(UserServiceImplementation service) {
        this.service = service;
    }

    /**
     * Register User that is mapped from our mock controller for testing purposes
     *
     * @param req will take in a RegisterUserRequest object
     * @return a RegisterUserResponse object in json format to front end
     * @throws InvalidUserEmailException if the user email is invalid
     * @throws InvalidRequestException if the request body is null
     */
    @PostMapping(value = "registerUser", consumes = "application/json", produces = "application/json")
    public RegisterUserResponse registerUser(@RequestBody RegisterUserRequest req) throws InvalidUserEmailException, InvalidUserPhoneNumberException, InvalidUserPasswordException, InvalidRequestException {
        return service.registerUser(req);
    }

    @GetMapping(value="test")
    public String test(){
        return "User controller is working";
    }

    @PostMapping(value = "updatePicture")
    public HttpStatus updatePicture(@RequestPart MultipartFile file, @RequestParam("userid") UUID userId){
        return service.updateProfilePicture(file, userId);
    }

    @GetMapping(value = "viewPicture/{id}")
    public ResponseEntity<byte[]> viewImage(@PathVariable UUID id) throws IOException {
        return service.viewImage(id);
    }

    @PostMapping(value = "removePicture/{id}")
    public void removePicture(@PathVariable UUID id){
        service.removeImage(id);
    }

    @GetMapping(value = "getUser/{id}")
    public GetUserByUUIDDTO getUserByUUID(@PathVariable UUID id) {
        return service.getUserByUUID(id);
    }

    @GetMapping(value = "acceptFriendRequest/{id}")
    public String acceptFriend(@PathVariable UUID id){
        return service.acceptFriendRequest(id);
    }

    @GetMapping(value = "getFriends/{id}")
    public List<UUID> getFriends(@PathVariable UUID id) {
        return service.getFriends(id);
    }

    @GetMapping(value = "getFriendRequests/{id}")
    public List<GetFriendRequestsResponse> getFriendRequests(@PathVariable UUID id) {
        return service.getFriendRequests(id);
    }



    @GetMapping(value = "deleteFriendRequest/{id}")
    public void deleteRequest(@PathVariable UUID id){
        service.deleteFriendRequest(id);
    }

    @GetMapping(value = "removeFriend/{id}/{friendID}")
    public void deleteRequest(@PathVariable UUID id, @PathVariable UUID friendID){
        service.removeFriend(id, friendID);
    }

    @GetMapping(value = "getFriendProfiles/{id}")
    public List<GetUserByUUIDDTO> getFriendProfiles(@PathVariable UUID id){
        return service.getFriendProfiles(id);
    }

    @GetMapping(value = "getByUserName/{userName}")
    public UUID getUserIDByUserName(@PathVariable String userName){
        return service.getUserIDByUserName(userName);
    }

    @GetMapping(value = "createFriendRequest/{id1}/{id2}")
    public void createFriendRequest(@PathVariable String id1, @PathVariable String id2){
        service.createFriendRequest(id1, id2);
    }

    @GetMapping("getFriendRequest/{id}")
    public FriendDTO getFriendRequest(@PathVariable UUID id){
        return service.getFriendRequest(id);
    }

    @GetMapping("deleteUser/{id}")
    public void deleteUser(@PathVariable UUID id){
        service.deleteUser(id);
    }

    @PostMapping("editUserProfile")
    public String editUserProfile(@RequestBody EditUserProfileRequest req){
        return service.editUserProfile(req);
    }

}
