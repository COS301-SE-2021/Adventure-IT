package com.adventureit.chat.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class MessageInfo {
    @Id
    UUID id;
    UUID chatID;

    public MessageInfo(UUID id, UUID chatID) {
        this.id = id;
        this.chatID = chatID;
    }


    public MessageInfo() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getChatID() {
        return chatID;
    }

    public void setChatID(UUID chatID) {
        this.chatID = chatID;
    }
}
