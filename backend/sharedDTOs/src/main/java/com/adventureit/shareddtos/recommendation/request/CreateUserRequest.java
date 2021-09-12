package com.adventureit.shareddtos.recommendation.request;

import java.util.UUID;

public class CreateUserRequest {
    public UUID userId;
    public CreateUserRequest(UUID id){
        this.userId = id;
    }
}
