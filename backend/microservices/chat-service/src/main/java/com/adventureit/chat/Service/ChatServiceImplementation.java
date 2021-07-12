package com.adventureit.chat.Service;

import com.adventureit.chat.Entity.*;
import com.adventureit.chat.Repository.ChatRepository;
import com.adventureit.chat.Repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ChatServiceImplementation implements ChatService {
    @Autowired
    ChatRepository chatRepository;
    @Autowired
    MessageRepository messageRepository;

    public ChatServiceImplementation(ChatRepository chatRepository,MessageRepository messageRepository){
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
    }

    @Override
    public String createDirectChat(UUID id, UUID adventureID, UUID user1, UUID user2) {
        DirectChat directChat = new DirectChat(id,adventureID,user1,user2);
        chatRepository.save(directChat);
        return "Chat successfully created";
    }

    @Override
    public String createGroupChat(UUID id, UUID adventureID, List<UUID> participants, String name) {
        GroupChat groupChat = new GroupChat(id,adventureID,participants,name);
        chatRepository.save(groupChat);
        return "Group Chat successfully created";
    }

    @Override
    public String sendDirectMessage(UUID id, UUID chatID,UUID sender, UUID receiver, String msg) throws Exception {
        Chat chat = chatRepository.findChatById(chatID);
        if(chat == null){
            throw new Exception("Chat does not exist");
        }

        DirectMessage message = new DirectMessage(id,sender,receiver,msg);
        messageRepository.save(message);
        chat.getMessages().add(id);
        chatRepository.save(chat);
        return "Message Sent";
    }

    @Override
    public String sendGroupMessage(UUID id, UUID chatID, UUID sender, List<UUID> receivers,String msg) throws Exception {
        Chat chat = chatRepository.findChatById(chatID);
        if(chat == null){
            throw new Exception("Chat does not exist");
        }

        GroupMessage message = new GroupMessage(id,sender,receivers,msg);
        messageRepository.save(message);
        chat.getMessages().add(id);
        chatRepository.save(chat);

        return "Message Sent";
    }

    @Override
    public void markDirectMessageRead(UUID id) throws Exception {
        DirectMessage message = (DirectMessage) messageRepository.findMessageById(id);
        if(message == null){
            throw new Exception("Message does not exist");
        }

        message.setRead(!message.getRead());
        messageRepository.save(message);
    }

    @Override
    public void markGroupMessageRead(UUID id, UUID userID) throws Exception {
        GroupMessage message = (GroupMessage) messageRepository.findMessageById(id);
        if(message == null){
            throw new Exception("Message does not exist");
        }

        message.getRead().replace(userID,true);
        messageRepository.save(message);
    }
}
