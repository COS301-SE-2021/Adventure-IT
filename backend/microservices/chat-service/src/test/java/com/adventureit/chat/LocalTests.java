package com.adventureit.chat;

import com.adventureit.chat.Entity.ColorPair;
import com.adventureit.chat.Entity.GroupChat;
import com.adventureit.chat.Service.ChatServiceImplementation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class LocalTests {
    @Autowired
    ChatServiceImplementation chatServiceImplementation;

    @Test
    public void createDirectChat(){
        chatServiceImplementation.createDirectChat(UUID.randomUUID(),UUID.randomUUID());
    }

    @Test
    public void sendDirectMessage() throws Exception {
        chatServiceImplementation.sendDirectMessage(UUID.fromString("666c61a3-81fc-49f5-a100-626a7e3c44a4"),UUID.randomUUID(),UUID.randomUUID(),"Hello");
    }

    @Test
    public void markDirectMessage() throws Exception {
        chatServiceImplementation.markDirectMessageRead(UUID.fromString("b00c952d-bc81-4333-85db-258b23e9b8db"));
    }

    @Test
    public void createGroupChat(){
        UUID userID1 = UUID.randomUUID();
        UUID userID2 = UUID.randomUUID();
        UUID userID3 = UUID.randomUUID();
        UUID adventureID = UUID.randomUUID();
        UUID groupChatID = UUID.randomUUID();
        List<ColorPair> list = new ArrayList<ColorPair>();
        list.add(new ColorPair(UUID.randomUUID(),adventureID,userID1,1));
        list.add(new ColorPair(UUID.randomUUID(),adventureID,userID2,2));
        list.add(new ColorPair(UUID.randomUUID(),adventureID,userID3,3));
        List<UUID> participants = new ArrayList<>(List.of(userID1,userID2,userID3));
        chatServiceImplementation.createGroupChat(UUID.randomUUID(),participants,"Group Chat");
        GroupChat gc = new GroupChat(groupChatID,UUID.randomUUID(),participants,list,"Group Chat");
        System.out.println(gc.getColor(userID1));
    }

    @Test
    public void sendGroupMessage() throws Exception {
        chatServiceImplementation.sendGroupMessage(UUID.fromString("8e1f8bda-ed48-487b-aafe-264ab3b9e207"),UUID.fromString("62e16947-d8cd-405a-ab3e-c93a99ba539c"),"Mock Message");
    }

    @Test
    public void markGroupMessage() throws Exception{
        chatServiceImplementation.markGroupMessageRead(UUID.fromString("3c7ca522-adbd-437a-acc7-11f6506e985b"),UUID.fromString("46366b5d-1435-4440-b990-a4a928f852f6"));
    }

}
