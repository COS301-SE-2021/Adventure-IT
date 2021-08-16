package com.adventureit.chat.Requests;

import java.util.UUID;

public class SendDirectMessageRequestDTO {
    UUID chatID;
    UUID sender;
    UUID receiver;
    String msg;

    public SendDirectMessageRequestDTO(UUID chatID, UUID sender, UUID receiver, String msg) {
        this.chatID = chatID;
        this.sender = sender;
        this.receiver = receiver;
        this.msg = msg;
    }

    public UUID getChatID() {
        return chatID;
    }

    public void setChatID(UUID chatID) {
        this.chatID = chatID;
    }

    public UUID getSender() {
        return sender;
    }

    public void setSender(UUID sender) {
        this.sender = sender;
    }

    public UUID getReceiver() {
        return receiver;
    }

    public void setReceiver(UUID receiver) {
        this.receiver = receiver;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
