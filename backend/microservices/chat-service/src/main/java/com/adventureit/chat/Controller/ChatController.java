package com.adventureit.chat.Controller;

import com.adventureit.chat.Requests.CreateDirectChatRequest;
import com.adventureit.chat.Requests.CreateGroupChatRequest;
import com.adventureit.chat.Service.ChatServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatServiceImplementation service;

    @GetMapping("/test")
    String test(){
        return "Budget Controller is functioning";
    }

    @PostMapping("/createDirectChat")
    String createDirectChat(@RequestBody CreateDirectChatRequest req){
        service.createDirectChat(req.getAdventureId(),req.getUser1Id(),req.getUser2Id());
        return "Chat created";
    }

    @PostMapping("/createGroupChat")
    String createDirectChat(@RequestBody CreateGroupChatRequest req){
        service.createGroupChat(req.getAdventureId(),req.getParticipants(),req.getName());
        return "Group Chat created";
    }



}
