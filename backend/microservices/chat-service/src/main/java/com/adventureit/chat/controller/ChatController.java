package com.adventureit.chat.controller;

import com.adventureit.chat.entity.DirectMessage;
import com.adventureit.chat.entity.GroupMessage;
import com.adventureit.shareddtos.chat.requests.CreateDirectChatRequest;
import com.adventureit.shareddtos.chat.requests.CreateGroupChatRequest;
import com.adventureit.shareddtos.chat.requests.SendDirectMessageRequestDTO;
import com.adventureit.shareddtos.chat.requests.SendGroupMessageRequestDTO;
import com.adventureit.shareddtos.chat.responses.DirectChatResponseDTO;
import com.adventureit.shareddtos.chat.responses.GroupChatResponseDTO;
import com.adventureit.chat.service.ChatServiceImplementation;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
        service.createDirectChat(req.getUser1Id(),req.getUser2Id());
        return "Chat created";
    }

    @PostMapping("/createGroupChat")
    public String createGroupChat(@RequestBody CreateGroupChatRequest req){
        service.createGroupChat(req.getAdventureId(),req.getParticipants(),req.getName());
        return "Group Chat created";
    }

    @GetMapping("/getGroupChatByAdventureID/{id}")
    public GroupChatResponseDTO getGroupChatByAdventureID(@PathVariable UUID id) {
        return service.getGroupChatByAdventureID(id);
    }

    @GetMapping("/getGroupChat/{id}")
    public GroupChatResponseDTO getGroupChat(@PathVariable UUID id) {
        return service.getGroupChat(id);
    }

    @GetMapping("/getDirectChat/{id1}/{id2}")
    public DirectChatResponseDTO getDirectChat(@PathVariable UUID id1,@PathVariable UUID id2) {
        return service.getDirectChat(id1, id2);
    }

    @GetMapping("/getDirectChatByID/{id}")
    public DirectChatResponseDTO getDirectChat(@PathVariable UUID id) {
        return service.getDirectChatByID(id);
    }

    @GetMapping("/getGroupMessageByID/{id}")
    public GroupMessage getGroupMessage(@PathVariable UUID id) throws  IOException, ClassNotFoundException {
        return (GroupMessage) service.getMessage(id);
    }

    @GetMapping("/getDirectMessageByID/{id}")
    public DirectMessage getDirectMessage(@PathVariable UUID id) throws IOException, ClassNotFoundException {
        return (DirectMessage) service.getMessage(id);
    }


    @PostMapping("/sendGroupMessage")
    public String sendGroupMessage(@RequestBody SendGroupMessageRequestDTO request) throws IOException {
        service.sendGroupMessage(request.getChatID(),request.getSender(),request.getMsg());

        return "Message sent";
    }

    @PostMapping("/sendDirectMessage")
    public String sendGroupMessage(@RequestBody SendDirectMessageRequestDTO request) throws IOException {
        service.sendDirectMessage(request.getChatID(),request.getSender(),request.getReceiver(),request.getMsg());
        return "Message sent";
    }

    @GetMapping("/addParticipant/{advID}/{participantID}")
    public String addParticipant(@PathVariable UUID advID, @PathVariable UUID participantID){
        return service.addParticipant(advID, participantID);
    }

    @GetMapping("/deleteChat/{id}")
    public void deleteChat(@PathVariable UUID id) {
        service.deleteDirectChat(id);
    }
}
