package com.adventureit.chat;


import com.adventureit.chat.controller.ChatController;
import com.adventureit.chat.entity.GroupChat;
import com.adventureit.chat.entity.GroupMessage;
import com.adventureit.chat.repository.ColorPairRepository;
import com.adventureit.chat.repository.DirectChatRepository;
import com.adventureit.chat.repository.GroupChatRepository;
import com.adventureit.chat.repository.MessageRepository;
import com.adventureit.shareddtos.chat.responses.GroupChatResponseDTO;
import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"firebase-path=C:\\\\Users\\\\kevin\\\\Documents\\\\Enviroment variables\\\\adventure-it-bc0b6-firebase-adminsdk-o2fq8-ad3a51fb5e.json","service-registry-client.instance.hostname=localhost","service-registry-client.client.service-url.defaultZone=http://localhost:8761/eureka/","service-registry-client.client.register-with-eureka=true", "service-registry-client.client.fetch-registry=true","chat-microservice.application-name=CHAT-MICROSERVICE", "chat-microservice.datasource.url=jdbc:postgresql://adventure-it-db.c9gozrkqo8dv.us-east-2.rds.amazonaws.com/adventureit?socketTimeout=5","chat-microservice.datasource.username=postgres","chat-microservice.datasource.password=310PB!Gq%f&J","chat-microservice.datasource.hikari.maximum-pool-size=2","chat-microservice.jpa.hibernate.ddl-auto=update","chat-microservice.jpa.show-sql=false","chat-microservice.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect","chat-microservice.jpa.properties.hibernate.format_sql=true" })
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
        Assertions.assertEquals("Chat Controller is functional",this.restTemplate.getForObject("http://localhost:" + port + "/chat/test", String.class));
    }

    @Test
    @Description("Ensure that the get group chat by adventure id is working")
    public void httpGetGroupChatByAdventureID_returnResponse(){
        UUID id = UUID.randomUUID();
        UUID adventureID = UUID.randomUUID();
        GroupChat chat = new GroupChat(id, adventureID, new ArrayList<>(), new ArrayList<>(), "Mock");
        groupChatRepository.saveAndFlush(chat);
        GroupChatResponseDTO responseDTO = this.restTemplate.getForObject("http://localhost:" + port + "/chat/getGroupChatByAdventureID/" + adventureID, GroupChatResponseDTO.class);
        Assertions.assertTrue(responseDTO != null);
    }

    @Test
    @Description("Ensure that the get group chat by id is working")
    public void httpGetGroupChatByID_returnResponse(){
        UUID id = UUID.randomUUID();
        UUID adventureID = UUID.randomUUID();
        GroupChat chat = new GroupChat(id, adventureID, new ArrayList<>(), new ArrayList<>(), "Mock");
        groupChatRepository.saveAndFlush(chat);
        GroupChatResponseDTO responseDTO = this.restTemplate.getForObject("http://localhost:" + port + "/chat/getGroupChat/" + id, GroupChatResponseDTO.class);
        Assertions.assertTrue(responseDTO != null);
    }

    @Test
    @Description("Ensure that the add participant to group chat function is working")
    public void httpAddParticipant_returnResponse(){
        UUID id = UUID.randomUUID();
        UUID chatID = UUID.randomUUID();
        UUID adventureID = UUID.randomUUID();
        UUID userID1 = UUID.randomUUID();
        UUID userID2 = UUID.randomUUID();
        GroupChat chat = new GroupChat(chatID, adventureID, List.of(userID2), new ArrayList<>(), "Mock");
        groupChatRepository.saveAndFlush(chat);
        Assertions.assertEquals(this.restTemplate.getForObject("http://localhost:" + port + "/chat/addParticipant/" + adventureID + "/" + userID1, String.class),userID1 +" successfully added to chat "+adventureID);
    }

    @Test
    @Description("Ensure that the get group message by id is working")
    public void httpGetGroupMessageByID_returnResponse(){
        UUID id = UUID.randomUUID();
        UUID userID = UUID.randomUUID();
        UUID chatID = UUID.randomUUID();
        GroupMessage groupMessage = new GroupMessage(id,userID,chatID,"Mock");
        messageRepository.saveAndFlush(groupMessage);
        GroupMessage responseDTO = this.restTemplate.getForObject("http://localhost:" + port + "/chat/getGroupMessageByID/" + id, GroupMessage.class);
        Assertions.assertTrue(responseDTO != null);
    }
}
