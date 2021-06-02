package com.adventureit.userservice.Requests;

import java.util.UUID;

public class GetUserByUUIDRequest {
    private UUID userID;


    /**
     * This service will be used to generate a GetUserByUUID request
     * @param userID user Id of the user that need to be retrieved
     */
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
