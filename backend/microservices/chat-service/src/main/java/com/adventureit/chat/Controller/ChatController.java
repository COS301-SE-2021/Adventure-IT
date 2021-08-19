package com.adventureit.chat.Controller;

import com.adventureit.chat.Entity.DirectMessage;
import com.adventureit.chat.Entity.GroupMessage;
import com.adventureit.chat.Requests.CreateDirectChatRequest;
import com.adventureit.chat.Requests.CreateGroupChatRequest;
import com.adventureit.chat.Requests.SendDirectMessageRequestDTO;
import com.adventureit.chat.Requests.SendGroupMessageRequestDTO;
import com.adventureit.chat.Responses.DirectChatResponseDTO;
import com.adventureit.chat.Responses.GroupChatResponseDTO;
import com.adventureit.chat.Service.ChatServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
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
    public GroupChatResponseDTO getGroupChatByAdventureID(@PathVariable UUID id) throws Exception {
        return service.getGroupChatByAdventureID(id);
    }

    @GetMapping("/getGroupChat/{id}")
    public GroupChatResponseDTO getGroupChat(@PathVariable UUID id) throws Exception {
        return service.getGroupChat(id);
    }

    @GetMapping("/getDirectChat/{ID1}/{ID2}")
    public DirectChatResponseDTO getDirectChat(@PathVariable UUID ID1,@PathVariable UUID ID2) throws Exception {
        return service.getDirectChat(ID1, ID2);
    }

    @GetMapping("/getDirectChatByID/{id}")
    public DirectChatResponseDTO getDirectChat(@PathVariable UUID id) throws Exception {
        return service.getDirectChatByID(id);
    }

    @GetMapping("/getGroupMessageByID/{id}")
    public GroupMessage getGroupMessage(@PathVariable UUID id){
        return (GroupMessage) service.getMessage(id);
    }

    @GetMapping("/getDirectMessageByID/{id}")
    public DirectMessage getDirectMessage(@PathVariable UUID id){
        return (DirectMessage) service.getMessage(id);
    }


    @PostMapping("/sendGroupMessage")
    public String sendGroupMessage(@RequestBody SendGroupMessageRequestDTO request) throws Exception {
        System.out.println(request.getChatID());
        System.out.println(request.getSender());
        System.out.println(request.getMsg());
        service.sendGroupMessage(request.getChatID(),request.getSender(),request.getMsg());

        return "Message sent";
    }

    @PostMapping("/sendDirectMessage")
    public String sendGroupMessage(@RequestBody SendDirectMessageRequestDTO request) throws Exception {
        service.sendDirectMessage(request.getChatID(),request.getSender(),request.getReceiver(),request.getMsg());
        return "Message sent";
    }

    @GetMapping("/deleteChat/{id}")
    public void deleteChat(@PathVariable UUID id) throws Exception {
        service.deleteDirectChat(id);
    }
}
