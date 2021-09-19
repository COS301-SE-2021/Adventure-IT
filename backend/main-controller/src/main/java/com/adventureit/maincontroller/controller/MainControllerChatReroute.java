package com.adventureit.maincontroller.controller;

import com.adventureit.maincontroller.exceptions.ControllerNotAvailable;
import com.adventureit.maincontroller.service.MainControllerServiceImplementation;
import com.adventureit.shareddtos.adventure.AdventureDTO;
import com.adventureit.shareddtos.chat.MessageDTO;
import com.adventureit.shareddtos.chat.requests.CreateDirectChatRequest;
import com.adventureit.shareddtos.chat.requests.CreateGroupChatRequest;
import com.adventureit.shareddtos.chat.requests.SendDirectMessageRequestDTO;
import com.adventureit.shareddtos.chat.requests.SendGroupMessageRequestDTO;
import com.adventureit.shareddtos.chat.responses.DirectChatResponseDTO;
import com.adventureit.shareddtos.chat.responses.GroupChatResponseDTO;


import com.adventureit.maincontroller.responses.DirectMessageResponseDTO;
import com.adventureit.maincontroller.responses.GroupMessageResponseDTO;
import com.adventureit.maincontroller.responses.MainDirectChatResponseDTO;
import com.adventureit.maincontroller.responses.MainGroupChatResponseDTO;
import com.adventureit.shareddtos.notification.requests.SendFirebaseNotificationRequest;
import com.adventureit.shareddtos.notification.requests.SendFirebaseNotificationsRequest;
import com.adventureit.shareddtos.user.responses.GetUserByUUIDDTO;
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
    private final MainControllerServiceImplementation service;

    private static final String INTERNET_PORT = "http://localhost";
    private static final String USER_PORT = "9002";
    private static final String CHAT_PORT = "9010";
    private static final String ADVENTURE_PORT ="9001";
    private static final String NOTIFICATION_PORT ="9004";
    private static final String GET_USER = "/user/getUser/";

    public MainControllerChatReroute(MainControllerServiceImplementation service) {
        this.service = service;
    }

    @GetMapping("/test")
    public String test(){
        return restTemplate.getForObject(INTERNET_PORT + ":" + CHAT_PORT + "/chat/test", String.class);
    }

    @PostMapping("/createDirectChat")
    public String createDirectChat(@RequestBody CreateDirectChatRequest req) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {CHAT_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.postForObject(INTERNET_PORT + ":" + CHAT_PORT + "/chat/createDirectChat",req, String.class);
    }

    @PostMapping("/createGroupChat")
    public String createDirectChat(@RequestBody CreateGroupChatRequest req) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {CHAT_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.postForObject(INTERNET_PORT + ":" + CHAT_PORT + "/chat/createGroupChat",req, String.class);
    }

    @GetMapping("/getGroupMessages/{id}")
    public List<GroupMessageResponseDTO> getGroupMessages(@PathVariable UUID id) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {CHAT_PORT, USER_PORT};
        service.pingCheck(ports,restTemplate);
        GroupChatResponseDTO chat = restTemplate.getForObject(INTERNET_PORT + ":" + CHAT_PORT + "/chat/getGroupChat/" + id, GroupChatResponseDTO.class);
        List<UUID> usersIds=new ArrayList<>();
        List<GetUserByUUIDDTO> users=new ArrayList<>();
        List <GroupMessageResponseDTO> list=new ArrayList<>();
        assert chat != null;

        for(MessageDTO message: chat.getMessages())
        {
           int index=usersIds.indexOf(message.getSender());
           if(index==-1) {
               usersIds.add(message.getSender());

           }
        }

        if(usersIds.size() == 1){
            GetUserByUUIDDTO user =restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + GET_USER + usersIds.get(0), GetUserByUUIDDTO.class);
            users.add(user);
        }
        else {
            users = restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + "/user/getUsers/" + usersIds, List.class);
        }

        for (MessageDTO message: chat.getMessages()) {
           int index=usersIds.indexOf(message.getSender());
            assert users != null;
            list.add(new GroupMessageResponseDTO(message.getId(),users.get(index),message.getPayload(), message.getTimestamp()));
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
    public MainGroupChatResponseDTO getGroupChat(@PathVariable UUID id) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {CHAT_PORT};
        service.pingCheck(ports,restTemplate);
        GroupChatResponseDTO responseDTO = restTemplate.getForObject(INTERNET_PORT + ":" + CHAT_PORT + "/chat/getGroupChatByAdventureID/" + id, GroupChatResponseDTO.class);
        assert responseDTO != null;

        return new MainGroupChatResponseDTO(responseDTO.getId(),responseDTO.getAdventureID(),responseDTO.getParticipants(),responseDTO.getName(),responseDTO.getColors());
    }

    @PostMapping("/sendGroupMessage")
    public String sendGroupMessage(@RequestBody SendGroupMessageRequestDTO request) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {CHAT_PORT};
        service.pingCheck(ports,restTemplate);
        SendGroupMessageRequestDTO req = new SendGroupMessageRequestDTO(request.getChatID(),request.getSender(),request.getMsg());

        // Get associated chat
        GroupChatResponseDTO chat = restTemplate.getForObject(INTERNET_PORT + ":" + CHAT_PORT + "/chat/getGroupChat/" + req.getChatID(), GroupChatResponseDTO.class);

        // Get associated adventure
        assert chat != null;
        AdventureDTO adventure = restTemplate.getForObject(INTERNET_PORT + ":" + ADVENTURE_PORT + "/adventure/getAdventureByUUID/" + chat.getAdventureID(), AdventureDTO.class);

        // Get all participants of chat
        List<UUID> users = chat.getParticipants();
        users.remove(request.getSender());

        // Send notification to all participants
        assert adventure != null;
        SendFirebaseNotificationsRequest notifReq = new SendFirebaseNotificationsRequest(users, "New Group Message", "Adventure: " + adventure.getName(), null);
        restTemplate.postForObject(INTERNET_PORT + ":" + NOTIFICATION_PORT + "/notification/sendFirebaseNotifications",notifReq, String.class);

        return restTemplate.postForObject(INTERNET_PORT + ":" + CHAT_PORT + "/chat/sendGroupMessage",req,String.class);
    }

    @PostMapping("/sendDirectMessage")
    public String sendDirectMessage(@RequestBody SendDirectMessageRequestDTO request) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {CHAT_PORT};
        service.pingCheck(ports,restTemplate);

        GetUserByUUIDDTO user =restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + GET_USER + request.getSender(), GetUserByUUIDDTO.class);
        assert user != null;
        SendFirebaseNotificationRequest notifReq = new SendFirebaseNotificationRequest(request.getReceiver(), "New direct message", "From: "+user.getUsername(), null);
        restTemplate.postForObject(INTERNET_PORT + ":" + NOTIFICATION_PORT + "/notification/sendFirebaseNotification",notifReq, String.class);
        return restTemplate.postForObject(INTERNET_PORT + ":" + CHAT_PORT + "/chat/sendDirectMessage", request,String.class);
    }

    @GetMapping("/getDirectChat/{id1}/{id2}")
    public MainDirectChatResponseDTO getDirectChat(@PathVariable UUID id1, @PathVariable UUID id2) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {CHAT_PORT};
        service.pingCheck(ports,restTemplate);
        DirectChatResponseDTO responseDTO = restTemplate.getForObject(INTERNET_PORT + ":" + CHAT_PORT + "/chat/getDirectChat/" + id1 + "/" + id2, DirectChatResponseDTO.class);
        assert responseDTO != null;
        return new MainDirectChatResponseDTO(responseDTO.getId(),responseDTO.getParticipants());
    }

    @GetMapping("/getDirectMessages/{id}")
    public List<DirectMessageResponseDTO> getDirectMessages(@PathVariable UUID id) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {CHAT_PORT, USER_PORT};
        service.pingCheck(ports,restTemplate);

        DirectChatResponseDTO chat = restTemplate.getForObject(INTERNET_PORT + ":" + CHAT_PORT + "/chat/getDirectChat/" + id, DirectChatResponseDTO.class);
        assert chat != null;
        List<UUID> usersIds=chat.getParticipants();
        List<GetUserByUUIDDTO>users=new ArrayList<>();
        List <DirectMessageResponseDTO> list=new ArrayList<>();


        for(MessageDTO message: chat.getMessages())
        {
            int index=usersIds.indexOf(message.getSender());
            if(index==-1) {
                usersIds.add(message.getSender());

            }
        }

        if(usersIds.size() == 1){
            GetUserByUUIDDTO user =restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + GET_USER + usersIds.get(0), GetUserByUUIDDTO.class);
            users.add(user);
        }
        else {
            users = restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + "/user/getUsers/" + usersIds, List.class);
        }


        for (MessageDTO message: chat.getMessages()) {
            int sender=usersIds.indexOf(message.getSender());
            assert users != null;
            list.add(new DirectMessageResponseDTO(message.getId(),users.get(sender), message.getTimestamp(),message.getPayload()));
        }

        list.sort(new Comparator<>() {
            @Override
            public int compare(DirectMessageResponseDTO o1, DirectMessageResponseDTO o2) {
                return o1.getTimestamp().compareTo(o2.getTimestamp());
            }
        });

        return list;
    }

}
