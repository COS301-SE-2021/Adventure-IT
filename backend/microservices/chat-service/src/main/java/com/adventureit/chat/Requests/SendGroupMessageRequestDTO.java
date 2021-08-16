package com.adventureit.chat.Requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class SendGroupMessageRequestDTO {
    UUID chatID;
    UUID sender;
    String msg;

    public SendGroupMessageRequestDTO(@JsonProperty("chatId")UUID chatID,@JsonProperty("sender") UUID sender,@JsonProperty("message") String msg){
        this.chatID = chatID;
        this.sender = sender;
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
