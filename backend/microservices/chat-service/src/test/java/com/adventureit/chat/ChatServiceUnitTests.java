package com.adventureit.chat;


import com.adventureit.chat.Repository.ColorPairRepository;
import com.adventureit.chat.Repository.DirectChatRepository;
import com.adventureit.chat.Repository.GroupChatRepository;
import com.adventureit.chat.Repository.MessageRepository;
import com.adventureit.chat.Requests.CreateDirectChatRequest;
import com.adventureit.chat.Requests.CreateGroupChatRequest;
import com.adventureit.chat.Requests.SendDirectMessageRequestDTO;
import com.adventureit.chat.Requests.SendGroupMessageRequestDTO;
import com.adventureit.chat.Service.ChatServiceImplementation;
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
    public void sendDirectMessageRequest(){
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
    public void sendGroupMessageRequest(){

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

}
