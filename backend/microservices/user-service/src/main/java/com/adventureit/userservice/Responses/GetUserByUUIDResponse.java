package com.adventureit.userservice.Responses;

import com.adventureit.userservice.Entities.User;

public class GetUserByUUIDResponse {
    private boolean success;
    private User user;

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
