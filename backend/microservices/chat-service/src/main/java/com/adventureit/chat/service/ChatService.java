package com.adventureit.chat.service;

import com.adventureit.chat.entity.Message;
import com.adventureit.chat.responses.DirectChatResponseDTO;
import com.adventureit.chat.responses.GroupChatResponseDTO;

import java.util.List;
import java.util.UUID;

public interface ChatService {
     String addParticipant( UUID adventureID, UUID participant);
     String createDirectChat(UUID user1, UUID user2);
     String createGroupChat( UUID adventureID, List<UUID> participants, String name);
     String sendDirectMessage(UUID chatID,UUID sender, UUID receiver,String message);
     String sendGroupMessage(UUID chatID,UUID sender,String message);
     void markDirectMessageRead(UUID id);
     void markGroupMessageRead(UUID id, UUID userID);
     GroupChatResponseDTO getGroupChat(UUID id);
     GroupChatResponseDTO getGroupChatByAdventureID(UUID id);
     Message getMessage(UUID id);
     DirectChatResponseDTO getDirectChat(UUID id1, UUID id2);
     DirectChatResponseDTO getDirectChatByID(UUID id);
     void deleteDirectChat(UUID id);
     void deleteGroupChat(UUID id);
}
