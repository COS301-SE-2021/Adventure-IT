package com.adventureit.chat.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class CreateDirectChatRequest {
    private final UUID user1Id;
    private final UUID user2Id;

    public CreateDirectChatRequest(@JsonProperty("user1Id") UUID user1Id,@JsonProperty("user2Id") UUID user2Id) {
        this.user1Id = user1Id;
        this.user2Id = user2Id;
    }

    public UUID getUser1Id() {
        return user1Id;
    }

    public UUID getUser2Id() {
        return user2Id;
    }
}
