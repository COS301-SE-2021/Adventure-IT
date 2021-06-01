package com.adventureit.userservice.Responses;

import com.adventureit.userservice.Service.User;

public class GetUserByUUIDResponse {
    private User user;
    private boolean sucess;

    public GetUserByUUIDResponse(User user, boolean sucess) {
        this.user = user;
        this.sucess = sucess;
    }

    public boolean isSucess() {
        return sucess;
    }

    public void setSucess(boolean sucess) {
        this.sucess = sucess;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
