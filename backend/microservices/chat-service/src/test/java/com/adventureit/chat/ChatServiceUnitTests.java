package com.adventureit.chat;


import com.adventureit.chat.entity.ColorPair;
import com.adventureit.chat.entity.DirectChat;
import com.adventureit.chat.entity.GroupChat;
import com.adventureit.chat.repository.ColorPairRepository;
import com.adventureit.chat.repository.DirectChatRepository;
import com.adventureit.chat.repository.GroupChatRepository;
import com.adventureit.chat.repository.MessageRepository;
import com.adventureit.shareddtos.chat.requests.CreateDirectChatRequest;
import com.adventureit.shareddtos.chat.requests.CreateGroupChatRequest;
import com.adventureit.shareddtos.chat.requests.SendDirectMessageRequestDTO;
import com.adventureit.shareddtos.chat.requests.SendGroupMessageRequestDTO;
import com.adventureit.shareddtos.chat.responses.GroupChatResponseDTO;
import com.adventureit.chat.service.ChatServiceImplementation;
import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@SpringBootTest
public class ChatServiceUnitTests{

    DirectChatRepository directChatRepository = Mockito.mock(DirectChatRepository.class);
    GroupChatRepository groupChatRepository = Mockito.mock(GroupChatRepository.class);
    MessageRepository messageRepository = Mockito.mock(MessageRepository.class);
    ColorPairRepository colorPairRepository = Mockito.mock(ColorPairRepository.class);

    ChatServiceImplementation service = new ChatServiceImplementation(colorPairRepository,directChatRepository,groupChatRepository,messageRepository);

    UUID mockChatID =UUID.randomUUID();
    UUID mockUser1Id = UUID.randomUUID();
    UUID mockUser2Id = UUID.randomUUID();


    DirectChat mockDirectChat = new DirectChat(mockChatID, mockUser1Id, mockUser2Id);





    @Test
    @Description("Testing to make sure that the CreateTimelineRequest returns the correct parameters that were passed in")
    public void createDirectChatRequest(){

        //Given
        UUID user1 = UUID.randomUUID();
        UUID user2 = UUID.randomUUID();


        //When
        CreateDirectChatRequest mockRequest = new CreateDirectChatRequest(user1, user2);

        //Then
        Assertions.assertEquals(user1, mockRequest.getUser1Id());
        Assertions.assertEquals(user2, mockRequest.getUser2Id());
    }

    @Test
    @Description("Testing to make sure that the CreateTimelineRequest returns the correct parameters that were passed in")
    public void createGroupChatRequest(){

        //Given
        UUID mockAdventureId = UUID.randomUUID();
        List<UUID> participants = new ArrayList<>();
        String name = "Mock Chat";
        participants.add(UUID.randomUUID());

        //When
        CreateGroupChatRequest mockRequest = new CreateGroupChatRequest(mockAdventureId,participants,name);

        //Then
        Assertions.assertEquals(mockAdventureId, mockRequest.getAdventureId());
        Assertions.assertEquals(participants, mockRequest.getParticipants());
        Assertions.assertEquals(name, mockRequest.getName());
    }

    @Test
    @Description("Testing to make sure that the CreateTimelineRequest returns the correct parameters that were passed in")
    public void sendDirectMessageRequestTest(){
        //Given
        UUID mockChatID = UUID.randomUUID();
        UUID mockSender = UUID.randomUUID();
        UUID mockReceiver = UUID.randomUUID();
        String mockMessage = "Mock message";

        //When
        SendDirectMessageRequestDTO mockRequest = new SendDirectMessageRequestDTO(mockChatID,mockSender,mockReceiver,mockMessage);

        //Then
        Assertions.assertEquals(mockChatID, mockRequest.getChatID());
        Assertions.assertEquals(mockSender, mockRequest.getSender());
        Assertions.assertEquals(mockReceiver, mockRequest.getReceiver());
        Assertions.assertEquals(mockMessage, mockRequest.getMsg());


    }

    @Test
    @Description("Testing to make sure that the CreateTimelineRequest returns the correct parameters that were passed in")
    public void sendGroupMessageRequestTest(){

        //Given
        UUID mockChatID = UUID.randomUUID();
        UUID mockSender = UUID.randomUUID();
        String mockMessage = "Mock Message";

        //When
        SendGroupMessageRequestDTO mockRequest = new SendGroupMessageRequestDTO(mockChatID,mockSender,mockMessage);

        //Then
        Assertions.assertEquals(mockChatID, mockRequest.getChatID());
        Assertions.assertEquals(mockSender, mockRequest.getSender());
        Assertions.assertEquals(mockMessage, mockRequest.getMsg());

    }

    @Test
    @Description("Testing to make sure that the CreateTimelineRequest returns the correct parameters that were passed in")
    public void createDirectChatTest(){

        //Given
        UUID user1 = UUID.randomUUID();
        UUID user2 = UUID.randomUUID();

        //When
        Mockito.when(directChatRepository.save(Mockito.any())).thenReturn(mockDirectChat);
        String response = service.createDirectChat(user1,user2);

        //Then
        Assertions.assertEquals("Chat successfully created", response);

    }

    @Test
    @Description("Testing to make sure that the CreateTimelineRequest returns the correct parameters that were passed in")
    public void createGroupChatTest() {
        //Given
        UUID mockAdventureId = UUID.randomUUID();
        List<UUID> participants = new ArrayList<>();
        String name = "Mock Chat";
        participants.add(UUID.randomUUID());

        //When
        String response = service.createGroupChat(mockAdventureId,participants,name);
        Assertions.assertEquals("Group Chat successfully created", response);
    }

    @Test
    @Description("Testing to make sure that the CreateTimelineRequest returns the correct parameters that were passed in")
    public void sendDirectMessage() throws Exception {
        //Given
        UUID mockChatID = UUID.randomUUID();
        UUID mockSender = UUID.randomUUID();
        UUID mockReceiver = UUID.randomUUID();
        String mockMessage = "Mock message";
        Mockito.when(directChatRepository.findByDirectChatId(mockChatID)).thenReturn(mockDirectChat);

        //When

        String response = service.sendDirectMessage(mockChatID,mockSender,mockReceiver,mockMessage);
        Assertions.assertEquals("Message Sent", response);
    }

    @Test
    @Description("Testing to make sure that the CreateTimelineRequest returns the correct parameters that were passed in")
    public void sendDirectMessageFailTest() throws Exception {
        //Given
        UUID incorrectMockChatID = UUID.randomUUID();
        UUID mockSender = UUID.randomUUID();
        UUID mockReceiver = UUID.randomUUID();
        String mockMessage = "Mock message";
        Mockito.when(directChatRepository.findByDirectChatId(incorrectMockChatID)).thenReturn(null);

        //When
        Assertions.assertThrows(Exception.class, ()->
                service.sendDirectMessage(mockChatID,mockSender,mockReceiver,mockMessage));

    }

    @Test
    @Description("Testing to make sure that the CreateTimelineRequest returns the correct parameters that were passed in")
    public void sendGroupMessageTest() throws Exception {
        //Given
        UUID mockGroupChatID = UUID.randomUUID();
        UUID mockAdventureId = UUID.randomUUID();
        List<UUID> participants = new ArrayList<>();
        participants.add(UUID.randomUUID());
        List<ColorPair> colors = new ArrayList<>();
        colors.add(new ColorPair(UUID.randomUUID(),UUID.randomUUID(),UUID.randomUUID(),65));
        String name = "General Chat";
        String message = "Test message";
        UUID mockUserId =UUID.randomUUID();
        GroupChat mockGroupChat = new GroupChat(mockGroupChatID,mockAdventureId,participants,colors,name);
        Mockito.when(groupChatRepository.getGroupChatByGroupChatId(mockGroupChatID)).thenReturn(mockGroupChat);

        //When
        String response = service.sendGroupMessage(mockGroupChatID,mockUserId,message);

        //Then
        Assertions.assertEquals("Message Sent", response);

    }

    @Test
    @Description("Testing to make sure that the CreateTimelineRequest returns the correct parameters that were passed in")
    public void sendGroupMessageTestFail() throws Exception {
        //Given
        UUID incorrectMockGroupChatID = UUID.randomUUID();
        String message = "Test message";
        UUID mockUserId =UUID.randomUUID();
        Mockito.when(groupChatRepository.findGroupChatByGroupChatId(incorrectMockGroupChatID)).thenReturn(null);

        //When
        Assertions.assertThrows(Exception.class, ()->
                service.sendGroupMessage(incorrectMockGroupChatID,mockUserId,message));

    }

    @Test
    @Description("Testing to make sure that the CreateTimelineRequest returns the correct parameters that were passed in")
    public void getGroupChatByAdventureIDTest() throws Exception {
        UUID mockGroupChatID = UUID.randomUUID();
        UUID mockAdventureId = UUID.randomUUID();
        List<UUID> participants = new ArrayList<>();
        participants.add(UUID.randomUUID());
        List<ColorPair> colors = new ArrayList<>();
        colors.add(new ColorPair(UUID.randomUUID(),UUID.randomUUID(),UUID.randomUUID(),65));
        String name = "General Chat";
        String message = "Test message";
        UUID mockUserId =UUID.randomUUID();
        GroupChat mockGroupChat = new GroupChat(mockGroupChatID,mockAdventureId,participants,colors,name);
        Mockito.when(groupChatRepository.findAllByAdventureID(mockAdventureId)).thenReturn(mockGroupChat);

        //When
        GroupChatResponseDTO response = service.getGroupChatByAdventureID(mockAdventureId);

        //Then
        Assertions.assertEquals(mockGroupChatID, response.getId());
        Assertions.assertEquals(mockAdventureId, response.getAdventureID());
        Assertions.assertEquals(participants, response.getParticipants());
        Assertions.assertEquals(colors, response.getColors());
        Assertions.assertEquals(name, response.getName());


    }

    @Test
    @Description("Testing to make sure that the CreateTimelineRequest returns the correct parameters that were passed in")
    public void getGroupChatByAdventureIDTestFail() throws Exception {
        //Given
        UUID incorrectAdventureID = UUID.randomUUID();

        //When
        Mockito.when(groupChatRepository.findAllByAdventureID(incorrectAdventureID)).thenReturn(null);

        //Then
        Assertions.assertThrows(Exception.class, ()->
                service.getGroupChatByAdventureID(incorrectAdventureID));
    }

    @Test
    @Description("Testing to make sure that the CreateTimelineRequest returns the correct parameters that were passed in")
    public void getGroupChatTest() throws Exception {
        UUID mockGroupChatID = UUID.randomUUID();
        UUID mockAdventureId = UUID.randomUUID();
        List<UUID> participants = new ArrayList<>();
        participants.add(UUID.randomUUID());
        List<ColorPair> colors = new ArrayList<>();
        colors.add(new ColorPair(UUID.randomUUID(),UUID.randomUUID(),UUID.randomUUID(),65));
        String name = "General Chat";
        String message = "Test message";
        UUID mockUserId =UUID.randomUUID();
        GroupChat mockGroupChat = new GroupChat(mockGroupChatID,mockAdventureId,participants,colors,name);
        Mockito.when(groupChatRepository.findGroupChatByGroupChatId(mockGroupChatID)).thenReturn(mockGroupChat);

        //When
        GroupChatResponseDTO response = service.getGroupChat(mockGroupChatID);

        //Then
        Assertions.assertEquals(mockGroupChatID, response.getId());
        Assertions.assertEquals(mockAdventureId, response.getAdventureID());
        Assertions.assertEquals(participants, response.getParticipants());
        Assertions.assertEquals(colors, response.getColors());
        Assertions.assertEquals(name, response.getName());


    }

    @Test
    @Description("Testing to make sure that the CreateTimelineRequest returns the correct parameters that were passed in")
    public void getGroupChatTestFail() throws Exception {
        //Given
        UUID incorrectGroupChatID = UUID.randomUUID();

        //When
        Mockito.when(groupChatRepository.findAllByAdventureID(incorrectGroupChatID)).thenReturn(null);

        //Then
        Assertions.assertThrows(Exception.class, ()->
                service.getGroupChatByAdventureID(incorrectGroupChatID));
    }
}
