package com.adventureit.maincontroller.responses;

import com.adventureit.shareddtos.user.responses.GetUserByUUIDDTO;

import java.time.LocalDateTime;
import java.util.UUID;

public class DirectMessageResponseDTO {
    java.util.UUID id;
    GetUserByUUIDDTO sender;
    GetUserByUUIDDTO receiver;
    String message;
    Boolean read;
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

}
