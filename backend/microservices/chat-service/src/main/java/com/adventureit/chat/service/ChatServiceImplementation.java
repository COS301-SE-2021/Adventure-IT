package com.adventureit.chat.service;

import com.adventureit.chat.entity.*;
import com.adventureit.chat.exceptions.ChatNotFoundException;
import com.adventureit.chat.exceptions.GroupChatFullException;
import com.adventureit.chat.exceptions.MessageNotFoundException;
import com.adventureit.chat.repository.*;
import com.adventureit.shareddtos.chat.ColorPairDTO;
import com.adventureit.shareddtos.chat.responses.DirectChatResponseDTO;
import com.adventureit.shareddtos.chat.responses.GroupChatResponseDTO;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.ReadChannel;
import com.google.cloud.storage.*;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.channels.Channels;
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
    MessageInfoRepository messageInfoRepository;
    Random rand;

    private StorageOptions storageOptions;
    private String bucketName;

    public ChatServiceImplementation(ColorPairRepository colorPairRepository,DirectChatRepository directChatRepository,GroupChatRepository groupChatRepository,MessageRepository messageRepository, MessageInfoRepository messageInfoRepository){
        this.groupChatRepository = groupChatRepository;
        this.directChatRepository = directChatRepository;
        this.messageRepository = messageRepository;
        this.colorPairRepository = colorPairRepository;
        this.rand = new Random();
        this.messageInfoRepository = messageInfoRepository;
    }

    @PostConstruct
    private void initializeFirebase() throws IOException {
        bucketName = "adventure-it-bc0b6.appspot.com";
        String projectId = "Adventure-IT";
        FileInputStream serviceAccount = new FileInputStream("C:\\Users\\sgood\\Documents\\CS\\SEM 2\\COS301\\adventure-it-bc0b6-firebase-adminsdk-o2fq8-ad3a51fb5e.json");
        this.storageOptions = StorageOptions.newBuilder().setProjectId(projectId).setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
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
    public String sendDirectMessage(UUID chatID,UUID sender, UUID receiver, String msg) throws IOException {
        DirectChat chat = directChatRepository.findByDirectChatId(chatID);
        if(chat == null){
            throw new ChatNotFoundException(chatID);
        }

        UUID id = UUID.randomUUID();

        DirectMessage message = new DirectMessage(id,sender,chatID,msg,false,receiver);
        MessageInfo messageInfo = new MessageInfo(id,chatID);
        messageInfoRepository.save(messageInfo);
        directChatRepository.save(chat);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(message);
        oos.flush();

        Storage storage = storageOptions.getService();
        BlobId blobId = BlobId.of(bucketName, id.toString());
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        storage.create(blobInfo, bos.toByteArray());

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

        List<ColorPairDTO> colors = new ArrayList<>();
        for(ColorPair c : chats.getColors()){
            colors.add(this.convertToColorPairDTO(c));
        }

        return new GroupChatResponseDTO(chats.getGroupChatId(),chats.getAdventureID(),chats.getParticipants(),messageIds,chats.getName(), colors);
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

        List<ColorPairDTO> colors = new ArrayList<>();
        for(ColorPair c : chat.getColors()){
            colors.add(this.convertToColorPairDTO(c));
        }

        return new GroupChatResponseDTO(chat.getGroupChatId(),chat.getAdventureID(),chat.getParticipants(),messageIds,chat.getName(), colors);
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
    public Message getMessage(UUID id) throws NotFoundException, IOException, ClassNotFoundException {
        MessageInfo messageInfo = messageInfoRepository.findMessageInfoById(id);
        if(messageInfo == null){
            throw new NotFoundException("Get Message: Message does not exist");
        }

        Storage storage = storageOptions.getService();
        Blob blob = storage.get(BlobId.of(bucketName, id.toString()));
        ReadChannel reader = blob.reader();
        InputStream inputStream = Channels.newInputStream(reader);
        byte[] content = inputStream.readAllBytes();

        ByteArrayInputStream in = new ByteArrayInputStream(content);
        ObjectInputStream is = new ObjectInputStream(in);

        return (Message) is.readObject();
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

    public ColorPairDTO convertToColorPairDTO(ColorPair c){
        return new ColorPairDTO(c.getColorPairId(), c.getUserID(), c.getAdventureId(), c.getColor());
    }
}
