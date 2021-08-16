package com.adventureit.userservice.Requests;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class AcceptFriendRequest {
    UUID id;
    UUID ID1;
    UUID ID2;

    public AcceptFriendRequest(){}

    public AcceptFriendRequest(@JsonProperty("id") UUID id,@JsonProperty("ID1") UUID ID1, @JsonProperty("ID2") UUID ID2){
        this.id = id;
        this.ID1 = ID1;
        this.ID2 = ID2;
    }

    public UUID getID2() {
        return ID2;
    }

    public UUID getID1() {
        return ID1;
    }

    public void setID2(UUID ID2) {
        this.ID2 = ID2;
    }

    public void setID1(UUID ID1) {
        this.ID1 = ID1;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
