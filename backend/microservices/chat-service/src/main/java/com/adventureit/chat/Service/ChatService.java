package com.adventureit.chat.Service;

import java.util.List;
import java.util.UUID;

public interface ChatService {
    public String createDirectChat(UUID id, UUID adventureID, UUID user1, UUID user2);
    public String createGroupChat(UUID id, UUID adventureID, List<UUID> participants, String name);
    public String sendDirectMessage(UUID id, UUID chatID,UUID sender, UUID receiver,String message) throws Exception;
    public String sendGroupMessage(UUID id, UUID chatID,UUID sender, List<UUID> receivers,String message) throws Exception;
    public void markMessageRead(UUID id) throws Exception;

}
