package com.adventureit.chat;

import com.adventureit.chat.Service.ChatServiceImplementation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class LocalTests {
    @Autowired
    ChatServiceImplementation chatServiceImplementation;

    @Test
    public void createDirectChat(){
        chatServiceImplementation.createDirectChat(UUID.randomUUID(),UUID.randomUUID(),UUID.randomUUID(),UUID.randomUUID());
    }

    @Test
    public void sendDirectMessage() throws Exception {
        chatServiceImplementation.sendDirectMessage(UUID.randomUUID(),UUID.fromString("41bc4320-95fc-41de-b40f-fd9c3fb68c48"),UUID.randomUUID(),UUID.randomUUID(),"Hello");
    }
}
