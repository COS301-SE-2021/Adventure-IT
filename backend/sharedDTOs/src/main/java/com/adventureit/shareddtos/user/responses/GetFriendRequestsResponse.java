package com.adventureit.shareddtos.user.responses;

import java.util.Date;
import java.util.UUID;

public class GetFriendRequestsResponse {
    private UUID id;
    private final UUID firstUser;
    private final UUID secondUser;
    private final Date createdDate;
    boolean accepted;
    private final String requester;

    public GetFriendRequestsResponse(UUID id,UUID firstUser, UUID secondUser, Date createdDate, boolean accepted, GetUserByUUIDDTO requester){
        this.id = id;
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.createdDate = createdDate;
        this.accepted = accepted;
        this.requester = requester.getUsername();
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

    public String getRequester() {
        return requester;
    }

}
