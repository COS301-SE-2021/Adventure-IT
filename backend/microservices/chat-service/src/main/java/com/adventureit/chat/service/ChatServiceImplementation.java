package com.adventureit.chat.service;

import com.adventureit.chat.entity.*;
import com.adventureit.chat.exceptions.ChatNotFoundException;
import com.adventureit.chat.exceptions.GroupChatFullException;
import com.adventureit.chat.exceptions.MessageNotFoundException;
import com.adventureit.chat.repository.ColorPairRepository;
import com.adventureit.chat.repository.DirectChatRepository;
import com.adventureit.chat.repository.GroupChatRepository;
import com.adventureit.chat.repository.MessageRepository;
import com.adventureit.chat.responses.DirectChatResponseDTO;
import com.adventureit.chat.responses.GroupChatResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class ChatServiceImplementation implements ChatService {
    GroupChatRepository groupChatRepository;
    DirectChatRepository directChatRepository;
    MessageRepository messageRepository;
    ColorPairRepository colorPairRepository;
    Random rand;

    public ChatServiceImplementation(ColorPairRepository colorPairRepository,DirectChatRepository directChatRepository,GroupChatRepository groupChatRepository,MessageRepository messageRepository){
        this.groupChatRepository = groupChatRepository;
        this.directChatRepository = directChatRepository;
        this.messageRepository = messageRepository;
        this.colorPairRepository = colorPairRepository;
        this.rand = new Random();

    }

    @Override
    public String createDirectChat(UUID user1, UUID user2) {
        UUID id = UUID.randomUUID();
        DirectChat directChat = new DirectChat(id,user1,user2);
        directChatRepository.save(directChat);
        return "Chat successfully created";
    }

    @Override
    public String createGroupChat( UUID adventureID, List<UUID> participants, String name) {
        UUID id = UUID.randomUUID();
        List<ColorPair> list = new ArrayList<>();
        List<Integer> checked = new ArrayList<>();
        for (UUID participant : participants) {
            int randomCol = this.rand.nextInt(359) + 1;
            while (checked.contains(randomCol)) {
                randomCol = this.rand.nextInt(359) + 1;
            }
            checked.add(randomCol);
            UUID colorPairId = UUID.randomUUID();
            ColorPair newPair = new ColorPair(colorPairId, participant,adventureID , randomCol);
            list.add(newPair);
            colorPairRepository.save(newPair);
        }
        GroupChat groupChat = new GroupChat(id,adventureID,participants,list,name);
        groupChatRepository.save(groupChat);
        return "Group Chat successfully created";
    }

    @Override
    public String addParticipant( UUID adventureID, UUID participant) {
        GroupChat groupChat = groupChatRepository.findAllByAdventureID(adventureID);
        groupChat.getParticipants().add(participant);
        List<Integer> checked = new ArrayList<>();
        List<ColorPair> cp = colorPairRepository.findAllByAdventureId(adventureID);

        if(cp.size() >=360 ){
            throw new GroupChatFullException("This group chat has reached maximum capacity");
        }
        for (ColorPair colorPair : cp) {
            checked.add(colorPair.getColor());
        }
        int randval = this.rand.nextInt(359)+1;
        while(checked.contains(randval)){
             randval = this.rand.nextInt(359)+1;
        }
        ColorPair newcolor =new ColorPair(UUID.randomUUID(),participant,adventureID,randval);
        groupChat.getColors().add(newcolor);
        colorPairRepository.save(newcolor);
        groupChatRepository.save(groupChat);
        return participant+" successfully added to chat "+adventureID;
    }

    public int getUserColor(UUID groupChatID, UUID userID){
        return groupChatRepository.findGroupChatByGroupChatId(groupChatID).getColor(userID);
    }

    @Override
    @Transactional
    public String sendDirectMessage(UUID chatID,UUID sender, UUID receiver, String msg) {
        DirectChat chat = directChatRepository.findByDirectChatId(chatID);
        if(chat == null){
            throw new ChatNotFoundException(chatID);
        }
        DirectMessage message = new DirectMessage(UUID.randomUUID(),sender,chatID,msg,false,receiver);
        messageRepository.save(message);
        directChatRepository.save(chat);
        return "Message Sent";
    }

    @Override
    @Transactional
    public String sendGroupMessage(UUID chatID, UUID sender,String msg) {
        GroupChat chat = groupChatRepository.getGroupChatByGroupChatId(chatID);
        if(chat == null){
            throw new ChatNotFoundException(chatID);
        }
        GroupMessage message = new GroupMessage(UUID.randomUUID(),sender,chatID,msg);
        messageRepository.save(message);
        groupChatRepository.save(chat);
        return "Message Sent";
    }

    @Override
    public void markDirectMessageRead(UUID id) {
        DirectMessage message = (DirectMessage) messageRepository.findMessageById(id);
        if(message == null){
            throw new MessageNotFoundException(id);
        }

        message.setRead(!message.getRead());
        messageRepository.save(message);
    }

    @Override
    @Transactional
    public void markGroupMessageRead(UUID id, UUID userID) {
        GroupMessage message = (GroupMessage) messageRepository.findMessageById(id);
        if(message == null){
            throw new MessageNotFoundException(id);
        }

        message.getRead().replace(userID,true);
        messageRepository.save(message);
    }

    @Override
    public GroupChatResponseDTO getGroupChatByAdventureID(UUID id) {
        GroupChat chats = groupChatRepository.findAllByAdventureID(id);
        if(chats == null){
            throw new ChatNotFoundException(id);
        }
        List<UUID> messageIds = new ArrayList<>();
        List<Message> messages = messageRepository.findAllByChatId(chats.getGroupChatId());
        for (Message message : messages) {
            messageIds.add(message.getId());
        }
        return new GroupChatResponseDTO(chats.getGroupChatId(),chats.getAdventureID(),chats.getParticipants(),messageIds,chats.getName(), chats.getColors());
    }

    @Override
    public GroupChatResponseDTO getGroupChat(UUID id) {
        GroupChat chat = groupChatRepository.findGroupChatByGroupChatId(id);
        if(chat == null){
            throw new ChatNotFoundException(id);
        }
        List<UUID> messageIds = new ArrayList<>();
        List<Message> messages = messageRepository.findAllByChatId(chat.getGroupChatId());
        for (Message message : messages) {
            messageIds.add(message.getId());
        }

        return new GroupChatResponseDTO(chat.getGroupChatId(),chat.getAdventureID(),chat.getParticipants(),messageIds,chat.getName(), chat.getColors());
    }

    @Override
    public DirectChatResponseDTO getDirectChat(UUID id1, UUID id2) {
        List<DirectChat> chats = directChatRepository.findAllByParticipantsContaining(id1);
        DirectChat chat = null;
        if(chats.isEmpty()){
            throw new ChatNotFoundException();
        }

        for (DirectChat c:chats) {
            if(c.getParticipants().contains(id2)){
                chat = c;
                break;
            }
        }

        if(chat ==null){
            throw new ChatNotFoundException();
        }
        List<UUID> messageIds = new ArrayList<>();
        List<Message> messages = messageRepository.findAllByChatId(chat.getDirectChatId());
        for (Message message : messages) {
            messageIds.add(message.getId());
        }

        return new DirectChatResponseDTO(chat.getDirectChatId(),chat.getParticipants(),messageIds);
    }

    @Override
    public DirectChatResponseDTO getDirectChatByID(UUID id) {
        DirectChat chat = directChatRepository.findByDirectChatId(id);

        if(chat == null){
            throw new ChatNotFoundException(id);
        }
        List<UUID> messageIds = new ArrayList<>();
        List<Message> messages = messageRepository.findAllByChatId(chat.getDirectChatId());
        for (Message message : messages) {
            messageIds.add(message.getId());
        }

        return new DirectChatResponseDTO(chat.getDirectChatId(),chat.getParticipants(),messageIds);
    }

    @Override
    public Message getMessage(UUID id) {
        return messageRepository.findMessageById(id);
    }

    @Override
    public void deleteDirectChat(UUID id) {
        DirectChat chat = directChatRepository.findByDirectChatId(id);
        if(chat == null){
            throw new ChatNotFoundException(id);
        }

        directChatRepository.delete(chat);
    }

    @Override
    public void deleteGroupChat(UUID id) {
        GroupChat chat = groupChatRepository.findGroupChatByGroupChatId(id);
        if(chat == null){
            throw new ChatNotFoundException(id);
        }

       groupChatRepository.delete(chat);
    }
}