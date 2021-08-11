package com.adventureit.budgetservice.Requests;

import java.util.UUID;

public class SoftDeleteRequest {
    private UUID id;
    private UUID userID;

    public SoftDeleteRequest(){}

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

    public void setUserID(UUID userID) {
        this.userID = userID;
    }
}
