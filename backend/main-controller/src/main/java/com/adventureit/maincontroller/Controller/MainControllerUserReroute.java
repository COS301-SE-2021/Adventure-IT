package com.adventureit.maincontroller.Controller;


import com.adventureit.chat.Requests.CreateDirectChatRequest;
import com.adventureit.chat.Responses.DirectChatResponseDTO;
import com.adventureit.userservice.Entities.Friend;
import com.adventureit.userservice.Exceptions.InvalidRequestException;
import com.adventureit.userservice.Exceptions.InvalidUserEmailException;
import com.adventureit.userservice.Exceptions.InvalidUserPasswordException;
import com.adventureit.userservice.Exceptions.InvalidUserPhoneNumberException;
import com.adventureit.userservice.Requests.LoginUserRequest;
import com.adventureit.userservice.Requests.RegisterUserRequest;
import com.adventureit.userservice.Requests.UpdatePictureRequest;
import com.adventureit.userservice.Responses.GetFriendRequestsResponse;
import com.adventureit.userservice.Responses.GetUserByUUIDDTO;
import com.adventureit.userservice.Responses.LoginUserDTO;
import com.adventureit.userservice.Responses.RegisterUserResponse;
import com.netflix.discovery.EurekaClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class MainControllerUserReroute {


    private EurekaClient eurekaClient;

    private final RestTemplate restTemplate = new RestTemplate();
    private final String IP = "localhost";
    private final String userPort = "9002";
    private final String chatPort = "9010";


//    @RequestMapping("/api/GetUser/{id}")
//    public GetUserByUUIDDTO getUserByUUID(@PathVariable UUID id){
//        InstanceInfo userInstance =eurekaClient.getApplication("USER-SERVICE").getInstances().get(0);
//        String userIP = userInstance.getIPAddr();
//        int userPort = userInstance.getPort();
//        return restTemplate.getForObject("http://"+ userIP + ":" + userPort + "/user/GetUser/"+id.toString(), GetUserByUUIDDTO.class);
//    }

    @GetMapping(value="/ConfirmToken/{token}")
    public String ConfirmToken(@RequestParam("token") String token){
        return restTemplate.getForObject("http://"+ IP + ":" + userPort + "/user/ConfirmToken/"+token, String.class);

    }

    @PostMapping(value = "/api/LoginUser", consumes = "application/json", produces = "application/json")
    public LoginUserDTO Login(@RequestBody LoginUserRequest req) throws InvalidUserEmailException, InvalidUserPhoneNumberException, InvalidUserPasswordException, InvalidRequestException {
        return restTemplate.postForObject("http://"+ IP + ":" + userPort + "/user/LoginUser/",req, LoginUserDTO.class);
    }

    @PostMapping(value = "api/RegisterUser", consumes = "application/json", produces = "application/json")
    public RegisterUserResponse RegisterUser(@RequestBody RegisterUserRequest req) throws InvalidUserEmailException, InvalidUserPhoneNumberException, InvalidUserPasswordException, InvalidRequestException {
        return restTemplate.postForObject("http://"+ IP + ":" + userPort + "/user/RegisterUser/",req, RegisterUserResponse.class);
    }

    @GetMapping(value="/user/test")
    public String test(){
        return "User controller is working";
    }

    @PostMapping(value = "api/updatePicture", consumes = "application/json", produces = "application/json")
    public String updatePicture(@RequestBody UpdatePictureRequest req) throws Exception {
       return restTemplate.postForObject("http://"+ IP + ":" + userPort + "/user/updatePicture/",req, String.class);
    }


    @GetMapping(value="/ConfirmToken/{token}")
    public String ConfirmToken(@RequestParam("token") String token){
        return restTemplate.getForObject("http://"+ IP + ":" + userPort + "/user/ConfirmToken/"+token, String.class);

    }

    @PostMapping(value = "/api/LoginUser", consumes = "application/json", produces = "application/json")
    public LoginUserDTO Login(@RequestBody LoginUserRequest req) throws InvalidUserEmailException, InvalidUserPhoneNumberException, InvalidUserPasswordException, InvalidRequestException {
        return restTemplate.postForObject("http://"+ IP + ":" + userPort + "/user/LoginUser/",req, LoginUserDTO.class);
    }

    @GetMapping(value="/GetUser/{id}")
    public GetUserByUUIDDTO getUserByUUID(@PathVariable UUID id){
        return restTemplate.getForObject("http://"+ IP + ":" + userPort + "/user/GetUser/"+id, GetUserByUUIDDTO.class);

    }

    @GetMapping(value = "/api/acceptFriendRequest/{id}")
    public String acceptFriend(@PathVariable UUID id){
        restTemplate.getForObject("http://"+ IP + ":" + userPort + "/user/acceptFriendRequest/"+id, String.class);
        Friend friend = restTemplate.getForObject("http://"+ IP + ":" + userPort + "/user/getFriendRequest/"+id, Friend.class);
        CreateDirectChatRequest request = new CreateDirectChatRequest(friend.getFirstUser(),friend.getSecondUser());
        restTemplate.postForObject("http://"+ IP + ":" + chatPort + "/chat/createDirectChat", request,String.class);
        return "Done";
    }

    @GetMapping(value="api/getFriends/{id}")
    public List<UUID> getFriends(@PathVariable UUID id){
        return restTemplate.getForObject("http://"+ IP + ":" + userPort + "/user/getFriends/"+id, List.class);

    }

    @GetMapping(value="api/GetFriendRequests/{id}")
    public List<GetFriendRequestsResponse> getFriendRequests(@PathVariable UUID id){
        return restTemplate.getForObject("http://"+ IP + ":" + userPort + "/user/GetFriendRequests/"+id, List.class);

    }

    @GetMapping(value="api/populateFriends")
    public void mockFriends() {
        restTemplate.getForObject("http://"+ IP + ":" + userPort + "/user/populateFriends", String.class);
    }

    @GetMapping(value="api/deleteFriendRequest/{id}")
    public void deleteRequest(@PathVariable UUID id) {
        restTemplate.getForObject("http://"+ IP + ":" + userPort + "/user/deleteFriendRequest/"+id, List.class);

    }

    @GetMapping(value="api/removeFriend/{id}/{friendID}")
    public void deleteRequest(@PathVariable UUID id, @PathVariable UUID friendID){
        DirectChatResponseDTO chat = restTemplate.getForObject("http://"+ IP + ":" + chatPort + "/chat/getDirectChat/"+id+"/"+friendID, DirectChatResponseDTO.class);
        restTemplate.getForObject("http://"+ IP + ":" + chatPort + "/chat/deleteChat/"+ chat.getId(), String.class);
        restTemplate.getForObject("http://"+ IP + ":" + userPort + "/user/removeFriend/"+id+"/"+friendID, String.class);
    }



}


