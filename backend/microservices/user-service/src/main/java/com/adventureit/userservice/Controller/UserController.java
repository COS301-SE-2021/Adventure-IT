package com.adventureit.userservice.Controller;

import com.adventureit.userservice.Exceptions.InvalidRequestException;
import com.adventureit.userservice.Exceptions.InvalidUserEmailException;
import com.adventureit.userservice.Exceptions.InvalidUserPasswordException;
import com.adventureit.userservice.Exceptions.InvalidUserPhoneNumberException;
import com.adventureit.userservice.Requests.LoginUserRequest;
import com.adventureit.userservice.Requests.RegisterUserRequest;
import com.adventureit.userservice.Requests.AcceptFriendRequest;
import com.adventureit.userservice.Requests.UpdatePictureRequest;
import com.adventureit.userservice.Responses.GetFriendRequestsResponse;
import com.adventureit.userservice.Responses.GetUserByUUIDDTO;
import com.adventureit.userservice.Responses.LoginUserDTO;
import com.adventureit.userservice.Responses.RegisterUserResponse;
import com.adventureit.userservice.Service.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;

import java.io.File;
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
    public UserController(UserServiceImplementation service){
        this.service = service;
    }

    /**
     * Register User that is mapped from our mock controller for testing purposes
     * @param req will take in a RegisterUserRequest object
     * @return a RegisterUserResponse object in json format to front end
     * @throws InvalidUserEmailException if the user email is invalid
     * @throws InvalidRequestException if the request body is null
     */
    @PostMapping(value = "RegisterUser", consumes = "application/json", produces = "application/json")
    public RegisterUserResponse RegisterUser(@RequestBody RegisterUserRequest req) throws InvalidUserEmailException, InvalidUserPhoneNumberException, InvalidUserPasswordException, InvalidRequestException {
        return service.RegisterUser(req);
    }

    @GetMapping(value="test")
    public String test(){
        return "User controller is working";
    }

    @PostMapping(value = "updatePicture", consumes = "application/json", produces = "application/json")
    public String updatePicture(@RequestBody UpdatePictureRequest req) throws Exception {
        File f = new File(req.getPath());
        byte[] content = Files.readAllBytes(f.toPath());
        MockMultipartFile file = new MockMultipartFile("Profile Picture", f.getName(), "jpg", content);
        return service.updateProfilePicture(file, req.getId());
    }

    @GetMapping(value="GetUser/{id}")
    public GetUserByUUIDDTO getUserByUUID(@PathVariable UUID id){
        return service.GetUserByUUID(id);
    }

    @GetMapping(value = "acceptFriendRequest/{id}")
    public String acceptFriend(@PathVariable UUID id) throws Exception {
        return service.acceptFriendRequest(id);
    }

    @GetMapping(value="getFriends/{id}")
    public List<UUID> getFriends(@PathVariable UUID id){
        return service.getFriends(id);
    }

    @GetMapping(value="getFriendRequests/{id}")
    public List<GetFriendRequestsResponse> getFriendRequests(@PathVariable UUID id){
        return service.getFriendRequests(id);
    }

    @GetMapping(value="populateFriends")
    public void mockFriends()
    {
        service.mockFriendships();
    }

    @GetMapping(value="deleteFriendRequest/{id}")
    public void deleteRequest(@PathVariable UUID id) throws Exception {
        service.deleteFriendRequest(id);
    }

    @GetMapping(value="removeFriend/{id}/{friendID}")
    public void deleteRequest(@PathVariable UUID id, @PathVariable UUID friendID) throws Exception {
        service.removeFriend(id,friendID);
    }

    @GetMapping(value="getFriendProfiles/{id}")
    public List<GetUserByUUIDDTO>getFriendProfiles (@PathVariable UUID id) throws Exception {
        return service.getFriendProfiles(id);
    }

    @GetMapping(value="getByUserName/{userName}")
    public UUID getUserIDByUserName(@PathVariable String userName) throws Exception {
        return service.getUserIDByUserName(userName);
    }

    @GetMapping(value="createFriendRequest/{ID1}/{ID2}")
    public void createFriendRequest(@PathVariable String ID1,@PathVariable String ID2) throws Exception {
        service.createFriendRequest(ID1,ID2);
    }

}
