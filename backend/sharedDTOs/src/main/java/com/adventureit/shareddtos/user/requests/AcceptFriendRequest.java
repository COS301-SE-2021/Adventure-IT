package com.adventureit.shareddtos.user.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public class AcceptFriendRequest {
    UUID id;
    UUID userId1;
    UUID userId2;

    public AcceptFriendRequest(){}

    public AcceptFriendRequest(@JsonProperty("id") UUID id, @JsonProperty("userId1") UUID userId1, @JsonProperty("userId2") UUID userId2){
        this.id = id;
        this.userId1 = userId1;
        this.userId2 = userId2;
    }

    public UUID getUserId2() {
        return userId2;
    }

    public UUID getUserId1() {
        return userId1;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
