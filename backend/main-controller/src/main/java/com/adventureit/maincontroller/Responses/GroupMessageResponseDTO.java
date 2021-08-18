package com.adventureit.maincontroller.Responses;

import com.adventureit.userservice.Responses.GetUserByUUIDDTO;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GroupMessageResponseDTO {
    UUID id;
    GetUserByUUIDDTO sender;
    String message;
    LocalDateTime timestamp;
    List<GetUserByUUIDDTO> receivers;
    Map<UUID, Boolean> read = new HashMap<>();

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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public List<GetUserByUUIDDTO> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<GetUserByUUIDDTO> receivers) {
        this.receivers = receivers;
    }

    public Map<UUID, Boolean> getRead() {
        return read;
    }

    public void setRead(Map<UUID, Boolean> read) {
        this.read = read;
    }
}
