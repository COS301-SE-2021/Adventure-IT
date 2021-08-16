package com.adventureit.maincontroller.Responses;

import com.adventureit.userservice.Responses.GetUserByUUIDDTO;

import java.time.LocalDateTime;
import java.util.UUID;

public class DirectMessageResponseDTO {
    java.util.UUID id;
    GetUserByUUIDDTO sender;
    GetUserByUUIDDTO receiver;
    String message;
    Boolean read = false;
    LocalDateTime timestamp;

    public DirectMessageResponseDTO(UUID id, GetUserByUUIDDTO sender, GetUserByUUIDDTO receiver, LocalDateTime timestamp, String message, Boolean read) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.read = read;
        this.timestamp = timestamp;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public GetUserByUUIDDTO getReceiver() {
        return receiver;
    }

    public void setReceiver(GetUserByUUIDDTO receiver) {
        this.receiver = receiver;
    }

    public GetUserByUUIDDTO getSender() {
        return sender;
    }

    public void setSender(GetUserByUUIDDTO sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
