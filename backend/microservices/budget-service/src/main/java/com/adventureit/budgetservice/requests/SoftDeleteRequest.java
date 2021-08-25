package com.adventureit.budgetservice.requests;

import java.util.UUID;

public class SoftDeleteRequest {
    private UUID id;
    private final UUID userID;

    public SoftDeleteRequest(UUID id, UUID userID){
        this.id = id;
        this.userID = userID;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserID() {
        return userID;
    }
}
