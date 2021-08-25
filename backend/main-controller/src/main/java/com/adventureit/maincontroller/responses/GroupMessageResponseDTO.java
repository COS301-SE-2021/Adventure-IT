package com.adventureit.maincontroller.responses;

import com.adventureit.userservice.responses.GetUserByUUIDDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GroupMessageResponseDTO {
    UUID id;
    GetUserByUUIDDTO sender;
    String message;
    LocalDateTime timestamp;
    List<GetUserByUUIDDTO> receivers;
    Map<UUID, Boolean> read;

    public GroupMessageResponseDTO(UUID id, GetUserByUUIDDTO sender, String message, LocalDateTime timestamp, List<GetUserByUUIDDTO> receivers, Map<UUID,Boolean> read){
        this.id = id;
        this.sender = sender;
        this.message = message;
        this.timestamp = timestamp;
        this.receivers = receivers;
        this.read = read;
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
