package com.adventureit.chat.service;

import com.adventureit.chat.entity.Message;
import com.adventureit.shareddtos.chat.responses.DirectChatResponseDTO;
import com.adventureit.shareddtos.chat.responses.GroupChatResponseDTO;
import javassist.NotFoundException;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ChatService {
     String addParticipant( UUID adventureID, UUID participant);
     String createDirectChat(UUID user1, UUID user2);
     String createGroupChat( UUID adventureID, List<UUID> participants, String name);
     String sendDirectMessage(UUID chatID,UUID sender, UUID receiver,String message) throws IOException;
     String sendGroupMessage(UUID chatID,UUID sender,String message) throws IOException;
     void markDirectMessageRead(UUID id) throws NotFoundException, IOException, ClassNotFoundException;
     void markGroupMessageRead(UUID id, UUID userID) throws IOException, ClassNotFoundException;
     GroupChatResponseDTO getGroupChat(UUID id);
     GroupChatResponseDTO getGroupChatByAdventureID(UUID id);
     Message getMessage(UUID id) throws NotFoundException, IOException, ClassNotFoundException;
     DirectChatResponseDTO getDirectChat(UUID id1, UUID id2);
     DirectChatResponseDTO getDirectChatByID(UUID id);
     void deleteDirectChat(UUID id);
     void deleteGroupChat(UUID id) throws NotFoundException;
     void deleteMessage(UUID id) throws NotFoundException;
}
