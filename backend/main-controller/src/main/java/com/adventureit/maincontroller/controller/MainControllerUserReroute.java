package com.adventureit.maincontroller.controller;


import com.adventureit.shareddtos.chat.requests.CreateDirectChatRequest;
import com.adventureit.shareddtos.recommendation.request.CreateUserRequest;
import com.adventureit.shareddtos.user.requests.*;
import com.adventureit.shareddtos.user.responses.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class MainControllerUserReroute {

    private final RestTemplate restTemplate = new RestTemplate();
    private MainControllerServiceImplementation service;

    private final String IP = "http://localhost";
    private final String userPort = "9002";
    private final String locationPort = "9006";
    private final String recommendationPort = "9013";
    private final String chatPort = "9010";

    @Autowired
    public MainControllerUserReroute(MainControllerServiceImplementation service) {
        this.service = service;
    }

    @PostMapping(value = "registerUser", consumes = "application/json", produces = "application/json")
    public RegisterUserResponse registerUser(@RequestBody RegisterUserRequest req) throws Exception {
        String[] ports = {userPort,recommendationPort};
        service.pingCheck(ports,restTemplate);
        CreateUserRequest req2 = new CreateUserRequest(req.getUserID());
        restTemplate.postForObject(IP + ":" + recommendationPort + "/recommendation/add/user", req2, ResponseEntity.class);
        return restTemplate.postForObject(IP + ":" + userPort + "/user/registerUser/",req, RegisterUserResponse.class);
    }

    @GetMapping(value="test")
    public String test(){
        return "User controller is working";
    }
    @PostMapping(value = "updatePicture", consumes = "application/json", produces = "application/json")
    public String updatePicture(@RequestBody UpdatePictureRequest req) throws Exception {
        String[] ports = {userPort};
        service.pingCheck(ports,restTemplate);
       return restTemplate.postForObject(IP + ":" + userPort + "/user/updatePicture/",req, String.class);
    }

    @GetMapping(value="/confirmToken/{token}")
    public String confirmToken(@RequestParam("token") String token) throws Exception {
        String[] ports = {userPort};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(IP + ":" + userPort + "/user/confirmToken/"+token, String.class);

    }

    @PostMapping(value = "loginUser", consumes = "application/json", produces = "application/json")
    public LoginUserDTO login(@RequestBody LoginUserRequest req) throws Exception {
        String[] ports = {userPort};
        service.pingCheck(ports,restTemplate);
        return restTemplate.postForObject(IP + ":" + userPort + "/user/loginUser/",req, LoginUserDTO.class);
    }

    @GetMapping(value="/getUser/{id}")
    public GetUserByUUIDDTO getUserByUUID(@PathVariable UUID id) throws Exception {
        String[] ports = {userPort};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(IP + ":" + userPort + "/user/getUser/"+id, GetUserByUUIDDTO.class);

    }

    @GetMapping(value = "acceptFriendRequest/{id}")
    public String acceptFriend(@PathVariable UUID id) throws Exception {
        String[] ports = {userPort,chatPort};
        service.pingCheck(ports,restTemplate);
        FriendDTO friend = restTemplate.getForObject(IP + ":" + userPort + "/user/getFriendRequest/"+id, FriendDTO.class);
        restTemplate.getForObject(IP + ":" + userPort + "/user/acceptFriendRequest/"+id, String.class);
        assert friend != null;
        CreateDirectChatRequest request = new CreateDirectChatRequest(friend.getFirstUser(),friend.getSecondUser());
        restTemplate.postForObject(IP + ":" + chatPort + "/chat/createDirectChat", request,String.class);
        return "Done";
    }

    @GetMapping(value="getFriends/{id}")
    public List<UUID> getFriends(@PathVariable UUID id) throws Exception {
        String[] ports = {userPort};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(IP + ":" + userPort + "/user/getFriends/"+id, List.class);
    }

    @GetMapping(value="getFriendRequests/{id}")
    public List<GetFriendRequestsResponse> getFriendRequests(@PathVariable UUID id) throws Exception {
        String[] ports = {userPort};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(IP + ":" + userPort + "/user/getFriendRequests/"+id, List.class);

    }

    @GetMapping(value="populateFriends")
    public void mockFriends() throws Exception {
        String[] ports = {userPort};
        service.pingCheck(ports,restTemplate);
        restTemplate.getForObject( IP + ":" + userPort + "/user/populateFriends", String.class);
    }

    @GetMapping(value="deleteFriendRequest/{id}")
    public void deleteRequest(@PathVariable UUID id) throws Exception {
        String[] ports = {userPort};
        service.pingCheck(ports,restTemplate);
        restTemplate.getForObject(IP + ":" + userPort + "/user/deleteFriendRequest/"+id, List.class);

    }

    @GetMapping(value="removeFriend/{id}/{friendID}")
    public void deleteRequest(@PathVariable UUID id, @PathVariable UUID friendID) throws Exception {
        //add in delete direct chat here
        String[] ports = {userPort};
        service.pingCheck(ports,restTemplate);
        restTemplate.getForObject( IP + ":" + userPort + "/user/removeFriend/"+id+"/"+friendID, String.class);
    }


    @GetMapping(value = "getByUserName/{userName}")
    public UUID getUserIDByUserName(@PathVariable String userName) throws Exception {
        String[] ports = {userPort};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(IP + ":" + userPort + "/user/getByUserName/"+ userName, UUID.class);
    }

    @GetMapping(value = "createFriendRequest/{id1}/{id2}")
    public void createFriendRequest(@PathVariable String id1, @PathVariable String id2) throws Exception {
        String[] ports = {userPort};
        service.pingCheck(ports,restTemplate);
        restTemplate.getForObject(IP + ":" + userPort + "/user/createFriendRequest/"+ id1+"/"+id2, UUID.class);
    }

    @GetMapping("getFriendRequest/{id}")
    public FriendDTO getFriendRequest(@PathVariable UUID id) throws Exception {
        String[] ports = {userPort};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(IP + ":" + userPort + "/user/getFriendRequest/"+ id, FriendDTO.class);
    }

    @PostMapping("editUserProfile")
    public String editUseProfile(@RequestBody EditUserProfileRequest req) throws Exception {
        String[] ports = {userPort};
        service.pingCheck(ports,restTemplate);
        return restTemplate.postForObject(IP + ":" + userPort + "/user/editUserProfile", req,String.class);
    }

    @PostMapping("setEmergencyContact")
    public String setEmergencyContact(@RequestBody SetUserEmergencyContactRequest req){
        String[] ports = {userPort};
        service.pingCheck(ports,restTemplate);
        return restTemplate.postForObject(IP + ":" + userPort + "/user/setEmergencyContact/",req, String.class);
    }

    @GetMapping("getEmergencyContact/{userId}")
    public String setEmergencyContact(@PathVariable UUID userId){
        return restTemplate.getForObject(IP + ":" + userPort + "/user/getEmergencyContact/"+ userId, String.class);
    }

    @GetMapping("getUserTheme/{userId}")
    public Boolean getUserTheme( @PathVariable UUID userId) throws Exception {
        String[] ports = {userPort};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(IP + ":" + userPort + "/user/getUserTheme/"+ userId, Boolean.class);
    }

    @PostMapping("setUserTheme")
    public String setUserTheme( @PathVariable UUID userId,@PathVariable Boolean bool) throws Exception {
        String[] ports = {userPort};
        service.pingCheck(ports,restTemplate);
        return restTemplate.postForObject(IP + ":" + userPort + "/user/setUserTheme/",req, String.class);
    }

    @GetMapping("likeLocation/{userID}/{locationID}")
    public void likeLocation(@PathVariable UUID userID, @PathVariable UUID locationID) throws Exception {
        String[] ports = {userPort,locationPort};
        service.pingCheck(ports,restTemplate);
        restTemplate.getForObject(IP + ":" + userPort + "/user/addLikedLocation/"+ userID + "/" + locationID, String.class);
        restTemplate.getForObject(IP + ":" + locationPort + "/location/addLike/" + locationID, String.class);
    }

}


