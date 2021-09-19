package com.adventureit.maincontroller.controller;

import com.adventureit.shareddtos.adventure.AdventureDTO;
import com.adventureit.shareddtos.chat.DirectMessageDTO;
import com.adventureit.shareddtos.chat.GroupMessageDTO;
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

    private final String IP = "http://localhost";
    private final String userPort = "9002";
    private final String chatPort = "9010";
    private final String adventurePort="9001";
    private final String notificationPort="9004";

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
            GetUserByUUIDDTO user =restTemplate.getForObject(IP + ":" + userPort + "/user/getUser/" + usersIds.get(0), GetUserByUUIDDTO.class);
            users.add(user);
        }
        else {
            users = restTemplate.getForObject(IP + ":" + userPort + "/user/getUsers/" + usersIds, List.class);
        }

        for (MessageDTO message: chat.getMessages()) {
           int index=usersIds.indexOf(message.getSender());
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
    public MainGroupChatResponseDTO getGroupChat(@PathVariable UUID id){
        GroupChatResponseDTO responseDTO = restTemplate.getForObject(IP + ":" + chatPort + "/chat/getGroupChatByAdventureID/" + id, GroupChatResponseDTO.class);
        assert responseDTO != null;

        return new MainGroupChatResponseDTO(responseDTO.getId(),responseDTO.getAdventureID(),responseDTO.getParticipants(),responseDTO.getName(),responseDTO.getColors());
    }

    @PostMapping("/sendGroupMessage")
    public String sendGroupMessage(@RequestBody SendGroupMessageRequestDTO request){
        SendGroupMessageRequestDTO req = new SendGroupMessageRequestDTO(request.getChatID(),request.getSender(),request.getMsg());

        // Get associated chat
        GroupChatResponseDTO chat = restTemplate.getForObject(IP + ":" + chatPort + "/chat/getGroupChat/" + req.getChatID(), GroupChatResponseDTO.class);

        // Get associated adventure
        AdventureDTO adventure = restTemplate.getForObject(IP + ":" + adventurePort + "/adventure/getAdventureByUUID/" + chat.getAdventureID(), AdventureDTO.class);

        // Get all participants of chat
        List<UUID> users = chat.getParticipants();
        users.remove(request.getSender());

        // Send notification to all participants
        SendFirebaseNotificationsRequest notifReq = new SendFirebaseNotificationsRequest(users, "New Group Message", "Adventure: " + adventure.getName(), null);
        restTemplate.postForObject(IP + ":" + notificationPort + "/notification/sendFirebaseNotifications",notifReq, String.class);

        return restTemplate.postForObject(IP + ":" + chatPort + "/chat/sendGroupMessage",req,String.class);
    }

    @PostMapping("/sendDirectMessage")
    public String sendDirectMessage(@RequestBody SendDirectMessageRequestDTO request){
        GetUserByUUIDDTO user =restTemplate.getForObject(IP + ":" + userPort + "/user/getUser/" + request.getSender(), GetUserByUUIDDTO.class);
        SendFirebaseNotificationRequest notifReq = new SendFirebaseNotificationRequest(request.getReceiver(), "New direct message", "From: "+user.getUsername(), null);
        restTemplate.postForObject(IP + ":" + notificationPort + "/notification/sendFirebaseNotification",notifReq, String.class);
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
        DirectChatResponseDTO chat = restTemplate.getForObject(IP + ":" + chatPort + "/chat/getDirectChat/" + id, DirectChatResponseDTO.class);
        List<UUID> usersIds=chat.getParticipants();
        List<GetUserByUUIDDTO>users=new ArrayList<>();
        List <DirectMessageResponseDTO> list=new ArrayList<>();
        assert chat != null;


        for(MessageDTO message: chat.getMessages())
        {
            int index=usersIds.indexOf(message.getSender());
            if(index==-1) {
                usersIds.add(message.getSender());

            }
        }

        if(usersIds.size() == 1){
            GetUserByUUIDDTO user =restTemplate.getForObject(IP + ":" + userPort + "/user/getUser/" + usersIds.get(0), GetUserByUUIDDTO.class);
            users.add(user);
        }
        else {
            users = restTemplate.getForObject(IP + ":" + userPort + "/user/getUsers/" + usersIds, List.class);
        }


        for (MessageDTO message: chat.getMessages()) {
            int sender=usersIds.indexOf(message.getSender());
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
