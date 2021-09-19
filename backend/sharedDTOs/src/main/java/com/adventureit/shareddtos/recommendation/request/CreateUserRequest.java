package com.adventureit.shareddtos.recommendation.request;

import java.util.UUID;

public class CreateUserRequest {
    public UUID userId;

    public CreateUserRequest(){}

    public CreateUserRequest(UUID id){
        this.userId = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
