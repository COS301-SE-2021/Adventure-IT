package com.adventureit.maincontroller.Controller;

import com.adventureit.chat.Entity.DirectMessage;
import com.adventureit.chat.Entity.GroupMessage;
import com.adventureit.chat.Requests.CreateDirectChatRequest;
import com.adventureit.chat.Requests.CreateGroupChatRequest;
import com.adventureit.chat.Requests.SendDirectMessageRequestDTO;
import com.adventureit.chat.Requests.SendGroupMessageRequestDTO;
import com.adventureit.chat.Responses.DirectChatResponseDTO;
import com.adventureit.chat.Responses.GroupChatResponseDTO;
import com.adventureit.maincontroller.Responses.DirectMessageResponseDTO;
import com.adventureit.maincontroller.Responses.GroupMessageResponseDTO;
import com.adventureit.userservice.Responses.GetUserByUUIDDTO;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/chat")
public class MainControllerChatReroute {
    @Autowired
    private EurekaClient eurekaClient;

    private RestTemplate restTemplate = new RestTemplate();

    private final String IP = "localhost";
    private final String adventurePort = "9001";
    private final String userPort = "9002";
    private final String chatPort = "9010";

    @GetMapping("/test")
    public String test(){
        return restTemplate.getForObject("http://"+ IP + ":" + chatPort + "/chat/test", String.class);
    }

    @PostMapping("/createDirectChat")
    public String createDirectChat(@RequestBody CreateDirectChatRequest req){
        return restTemplate.postForObject("http://"+ IP + ":" + chatPort + "/chat/createDirectChat",req, String.class);
    }

    @PostMapping("/createGroupChat")
    public String createDirectChat(@RequestBody CreateGroupChatRequest req){
        return restTemplate.postForObject("http://"+ IP + ":" + chatPort + "/chat/createGroupChat",req, String.class);
    }

    @GetMapping("/getGroupMessages/{id}")
    public List<GroupMessageResponseDTO> getGroupMessages(@PathVariable UUID id) throws Exception {
        GroupChatResponseDTO chat = restTemplate.getForObject("http://"+ IP + ":" + chatPort + "/chat/getGroupChat/" + id, GroupChatResponseDTO.class);
        GroupMessage message = null;
        GetUserByUUIDDTO user = null;
        List<GetUserByUUIDDTO> users = new ArrayList<>();
        List <GroupMessageResponseDTO> list = new ArrayList<>();

        if(chat.getMessages().isEmpty()){
            throw new Exception("No messages available");
        }

        for (UUID ID:chat.getMessages()) {
            message = restTemplate.getForObject("http://"+ IP + ":" + chatPort + "/chat/getGroupMessageByID/" + ID, GroupMessage.class);
            user = restTemplate.getForObject("http://"+ IP + ":" + userPort + "/user/GetUser/" + message.getSender(), GetUserByUUIDDTO.class);

            for (UUID x:chat.getParticipants()) {
                users.add(restTemplate.getForObject("http://"+ IP + ":" + userPort + "/user/GetUser/" + x, GetUserByUUIDDTO.class));
            }

            list.add(new GroupMessageResponseDTO(message.getId(),user,message.getMessage(), message.getTimestamp(),users,message.getRead()));
        }

        list.sort(new Comparator<GroupMessageResponseDTO>() {
            @Override
            public int compare(GroupMessageResponseDTO o1, GroupMessageResponseDTO o2) {
                return o1.getTimestamp().compareTo(o2.getTimestamp());
            }
        });

        return list;
    }

    @GetMapping("/getGroupChatByAdventureID/{id}")
    public GroupChatResponseDTO getGroupChat(@PathVariable UUID id) throws Exception {
        return restTemplate.getForObject("http://"+ IP + ":" + chatPort + "/chat/getGroupChatByAdventureID/" + id, GroupChatResponseDTO.class);
    }

    @PostMapping("/sendGroupMessage")
    public String sendGroupMessage(@RequestBody SendGroupMessageRequestDTO request) throws Exception {
        return restTemplate.postForObject("http://"+ IP + ":" + chatPort + "/chat/sendGroupMessage", request,String.class);
    }

    @PostMapping("/sendDirectMessage")
    public String sendDirectMessage(@RequestBody SendDirectMessageRequestDTO request) throws Exception {
        return restTemplate.postForObject("http://"+ IP + ":" + chatPort + "/chat/sendDirectMessage", request,String.class);
    }

    @GetMapping("/getDirectChat/{ID1}/{ID2}")
    public DirectChatResponseDTO getDirectChat(@PathVariable UUID ID1, @PathVariable UUID ID2) throws Exception {
        return restTemplate.getForObject("http://"+ IP + ":" + chatPort + "/chat/getDirectChat/" + ID1 + "/" + ID2, DirectChatResponseDTO.class);
    }

    @GetMapping("/getDirectMessages/{id}")
    public List<DirectMessageResponseDTO> getDirectMessages(@PathVariable UUID id) throws Exception {
        DirectChatResponseDTO chat = restTemplate.getForObject("http://"+ IP + ":" + chatPort + "/chat/getDirectChatByID/" + id, DirectChatResponseDTO.class);
        DirectMessage message = null;
        GetUserByUUIDDTO user = null;
        GetUserByUUIDDTO x = null;
        List <DirectMessageResponseDTO> list = new ArrayList<>();

        if(chat.getMessages().isEmpty()){
            throw new Exception("No messages available");
        }

        for (UUID ID:chat.getMessages()) {
            message = restTemplate.getForObject("http://"+ IP + ":" + chatPort + "/chat/getDirectMessageByID/" + ID, DirectMessage.class);
            user = restTemplate.getForObject("http://"+ IP + ":" + userPort + "/user/GetUser/" + message.getSender(), GetUserByUUIDDTO.class);
            x = restTemplate.getForObject("http://"+ IP + ":" + userPort + "/user/GetUser/" + message.getReceiver(), GetUserByUUIDDTO.class);

            list.add(new DirectMessageResponseDTO(message.getId(),user,x,message.getTimestamp(),message.getMessage(),message.getRead()));
        }

        list.sort(new Comparator<DirectMessageResponseDTO>() {
            @Override
            public int compare(DirectMessageResponseDTO o1, DirectMessageResponseDTO o2) {
                return o1.getTimestamp().compareTo(o2.getTimestamp());
            }
        });

        return list;
    }

}
