package com.adventureit.chat.Service;

import com.adventureit.chat.Entity.*;
import com.adventureit.chat.Exceptions.GroupChatFullException;
import com.adventureit.chat.Repository.ChatRepository;
import com.adventureit.chat.Repository.ColorPairRepository;
import com.adventureit.chat.Repository.MessageRepository;
import com.adventureit.chat.Responses.DirectChatResponseDTO;
import com.adventureit.chat.Responses.GroupChatResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class ChatServiceImplementation implements ChatService {
    @Autowired
    ChatRepository chatRepository;
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    ColorPairRepository colorPairRepository;

    public ChatServiceImplementation(ChatRepository chatRepository,MessageRepository messageRepository){
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
    }

    @Override
    public String createDirectChat(UUID user1, UUID user2) {
        UUID id = UUID.randomUUID();
        DirectChat directChat = new DirectChat(user1,user2);
        chatRepository.save(directChat);
        return "Chat successfully created";
    }

    @Override
    public String createGroupChat( UUID adventureID, List<UUID> participants, String name) {
        UUID id = UUID.randomUUID();
        List<ColorPair> list = new ArrayList<ColorPair>();
        List<Integer> checked = new ArrayList<Integer>();
        Random rand = new Random();
        for (UUID participant : participants) {
            int randomCol = rand.nextInt(359)+1;
                while (checked.contains(randomCol))
                    randomCol = rand.nextInt(359)+1;
            checked.add(randomCol);
            list.add(new ColorPair(adventureID,participant, randomCol));
            colorPairRepository.save(new ColorPair(adventureID,participant, randomCol));
        }
        GroupChat groupChat = new GroupChat(id,adventureID,participants,list,name);
        chatRepository.save(groupChat);
        return "Group Chat successfully created";
    }

    @Override
    public String addParticipant( UUID adventureID, UUID participant) {
        Chat groupChat = chatRepository.findByAdventureID(adventureID);
        groupChat.getParticipants().add(participant);
        List<Integer> checked = new ArrayList<Integer>();
        List<ColorPair> cp = colorPairRepository.findAllByAdventureId(adventureID);

        if(cp.size() >=360 ){
            throw new GroupChatFullException("This group chat has reached maximum capacity");
        }
        Random rand = new Random();
        for (int x =0; x<cp.size();x++) {
            checked.add(cp.get(x).getColor());
        }
        int randval = rand.nextInt(359)+1;
        while(checked.contains(randval)){
             randval = rand.nextInt(359)+1;
        }
        colorPairRepository.save(new ColorPair(adventureID,participant,randval));
        chatRepository.save(groupChat);
        return participant+" successfully added to chat "+adventureID;
    }

    public int getUserColor(UUID groupChatID, UUID userID){
        return chatRepository.findChatById(groupChatID).getColor(userID);
    }

    @Override
    @Transactional
    public String sendDirectMessage(UUID chatID,UUID sender, UUID receiver, String msg) throws Exception {
        Chat chat = chatRepository.findChatById(chatID);
        if(chat == null){
            throw new Exception("Chat does not exist");
        }
        DirectMessage message = new DirectMessage(sender,receiver,msg);
        messageRepository.save(message);
        chat.getMessages().add(message.getId());
        chatRepository.save(chat);
        return "Message Sent";
    }

    @Override
    @Transactional
    public String sendGroupMessage(UUID chatID, UUID sender,String msg) throws Exception {
        Chat chat = chatRepository.findChatById(chatID);
        if(chat == null){
            throw new Exception("Chat does not exist");
        }

        List<UUID> rec = new ArrayList<>(chat.getParticipants());
        rec.remove(sender);

        GroupMessage message = new GroupMessage(sender,rec,msg);
        messageRepository.save(message);
        chat.getMessages().add(message.getId());
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
    @Transactional
    public void markGroupMessageRead(UUID id, UUID userID) throws Exception {
        GroupMessage message = (GroupMessage) messageRepository.findMessageById(id);
        if(message == null){
            throw new Exception("Message does not exist");
        }

        message.getRead().replace(userID,true);
        messageRepository.save(message);
    }

    @Override
    public GroupChatResponseDTO getGroupChatByAdventureID(UUID id) throws Exception {
        GroupChat chat = (GroupChat) chatRepository.findByAdventureID(id);
        if(chat == null){
            throw new Exception("Chat does not exist");
        }

        return new GroupChatResponseDTO(chat.getId(),chat.getAdventureID(),chat.getParticipants(),chat.getMessages(),chat.getName(), chat.getColors());
    }

    @Override
    public GroupChatResponseDTO getGroupChat(UUID id) throws Exception {
        GroupChat chat = (GroupChat) chatRepository.findChatById(id);
        if(chat == null){
            throw new Exception("Chat does not exist");
        }

        return new GroupChatResponseDTO(chat.getId(),chat.getAdventureID(),chat.getParticipants(),chat.getMessages(),chat.getName(), chat.getColors());
    }

    @Override
    public DirectChatResponseDTO getDirectChat(UUID ID1, UUID ID2) throws Exception {
        List<Chat> chats = chatRepository.findAllByParticipantsContaining(ID1);
        Chat chat = null;
        if(chats.isEmpty()){
            throw new Exception("Chat does not exist");
        }

        for (Chat c:chats) {
            if(c.getParticipants().contains(ID2)){
                chat = c;
                break;
            }
        }

        if(chat ==null){
            throw new Exception("Chat does not exist");
        }

        return new DirectChatResponseDTO(chat.getId(),chat.getParticipants(),chat.getMessages());
    }

    @Override
    public DirectChatResponseDTO getDirectChatByID(UUID id) throws Exception {
        DirectChat chat = (DirectChat) chatRepository.findChatById(id);

        if(chat == null){
            throw new Exception("Chat does not exist");
        }

        return new DirectChatResponseDTO(chat.getId(),chat.getParticipants(),chat.getMessages());
    }

    @Override
    public Message getMessage(UUID id) {
        return messageRepository.findMessageById(id);
    }

    @Override
    public void deleteChat(UUID id) throws Exception {
        Chat chat = chatRepository.findChatById(id);
        if(chat == null){
            throw new Exception("Chat does not exist");
        }

        chatRepository.delete(chat);
    }
}
