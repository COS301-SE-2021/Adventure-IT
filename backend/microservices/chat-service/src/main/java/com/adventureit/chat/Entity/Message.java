package com.adventureit.chat.Entity;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class Message {
    @Id
    UUID id;
    @NotNull
    UUID sender;
    @ElementCollection
    List<UUID> receivers = new ArrayList<UUID>();
    @NotNull
    String message;
    @NotNull
    LocalDateTime timestamp;
    @NotNull
    Boolean read = false;

    public Message(UUID id, UUID sender, List<UUID> receivers,String message){
        this.id = id;
        this.sender = sender;
        this.receivers = receivers;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public Message() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public void setReceivers(List<UUID> receivers) {
        this.receivers = receivers;
    }

    public List<UUID> getReceivers() {
        return receivers;
    }

    public void setSender(UUID sender) {
        this.sender = sender;
    }

    public UUID getSender() {
        return sender;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }


}
