package com.adventureit.chat.Service;

import com.adventureit.chat.Entity.*;
import com.adventureit.chat.Exceptions.GroupChatFullException;
import com.adventureit.chat.Repository.ColorPairRepository;
import com.adventureit.chat.Repository.DirectChatRepository;
import com.adventureit.chat.Repository.GroupChatRepository;
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
    GroupChatRepository groupChatRepository;
    @Autowired
    DirectChatRepository directChatRepository;
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    ColorPairRepository colorPairRepository;

    @Autowired
    public ChatServiceImplementation(ColorPairRepository colorPairRepository,DirectChatRepository directChatRepository,GroupChatRepository groupChatRepository,MessageRepository messageRepository){
        this.groupChatRepository = groupChatRepository;
        this.directChatRepository = directChatRepository;
        this.messageRepository = messageRepository;
        this.colorPairRepository = colorPairRepository;
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
        List<ColorPair> list = new ArrayList<ColorPair>();
        List<Integer> checked = new ArrayList<Integer>();
        Random rand = new Random();
        for (UUID participant : participants) {
            int randomCol = rand.nextInt(359) + 1;
            while (checked.contains(randomCol)) {
                randomCol = rand.nextInt(359) + 1;
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
        colorPairRepository.save(new ColorPair(UUID.randomUUID(),adventureID,participant,randval));
        groupChatRepository.save(groupChat);
        return participant+" successfully added to chat "+adventureID;
    }

    public int getUserColor(UUID groupChatID, UUID userID){
        return groupChatRepository.findGroupChatByGroupChatId(groupChatID).getColor(userID);
    }

    @Override
    @Transactional
    public String sendDirectMessage(UUID chatID,UUID sender, UUID receiver, String msg) throws Exception {
        DirectChat chat = directChatRepository.findByDirectChatId(chatID);
        if(chat == null){
            throw new Exception("Chat does not exist");
        }
        DirectMessage message = new DirectMessage(UUID.randomUUID(),sender,chatID,msg,false,receiver);
        messageRepository.save(message);
        directChatRepository.save(chat);
        return "Message Sent";
    }

    @Override
    @Transactional
    public String sendGroupMessage(UUID chatID, UUID sender,String msg) throws Exception {
        System.out.println("here1");
        GroupChat chat = groupChatRepository.getGroupChatByGroupChatId(chatID);
        System.out.println("here2");
        if(chat == null){
            throw new Exception("Chat does not exist");
        }
        System.out.println("here3");
        List<UUID> rec = new ArrayList<>(chat.getParticipants());
        System.out.println("here4");
        rec.remove(sender);
        System.out.println("here5");
        System.out.println("here6");
        GroupMessage message = new GroupMessage(UUID.randomUUID(),sender,chatID,msg);
        System.out.println("here7");
        messageRepository.save(message);
        System.out.println("here8");
        groupChatRepository.save(chat);
        System.out.println("here9");
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
        GroupChat chats = groupChatRepository.findAllByAdventureID(id);
        if(chats == null){
            throw new Exception("Chat does not exist");
        }
        List<UUID> messageIds = new ArrayList<>();
        List<Message> messages = messageRepository.findAllByChatId(chats.getGroupChatId());
        for (Message message : messages) {
            messageIds.add(message.getId());
        }
        return new GroupChatResponseDTO(chats.getGroupChatId(),chats.getAdventureID(),chats.getParticipants(),messageIds,chats.getName(), chats.getColors());
    }

    @Override
    public GroupChatResponseDTO getGroupChat(UUID id) throws Exception {
        GroupChat chat = groupChatRepository.findGroupChatByGroupChatId(id);
        if(chat == null){
            throw new Exception("Chat does not exist");
        }
        List<UUID> messageIds = new ArrayList<>();
        List<Message> messages = messageRepository.findAllByChatId(chat.getGroupChatId());
        for (Message message : messages) {
            messageIds.add(message.getId());
        }

        return new GroupChatResponseDTO(chat.getGroupChatId(),chat.getAdventureID(),chat.getParticipants(),messageIds,chat.getName(), chat.getColors());
    }

    @Override
    public DirectChatResponseDTO getDirectChat(UUID ID1, UUID ID2) throws Exception {
        List<DirectChat> chats = directChatRepository.findAllByParticipantsContaining(ID1);
        DirectChat chat = null;
        if(chats.isEmpty()){
            throw new Exception("Chat does not exist");
        }

        for (DirectChat c:chats) {
            if(c.getParticipants().contains(ID2)){
                chat = c;
                break;
            }
        }

        if(chat ==null){
            throw new Exception("Chat does not exist");
        }
        List<UUID> messageIds = new ArrayList<>();
        List<Message> messages = messageRepository.findAllByChatId(chat.getDirectChatId());
        for (Message message : messages) {
            messageIds.add(message.getId());
        }

        return new DirectChatResponseDTO(chat.getDirectChatId(),chat.getParticipants(),messageIds);
    }

    @Override
    public DirectChatResponseDTO getDirectChatByID(UUID id) throws Exception {
        DirectChat chat = directChatRepository.findByDirectChatId(id);

        if(chat == null){
            throw new Exception("Chat does not exist");
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
    public void deleteDirectChat(UUID id) throws Exception {
        DirectChat chat = directChatRepository.findByDirectChatId(id);
        if(chat == null){
            throw new Exception("Chat does not exist");
        }

        directChatRepository.delete(chat);
    }

    @Override
    public void deleteGroupChat(UUID id) throws Exception {
        GroupChat chat = groupChatRepository.findGroupChatByGroupChatId(id);
        if(chat == null){
            throw new Exception("Chat does not exist");
        }

       groupChatRepository.delete(chat);
    }
}
