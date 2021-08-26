package com.adventureit.chat.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class SendDirectMessageRequestDTO {
    UUID chatID;
    UUID sender;
    UUID receiver;
    String msg;

    public SendDirectMessageRequestDTO(@JsonProperty("chatID") UUID chatID,@JsonProperty("sender") UUID sender,@JsonProperty("receiver") UUID receiver,@JsonProperty("msg") String msg) {
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
