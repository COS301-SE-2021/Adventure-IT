package com.adventureit.maincontroller.controller;


import com.adventureit.maincontroller.service.MainControllerServiceImplementation;
import com.adventureit.shareddtos.chat.requests.CreateDirectChatRequest;
import com.adventureit.shareddtos.recommendation.request.CreateUserRequest;
import com.adventureit.shareddtos.user.requests.*;
import com.adventureit.shareddtos.user.responses.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class MainControllerUserReroute {

    private final RestTemplate restTemplate = new RestTemplate();
    private final MainControllerServiceImplementation service;

    private static final String INTERNET_PORT = "http://localhost";
    private static final String USER_PORT = "9002";
    private static final String LOCATION_PORT = "9006";
    private static final String RECOMMENDATION_PORT = "9013";
    private static final String CHAT_PORT = "9010";

    @Autowired
    public MainControllerUserReroute(MainControllerServiceImplementation service) {
        this.service = service;
    }

    @PostMapping(value = "registerUser", consumes = "application/json", produces = "application/json")
    public RegisterUserResponse registerUser(@RequestBody RegisterUserRequest req) throws Exception {
        String[] ports = {USER_PORT, RECOMMENDATION_PORT};
        service.pingCheck(ports,restTemplate);
        CreateUserRequest req2 = new CreateUserRequest(req.getUserID());
        restTemplate.postForObject(INTERNET_PORT + ":" + RECOMMENDATION_PORT + "/recommendation/add/user", req2, String.class);
        return restTemplate.postForObject(INTERNET_PORT + ":" + USER_PORT + "/user/registerUser/",req, RegisterUserResponse.class);
    }

    @GetMapping(value="test")
    public String test(){
        return "User controller is working";
    }
    @PostMapping(value = "updatePicture", consumes = "application/json", produces = "application/json")
    public String updatePicture(@RequestBody UpdatePictureRequest req) throws Exception {
        String[] ports = {USER_PORT};
        service.pingCheck(ports,restTemplate);
       return restTemplate.postForObject(INTERNET_PORT + ":" + USER_PORT + "/user/updatePicture/",req, String.class);
    }

    @GetMapping(value="/confirmToken/{token}")
    public String confirmToken(@RequestParam("token") String token) throws Exception {
        String[] ports = {USER_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + "/user/confirmToken/"+token, String.class);

    }

    @PostMapping(value = "loginUser", consumes = "application/json", produces = "application/json")
    public LoginUserDTO login(@RequestBody LoginUserRequest req) throws Exception {
        String[] ports = {USER_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.postForObject(INTERNET_PORT + ":" + USER_PORT + "/user/loginUser/",req, LoginUserDTO.class);
    }

    @GetMapping(value="/getUser/{id}")
    public GetUserByUUIDDTO getUserByUUID(@PathVariable UUID id) throws Exception {
        String[] ports = {USER_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + "/user/getUser/"+id, GetUserByUUIDDTO.class);

    }

    @GetMapping(value = "acceptFriendRequest/{id}")
    public String acceptFriend(@PathVariable UUID id) throws Exception {
        String[] ports = {USER_PORT, CHAT_PORT};
        service.pingCheck(ports,restTemplate);
        FriendDTO friend = restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + "/user/getFriendRequest/"+id, FriendDTO.class);
        restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + "/user/acceptFriendRequest/"+id, String.class);
        assert friend != null;
        CreateDirectChatRequest request = new CreateDirectChatRequest(friend.getFirstUser(),friend.getSecondUser());
        restTemplate.postForObject(INTERNET_PORT + ":" + CHAT_PORT + "/chat/createDirectChat", request,String.class);
        return "Done";
    }

    @GetMapping(value="getFriends/{id}")
    public List<UUID> getFriends(@PathVariable UUID id) throws Exception {
        String[] ports = {USER_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + "/user/getFriends/"+id, List.class);
    }

    @GetMapping(value="getFriendRequests/{id}")
    public List<GetFriendRequestsResponse> getFriendRequests(@PathVariable UUID id) throws Exception {
        String[] ports = {USER_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + "/user/getFriendRequests/"+id, List.class);

    }

    @GetMapping(value="populateFriends")
    public void mockFriends() throws Exception {
        String[] ports = {USER_PORT};
        service.pingCheck(ports,restTemplate);
        restTemplate.getForObject( INTERNET_PORT + ":" + USER_PORT + "/user/populateFriends", String.class);
    }

    @GetMapping(value="deleteFriendRequest/{id}")
    public void deleteRequest(@PathVariable UUID id) throws Exception {
        String[] ports = {USER_PORT};
        service.pingCheck(ports,restTemplate);
        restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + "/user/deleteFriendRequest/"+id, List.class);

    }

    @GetMapping(value="removeFriend/{id}/{friendID}")
    public void deleteRequest(@PathVariable UUID id, @PathVariable UUID friendID) throws Exception {
        //add in delete direct chat here
        String[] ports = {USER_PORT};
        service.pingCheck(ports,restTemplate);
        restTemplate.getForObject( INTERNET_PORT + ":" + USER_PORT + "/user/removeFriend/"+id+"/"+friendID, String.class);
    }


    @GetMapping(value = "getByUserName/{userName}")
    public UUID getUserIDByUserName(@PathVariable String userName) throws Exception {
        String[] ports = {USER_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + "/user/getByUserName/"+ userName, UUID.class);
    }

    @GetMapping(value = "createFriendRequest/{id1}/{id2}")
    public void createFriendRequest(@PathVariable String id1, @PathVariable String id2) throws Exception {
        String[] ports = {USER_PORT};
        service.pingCheck(ports,restTemplate);
        restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + "/user/createFriendRequest/"+ id1+"/"+id2, UUID.class);
    }

    @GetMapping("getFriendRequest/{id}")
    public FriendDTO getFriendRequest(@PathVariable UUID id) throws Exception {
        String[] ports = {USER_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + "/user/getFriendRequest/"+ id, FriendDTO.class);
    }

    @PostMapping("editUserProfile")
    public String editUseProfile(@RequestBody EditUserProfileRequest req) throws Exception {
        String[] ports = {USER_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.postForObject(INTERNET_PORT + ":" + USER_PORT + "/user/editUserProfile", req,String.class);
    }

    @PostMapping("setEmergencyContact")
    public String setEmergencyContact(@RequestBody SetUserEmergencyContactRequest req) throws Exception {
        String[] ports = {USER_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.postForObject(INTERNET_PORT + ":" + USER_PORT + "/user/setEmergencyContact/",req, String.class);
    }

    @GetMapping("getEmergencyContact/{userId}")
    public String setEmergencyContact(@PathVariable UUID userId){
        return restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + "/user/getEmergencyContact/"+ userId, String.class);
    }

    @GetMapping("getUserTheme/{userId}")
    public Boolean getUserTheme( @PathVariable UUID userId) throws Exception {
        String[] ports = {USER_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + "/user/getUserTheme/"+ userId, Boolean.class);
    }

    @PostMapping("setUserTheme")
    public String setUserTheme( @RequestBody SetUserThemeRequest req) throws Exception {
        String[] ports = {USER_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.postForObject(INTERNET_PORT + ":" + USER_PORT + "/user/setUserTheme/",req, String.class);
    }

    @GetMapping("likeLocation/{userID}/{locationID}")
    public void likeLocation(@PathVariable UUID userID, @PathVariable UUID locationID) throws Exception {
        String[] ports = {USER_PORT, LOCATION_PORT};
        service.pingCheck(ports,restTemplate);
        restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + "/user/addLikedLocation/"+ userID + "/" + locationID, String.class);
        restTemplate.getForObject(INTERNET_PORT + ":" + LOCATION_PORT + "/location/addLike/" + locationID, String.class);
    }

}


