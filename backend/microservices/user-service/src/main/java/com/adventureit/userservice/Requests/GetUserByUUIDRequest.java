package com.adventureit.userservice.Requests;

import java.util.UUID;

public class GetUserByUUIDRequest {
    private UUID userID;

    public GetUserByUUIDRequest(UUID userID) {
        this.userID = userID;
    }


    public UUID getUserID() {
        return userID;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
    }
}
