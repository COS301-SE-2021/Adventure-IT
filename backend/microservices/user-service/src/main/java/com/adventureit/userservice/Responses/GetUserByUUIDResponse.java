package com.adventureit.userservice.Responses;

import com.adventureit.userservice.Entities.User;

public class GetUserByUUIDResponse {
    private boolean success;
    private User user;

    /**
     * This ojcect will store the response attributes from the GetUserByUUID service, currently a mock
     * user will be sent back for testing purposes but for future implementation a user will be found from the database
     *
     * @param success success attribute to indicate whether the service was successful
     * @param user user to be returned
     */
    public GetUserByUUIDResponse(boolean success, User user) {
        this.success = success;
        this.user = user;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
