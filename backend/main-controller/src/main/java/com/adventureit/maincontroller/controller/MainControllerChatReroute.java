package com.adventureit.maincontroller.controller;

import com.adventureit.chat.entity.DirectMessage;
import com.adventureit.chat.entity.GroupMessage;
import com.adventureit.chat.requests.CreateDirectChatRequest;
import com.adventureit.chat.requests.CreateGroupChatRequest;
import com.adventureit.chat.requests.SendDirectMessageRequestDTO;
import com.adventureit.chat.requests.SendGroupMessageRequestDTO;
import com.adventureit.chat.responses.DirectChatResponseDTO;
import com.adventureit.chat.responses.GroupChatResponseDTO;


import com.adventureit.maincontroller.responses.DirectMessageResponseDTO;
import com.adventureit.maincontroller.responses.GroupMessageResponseDTO;
import com.adventureit.maincontroller.responses.MainDirectChatResponseDTO;
import com.adventureit.maincontroller.responses.MainGroupChatResponseDTO;
import com.adventureit.userservice.responses.GetUserByUUIDDTO;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/chat")
public class MainControllerChatReroute {

    private final RestTemplate restTemplate = new RestTemplate();

    private final String IP = "http://localhost";
    private final String userPort = "9002";
    private final String chatPort = "9010";

    @GetMapping("/test")
    public String test(){
        return restTemplate.getForObject(IP + ":" + chatPort + "/chat/test", String.class);
    }

    @PostMapping("/createDirectChat")
    public String createDirectChat(@RequestBody CreateDirectChatRequest req){
        return restTemplate.postForObject(IP + ":" + chatPort + "/chat/createDirectChat",req, String.class);
    }

    @PostMapping("/createGroupChat")
    public String createDirectChat(@RequestBody CreateGroupChatRequest req){
        return restTemplate.postForObject(IP + ":" + chatPort + "/chat/createGroupChat",req, String.class);
    }

    @GetMapping("/getGroupMessages/{id}")
    public List<GroupMessageResponseDTO> getGroupMessages(@PathVariable UUID id) throws Exception {
        GroupChatResponseDTO chat = restTemplate.getForObject(IP + ":" + chatPort + "/chat/getGroupChat/" + id, GroupChatResponseDTO.class);
        GroupMessage message;
        GetUserByUUIDDTO user;
        List<GetUserByUUIDDTO> users = new ArrayList<>();
        List <GroupMessageResponseDTO> list = new ArrayList<>();

        assert chat != null;

        for (UUID ID:chat.getMessages()) {
            message = restTemplate.getForObject(IP + ":" + chatPort + "/chat/getGroupMessageByID/" + ID, GroupMessage.class);
            assert message != null;
            user = restTemplate.getForObject(IP + ":" + userPort + "/user/getUser/" + message.getSender(), GetUserByUUIDDTO.class);

            for (UUID x:chat.getParticipants()) {
                users.add(restTemplate.getForObject(IP + ":" + userPort + "/user/getUser/" + x, GetUserByUUIDDTO.class));
            }

            list.add(new GroupMessageResponseDTO(message.getId(),user,message.getPayload(), message.getTimestamp(),users,message.getRead()));
        }

        list.sort(new Comparator<>() {
            @Override
            public int compare(GroupMessageResponseDTO o1, GroupMessageResponseDTO o2) {
                return o1.getTimestamp().compareTo(o2.getTimestamp());
            }
        });

        return list;
    }

    @GetMapping("/getGroupChatByAdventureID/{id}")
    public MainGroupChatResponseDTO getGroupChat(@PathVariable UUID id){
        GroupChatResponseDTO responseDTO = restTemplate.getForObject(IP + ":" + chatPort + "/chat/getGroupChatByAdventureID/" + id, GroupChatResponseDTO.class);
        assert responseDTO != null;
        return new MainGroupChatResponseDTO(responseDTO.getId(),responseDTO.getAdventureID(),responseDTO.getParticipants(),responseDTO.getName(),responseDTO.getColors());
    }

    @PostMapping("/sendGroupMessage")
    public String sendGroupMessage(@RequestBody SendGroupMessageRequestDTO request){
        SendGroupMessageRequestDTO req = new SendGroupMessageRequestDTO(request.getChatID(),request.getSender(),request.getMsg());
        return restTemplate.postForObject(IP + ":" + chatPort + "/chat/sendGroupMessage",req,String.class);
    }

    @PostMapping("/sendDirectMessage")
    public String sendDirectMessage(@RequestBody SendDirectMessageRequestDTO request){
        return restTemplate.postForObject(IP + ":" + chatPort + "/chat/sendDirectMessage", request,String.class);
    }

    @GetMapping("/getDirectChat/{id1}/{id2}")
    public MainDirectChatResponseDTO getDirectChat(@PathVariable UUID id1, @PathVariable UUID id2){
        DirectChatResponseDTO responseDTO = restTemplate.getForObject(IP + ":" + chatPort + "/chat/getDirectChat/" + id1 + "/" + id2, DirectChatResponseDTO.class);
        assert responseDTO != null;
        return new MainDirectChatResponseDTO(responseDTO.getId(),responseDTO.getParticipants());
    }

    @GetMapping("/getDirectMessages/{id}")
    public List<DirectMessageResponseDTO> getDirectMessages(@PathVariable UUID id) throws Exception {
        DirectChatResponseDTO chat = restTemplate.getForObject(IP + ":" + chatPort + "/chat/getDirectChatByID/" + id, DirectChatResponseDTO.class);
        DirectMessage message;
        GetUserByUUIDDTO user;
        GetUserByUUIDDTO x;
        List <DirectMessageResponseDTO> list = new ArrayList<>();

        assert chat != null;
        if(chat.getMessages().isEmpty()){
            throw new Exception("No messages available");
        }

        for (UUID ID:chat.getMessages()) {
            message = restTemplate.getForObject(IP + ":" + chatPort + "/chat/getDirectMessageByID/" + ID, DirectMessage.class);
            assert message != null;
            user = restTemplate.getForObject(IP + ":" + userPort + "/user/getUser/" + message.getSender(), GetUserByUUIDDTO.class);
            x = restTemplate.getForObject(IP + ":" + userPort + "/user/getUser/" + message.getReceiver(), GetUserByUUIDDTO.class);

            list.add(new DirectMessageResponseDTO(message.getId(),user,x,message.getTimestamp(),message.getPayload(),message.getRead()));
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
