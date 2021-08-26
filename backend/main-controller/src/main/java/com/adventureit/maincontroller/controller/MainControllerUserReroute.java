package com.adventureit.maincontroller.controller;


import com.adventureit.chat.requests.CreateDirectChatRequest;
import com.adventureit.userservice.exceptions.InvalidRequestException;
import com.adventureit.userservice.exceptions.InvalidUserEmailException;
import com.adventureit.userservice.exceptions.InvalidUserPasswordException;
import com.adventureit.userservice.exceptions.InvalidUserPhoneNumberException;
import com.adventureit.userservice.requests.LoginUserRequest;
import com.adventureit.userservice.requests.RegisterUserRequest;
import com.adventureit.userservice.requests.UpdatePictureRequest;
import com.adventureit.userservice.responses.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class MainControllerUserReroute {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String IP = "http://localhost";
    private final String userPort = "9002";


    @PostMapping(value = "RegisterUser", consumes = "application/json", produces = "application/json")
    public RegisterUserResponse registerUser(@RequestBody RegisterUserRequest req) throws InvalidUserEmailException, InvalidUserPhoneNumberException, InvalidUserPasswordException, InvalidRequestException {
        return restTemplate.postForObject(IP + ":" + userPort + "/user/RegisterUser/",req, RegisterUserResponse.class);
    }

    @GetMapping(value="test")
    public String test(){
        return "User controller is working";
    }

    @PostMapping(value = "updatePicture", consumes = "application/json", produces = "application/json")
    public String updatePicture(@RequestBody UpdatePictureRequest req){
       return restTemplate.postForObject(IP + ":" + userPort + "/user/updatePicture/",req, String.class);
    }


    @GetMapping(value="/ConfirmToken/{token}")
    public String confirmToken(@RequestParam("token") String token){
        return restTemplate.getForObject(IP + ":" + userPort + "/user/ConfirmToken/"+token, String.class);

    }

    @PostMapping(value = "LoginUser", consumes = "application/json", produces = "application/json")
    public LoginUserDTO login(@RequestBody LoginUserRequest req) throws InvalidUserEmailException, InvalidUserPhoneNumberException, InvalidUserPasswordException, InvalidRequestException {
        return restTemplate.postForObject(IP + ":" + userPort + "/user/LoginUser/",req, LoginUserDTO.class);
    }

    @GetMapping(value="/GetUser/{id}")
    public GetUserByUUIDDTO getUserByUUID(@PathVariable UUID id){
        return restTemplate.getForObject(IP + ":" + userPort + "/user/GetUser/"+id, GetUserByUUIDDTO.class);

    }

    @GetMapping(value = "acceptFriendRequest/{id}")
    public String acceptFriend(@PathVariable UUID id){
        FriendDTO friend = restTemplate.getForObject("http://"+ IP + ":" + userPort + "/user/getFriendRequest/"+id, FriendDTO.class);
        restTemplate.getForObject(IP + ":" + userPort + "/user/acceptFriendRequest/"+id, String.class);
        assert friend != null;
        CreateDirectChatRequest request = new CreateDirectChatRequest(friend.getFirstUser(),friend.getSecondUser());
        String chatPort = "9010";
        restTemplate.postForObject(IP + ":" + chatPort + "/chat/createDirectChat", request,String.class);
        return "Done";
    }

    @GetMapping(value="getFriends/{id}")
    public List<UUID> getFriends(@PathVariable UUID id){
        return restTemplate.getForObject(IP + ":" + userPort + "/user/getFriends/"+id, List.class);
    }

    @GetMapping(value="GetFriendRequests/{id}")
    public List<GetFriendRequestsResponse> getFriendRequests(@PathVariable UUID id){
        return restTemplate.getForObject(IP + ":" + userPort + "/user/getFriendRequests/"+id, List.class);

    }

    @GetMapping(value="populateFriends")
    public void mockFriends() {
        restTemplate.getForObject( IP + ":" + userPort + "/user/populateFriends", String.class);
    }

    @GetMapping(value="deleteFriendRequest/{id}")
    public void deleteRequest(@PathVariable UUID id) {
        restTemplate.getForObject(IP + ":" + userPort + "/user/deleteFriendRequest/"+id, List.class);

    }

    @GetMapping(value="removeFriend/{id}/{friendID}")
    public void deleteRequest(@PathVariable UUID id, @PathVariable UUID friendID){
        //add in delete direct chat here
        restTemplate.getForObject( IP + ":" + userPort + "/user/removeFriend/"+id+"/"+friendID, String.class);
    }


    @GetMapping(value = "getByUserName/{userName}")
    public UUID getUserIDByUserName(@PathVariable String userName){
        return restTemplate.getForObject(IP + ":" + userPort + "/user/getByUserName/"+ userName, UUID.class);
    }

    @GetMapping(value = "createFriendRequest/{id1}/{id2}")
    public void createFriendRequest(@PathVariable String id1, @PathVariable String id2){
        restTemplate.getForObject(IP + ":" + userPort + "/user/createFriendRequest/"+ id1+"/"+id2, UUID.class);

    }

    @GetMapping("getFriendRequest/{id}")
    public FriendDTO getFriendRequest(@PathVariable UUID id){
        return restTemplate.getForObject(IP + ":" + userPort + "/user/getFriendRequest/"+ id, FriendDTO.class);
    }
}

