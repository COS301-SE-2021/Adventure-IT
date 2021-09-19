package com.adventureit.maincontroller.responses;

import com.adventureit.shareddtos.user.responses.GetUserByUUIDDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GroupMessageResponseDTO {
    UUID id;
    GetUserByUUIDDTO sender;
    String message;
    LocalDateTime timestamp;

    public GroupMessageResponseDTO(UUID id, GetUserByUUIDDTO sender, String message, LocalDateTime timestamp){
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
