package com.adventureit.maincontroller.responses;

import com.adventureit.shareddtos.user.responses.GetUserByUUIDDTO;

import java.time.LocalDateTime;
import java.util.UUID;

public class DirectMessageResponseDTO {
    java.util.UUID id;
    GetUserByUUIDDTO sender;
    String message;
    LocalDateTime timestamp;

    public DirectMessageResponseDTO(UUID id, GetUserByUUIDDTO sender, LocalDateTime timestamp, String message) {
        this.id = id;
        this.sender = sender;
        this.message = message;
        this.timestamp = timestamp;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public GetUserByUUIDDTO getSender() {
        return sender;
    }


    public String getMessage() {
        return message;
    }


}
