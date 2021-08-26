package com.adventureit.userservice.responses;

import java.util.Date;
import java.util.UUID;

public class FriendDTO {
    private UUID id;
    private UUID firstUser;
    private UUID secondUser;
    private Date createdDate;
    boolean accepted;


    public FriendDTO(){}
    public FriendDTO(UUID id, UUID firstUser, UUID secondUser, Date createdDate, boolean accepted){
        this.id = id;
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.createdDate = createdDate;
        this.accepted = accepted;
    }

    public UUID getId() {
        return id;
    }

    public UUID getSecondUser() {
        return secondUser;
    }

    public UUID getFirstUser() {
        return firstUser;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public boolean isAccepted() {
        return accepted;
    }
}
