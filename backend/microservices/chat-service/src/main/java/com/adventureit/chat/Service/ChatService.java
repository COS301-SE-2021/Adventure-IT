package com.adventureit.chat.Service;

import com.adventureit.chat.Entity.Message;
import com.adventureit.chat.Responses.DirectChatResponseDTO;
import com.adventureit.chat.Responses.GroupChatResponseDTO;

import java.util.List;
import java.util.UUID;

public interface ChatService {
    public String addParticipant( UUID adventureID, UUID participant);
    public String createDirectChat(UUID user1, UUID user2);
    public String createGroupChat( UUID adventureID, List<UUID> participants, String name);
    public String sendDirectMessage(UUID chatID,UUID sender, UUID receiver,String message) throws Exception;
    public String sendGroupMessage(UUID chatID,UUID sender,String message) throws Exception;
    public void markDirectMessageRead(UUID id) throws Exception;
    public void markGroupMessageRead(UUID id, UUID userID) throws Exception;
    public GroupChatResponseDTO getGroupChat(UUID id) throws Exception;
    public GroupChatResponseDTO getGroupChatByAdventureID(UUID id) throws Exception;
    public Message getMessage(UUID id);
    public DirectChatResponseDTO getDirectChat(UUID ID1, UUID ID2) throws Exception;
    public DirectChatResponseDTO getDirectChatByID(UUID id) throws Exception;
    public void deleteChat(UUID id) throws Exception;
}
