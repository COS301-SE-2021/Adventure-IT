package com.adventureit.maincontroller.controller;


import com.adventureit.maincontroller.exceptions.ControllerNotAvailable;
import com.adventureit.maincontroller.service.MainControllerServiceImplementation;
import com.adventureit.shareddtos.chat.requests.CreateDirectChatRequest;
import com.adventureit.shareddtos.chat.responses.DirectChatResponseDTO;
import com.adventureit.shareddtos.media.responses.MediaResponseDTO;
import com.adventureit.shareddtos.recommendation.request.CreateUserRequest;
import com.adventureit.shareddtos.user.requests.*;
import com.adventureit.shareddtos.user.responses.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;

@RestController
@RequestMapping("/user")
public class MainControllerUserReroute {

    private RestTemplate restTemplate = new RestTemplate();
    private final MainControllerServiceImplementation service;

    private static final String INTERNET_PORT = "http://internal-microservice-load-balancer-1572194202.us-east-2.elb.amazonaws.com";
    private static final String USER_PORT = "9002";
    private static final String LOCATION_PORT = "9006";
    private static final String RECOMMENDATION_PORT = "9013";
    private static final String CHAT_PORT = "9010";
    private static final String ERROR = "Empty Error";

    @Autowired
    public MainControllerUserReroute(MainControllerServiceImplementation service) {
        this.service = service;
    }

    @GetMapping("/test")
    public String test(){
        return restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + "/user/test", String.class);
    }

    @PostMapping(value = "registerUser", consumes = "application/json", produces = "application/json")
    public RegisterUserResponse registerUser(@RequestBody RegisterUserRequest req) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {USER_PORT, RECOMMENDATION_PORT,LOCATION_PORT};
        service.pingCheck(ports,restTemplate);
        String id = req.getUserID().toString();
        if(id.equals("")) {
            throw new ControllerNotAvailable(ERROR);
        }
        CreateUserRequest req2 = new CreateUserRequest(UUID.fromString(id));
        restTemplate.postForObject(INTERNET_PORT + ":" + RECOMMENDATION_PORT + "/recommendation/add/user", req2, String.class);
        restTemplate.getForObject(INTERNET_PORT+":"+LOCATION_PORT+"/location/storeCurrentLocation/" + UUID.fromString(id) + "/0/0", String.class);
        return restTemplate.postForObject(INTERNET_PORT + ":" + USER_PORT + "/user/registerUser",req, RegisterUserResponse.class);
    }

    @PostMapping(value = "/updatePicture")
    public HttpStatus updatePicture(@RequestPart MultipartFile file, @RequestParam("userid") UUID userId) throws ControllerNotAvailable, InterruptedException, IOException {
        String[] ports = {USER_PORT};
        service.pingCheck(ports,restTemplate);

        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            Boolean bool = convFile.createNewFile();
            if (bool.equals(true)) {
                System.out.println("Successfull");
            }
            fos.write(file.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
        MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
        bodyMap.add("file", new FileSystemResource(convFile));
        bodyMap.add("userid", userId.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);
        restTemplate = new RestTemplate();

        HttpStatus status = restTemplate.postForObject(INTERNET_PORT + ":" + USER_PORT + "/user/updatePicture/",requestEntity, HttpStatus.class);
        Files.delete(Path.of(convFile.getPath()));
        return status;
    }

    @GetMapping(value="/confirmToken/{token}")
    public String confirmToken(@RequestParam("token") String token) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {USER_PORT};
        service.pingCheck(ports,restTemplate);
        String tok = token;
        if(tok.equals("")) {
            throw new ControllerNotAvailable(ERROR);
        }
        return restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + "/user/confirmToken/" + tok, String.class);
    }

    @PostMapping(value = "loginUser", consumes = "application/json", produces = "application/json")
    public LoginUserDTO login(@RequestBody LoginUserRequest req) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {USER_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.postForObject(INTERNET_PORT + ":" + USER_PORT + "/user/loginUser/",req, LoginUserDTO.class);
    }

    @GetMapping(value="/getUser/{id}")
    public GetUserByUUIDDTO getUserByUUID(@PathVariable UUID id) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {USER_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + "/user/getUser/"+id, GetUserByUUIDDTO.class);

    }

    @GetMapping(value = "acceptFriendRequest/{id}")
    public String acceptFriend(@PathVariable UUID id) throws ControllerNotAvailable, InterruptedException {
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
    public List<UUID> getFriends(@PathVariable UUID id) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {USER_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + "/user/getFriends/"+id, List.class);
    }

    @GetMapping(value="getFriendRequests/{id}")
    public List<GetFriendRequestsResponse> getFriendRequests(@PathVariable UUID id) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {USER_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + "/user/getFriendRequests/"+id, List.class);

    }

    @GetMapping(value="populateFriends")
    public void mockFriends() throws ControllerNotAvailable, InterruptedException {
        String[] ports = {USER_PORT};
        service.pingCheck(ports,restTemplate);
        restTemplate.getForObject( INTERNET_PORT + ":" + USER_PORT + "/user/populateFriends", String.class);
    }

    @GetMapping(value="deleteFriendRequest/{id}")
    public void deleteRequest(@PathVariable UUID id) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {USER_PORT};
        service.pingCheck(ports,restTemplate);
        restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + "/user/deleteFriendRequest/"+id, List.class);

    }

    @GetMapping(value="removeFriend/{id}/{friendID}")
    public void deleteRequest(@PathVariable UUID id, @PathVariable UUID friendID) throws ControllerNotAvailable, InterruptedException {
        DirectChatResponseDTO responseDTO = restTemplate.getForObject(INTERNET_PORT + ":" + CHAT_PORT + "/chat/getDirectChat/" + id + "/" + friendID, DirectChatResponseDTO.class);
        assert responseDTO != null;
        String[] ports = {USER_PORT};
        service.pingCheck(ports,restTemplate);
        UUID chatID = responseDTO.getId();
        if(chatID.toString().equals("")) {
            throw new ControllerNotAvailable(ERROR);
        }
        restTemplate.getForObject( INTERNET_PORT + ":" + CHAT_PORT + "/chat/deleteChat/" + chatID, String.class);
        restTemplate.getForObject( INTERNET_PORT + ":" + USER_PORT + "/user/removeFriend/"+id+"/"+friendID, String.class);
    }


    @GetMapping(value = "getByUserName/{userName}")
    public UUID getUserIDByUserName(@PathVariable String userName) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {USER_PORT};
        service.pingCheck(ports,restTemplate);
        String uName = userName;
        if(uName.equals("")) {
            throw new ControllerNotAvailable(ERROR);
        }
        return restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + "/user/getByUserName/"+ uName, UUID.class);
    }

    @GetMapping(value = "createFriendRequest/{id1}/{id2}")
    public void createFriendRequest(@PathVariable String id1, @PathVariable String id2) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {USER_PORT};
        service.pingCheck(ports,restTemplate);
        String fid = id1;
        if(fid.equals("")) {
            throw new ControllerNotAvailable(ERROR);
        }
        String sid = id2;
        if(sid.equals("")) {
            throw new ControllerNotAvailable(ERROR);
        }
        restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + "/user/createFriendRequest/"+ fid + "/" + sid, UUID.class);
    }

    @GetMapping("getFriendRequest/{id}")
    public FriendDTO getFriendRequest(@PathVariable UUID id) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {USER_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + "/user/getFriendRequest/"+ id, FriendDTO.class);
    }

    @PostMapping("editUserProfile")
    public String editUseProfile(@RequestBody EditUserProfileRequest req) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {USER_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.postForObject(INTERNET_PORT + ":" + USER_PORT + "/user/editUserProfile", req,String.class);
    }

    @PostMapping("setEmergencyContact")
    public String setEmergencyContact(@RequestBody SetUserEmergencyContactRequest req) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {USER_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.postForObject(INTERNET_PORT + ":" + USER_PORT + "/user/setEmergencyContact/",req, String.class);
    }

    @GetMapping("getEmergencyContact/{userId}")
    public String setEmergencyContact(@PathVariable UUID userId){
        return restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + "/user/getEmergencyContact/"+ userId, String.class);
    }

    @GetMapping("getUserTheme/{userId}")
    public Boolean getUserTheme( @PathVariable UUID userId) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {USER_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + "/user/getUserTheme/"+ userId, Boolean.class);
    }

    @PostMapping("setUserTheme")
    public String setUserTheme( @RequestBody SetUserThemeRequest req) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {USER_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.postForObject(INTERNET_PORT + ":" + USER_PORT + "/user/setUserTheme/",req, String.class);
    }

    @GetMapping("likeLocation/{userID}/{locationID}")
    public void likeLocation(@PathVariable UUID userID, @PathVariable UUID locationID) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {USER_PORT, LOCATION_PORT};
        service.pingCheck(ports,restTemplate);
        restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + "/user/addLikedLocation/"+ userID + "/" + locationID, String.class);
        restTemplate.getForObject(INTERNET_PORT + ":" + LOCATION_PORT + "/location/addLike/" + locationID, String.class);
    }

    @GetMapping("getNotificationSettings/{userId}")
    public boolean getNotificationSettings(@PathVariable UUID userId) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {USER_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + "/user/getNotificationSettings/"+ userId, boolean.class);
    }

    @GetMapping("setNotificationSettings/{userId}")
    public void setNotificationSettings(@PathVariable UUID userId) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {USER_PORT};
        service.pingCheck(ports,restTemplate);
        restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + "/user/setNotificationSettings/"+ userId, String.class);
    }

    @GetMapping("viewPicture/{id}")
    public ResponseEntity<byte[]> viewPicture(@PathVariable UUID id) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {USER_PORT};
        service.pingCheck(ports,restTemplate);
        MediaResponseDTO responseDTO = restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + "/user/viewPicture/" + id, MediaResponseDTO.class);
        assert responseDTO != null;
        return new ResponseEntity<>(responseDTO.getContent(), responseDTO.getHeaders(), HttpStatus.OK);
    }

    @GetMapping(value = "getFriendProfiles/{id}")
    public List<GetUserByUUIDDTO> getFriendProfiles(@PathVariable UUID id) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {USER_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + "/user/getFriendProfiles/"+ id, List.class);
    }
}


