package com.adventureit.chat.Controller;

import com.adventureit.chat.Entity.Message;
import com.adventureit.chat.Requests.CreateDirectChatRequest;
import com.adventureit.chat.Requests.CreateGroupChatRequest;
import com.adventureit.chat.Responses.GroupChatResponseDTO;
import com.adventureit.chat.Service.ChatServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatServiceImplementation service;

    @GetMapping("/test")
    public String test(){
        return "Chat Controller is functioning";
    }

    @PostMapping("/createDirectChat")
    public String createDirectChat(@RequestBody CreateDirectChatRequest req){
        service.createDirectChat(req.getAdventureId(),req.getUser1Id(),req.getUser2Id());
        return "Chat created";
    }

    @PostMapping("/createGroupChat")
    public String createDirectChat(@RequestBody CreateGroupChatRequest req){
        service.createGroupChat(req.getAdventureId(),req.getParticipants(),req.getName());
        return "Group Chat created";
    }

    @GetMapping("/getGroupChat/{id}")
    public GroupChatResponseDTO getGroupChat(@PathVariable UUID id) throws Exception {
        return service.getGroupChat(id);
    }

    @GetMapping("/getMessageByID/{id}")
    public Message getMessage(@PathVariable UUID id){
        return service.getMessage(id);
    }

}
