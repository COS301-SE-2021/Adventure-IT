package com.adventureit.chat;


import com.adventureit.chat.Controller.ChatController;
import com.adventureit.chat.Entity.GroupChat;
import com.adventureit.chat.Repository.ColorPairRepository;
import com.adventureit.chat.Repository.DirectChatRepository;
import com.adventureit.chat.Repository.GroupChatRepository;
import com.adventureit.chat.Repository.MessageRepository;
import com.adventureit.chat.Responses.GroupChatResponseDTO;
import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChatServiceIntegrationTests {
    @Autowired
    private ChatController chatController;

    @Autowired
    GroupChatRepository groupChatRepository;
    @Autowired
    DirectChatRepository directChatRepository;
    @Autowired
    ColorPairRepository colorPairRepository;
    @Autowired
    MessageRepository messageRepository;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Description("Ensure that the Budget Controller loads")
    public void chatControllerLoads() throws Exception {
        Assertions.assertNotNull(chatController);
    }

    @Test
    @Description("Ensure that the controller is accepting traffic and responding")
    public void httpTest_returnResponse(){
        Assertions.assertEquals(this.restTemplate.getForObject("http://localhost:" + port + "/chat/test", String.class),"Chat Controller is functioning");
    }

    @Test
    @Description("Ensure that the view function works")
    public void getGroupChatByAdventureIdControllerTest(){
        UUID id = UUID.randomUUID();
        UUID adventureID = UUID.randomUUID();
        GroupChat groupChat = new GroupChat(id,adventureID,new ArrayList<>(),new ArrayList<>(),"General");
        groupChatRepository.saveAndFlush(groupChat);
        this.restTemplate.getForObject("http://localhost:" + port + "/chat/getGroupChatByAdventureID/"+id, List.class);
    }
}
