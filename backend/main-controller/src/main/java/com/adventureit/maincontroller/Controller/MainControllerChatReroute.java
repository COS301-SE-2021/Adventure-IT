package com.adventureit.maincontroller.Controller;

import com.adventureit.chat.Entity.GroupMessage;
import com.adventureit.chat.Requests.CreateDirectChatRequest;
import com.adventureit.chat.Requests.CreateGroupChatRequest;
import com.adventureit.chat.Responses.GroupChatResponseDTO;
import com.adventureit.maincontroller.Responses.MessageResponseDTO;
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

    @GetMapping("/getMessages/{id}")
    public List<MessageResponseDTO> getMessages(@PathVariable UUID id) throws Exception {
        GroupChatResponseDTO chat = restTemplate.getForObject("http://"+ IP + ":" + chatPort + "/chat/getGroupChat/" + id, GroupChatResponseDTO.class);
        GroupMessage message = null;
        GetUserByUUIDDTO user = null;
        List<GetUserByUUIDDTO> users = new ArrayList<>();
        List <MessageResponseDTO> list = new ArrayList<>();

        for (UUID ID:chat.getMessages()) {
            message = restTemplate.getForObject("http://"+ IP + ":" + chatPort + "/chat/getMessageByID/" + ID, GroupMessage.class);
            user = restTemplate.getForObject("http://"+ IP + ":" + userPort + "/user/GetUser/" + message.getSender(), GetUserByUUIDDTO.class);

            for (UUID x:message.getReceivers()) {
                users.add(restTemplate.getForObject("http://"+ IP + ":" + userPort + "/user/GetUser/" + x, GetUserByUUIDDTO.class));
            }

            list.add(new MessageResponseDTO(message.getId(),user,message.getMessage(), message.getTimestamp(),users,message.getRead()));
        }

        list.sort(new Comparator<MessageResponseDTO>() {
            @Override
            public int compare(MessageResponseDTO o1, MessageResponseDTO o2) {
                return o1.getTimestamp().compareTo(o2.getTimestamp());
            }
        });

        return list;
    }

    @GetMapping("/getGroupChatByAdventureID/{id}")
    public GroupChatResponseDTO getGroupChat(@PathVariable UUID id) throws Exception {
        return restTemplate.getForObject("http://"+ IP + ":" + chatPort + "/chat/getGroupChatByAdventureID/" + id, GroupChatResponseDTO.class);
    }


}
