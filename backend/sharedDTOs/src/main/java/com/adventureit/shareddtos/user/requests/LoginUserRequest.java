package com.adventureit.shareddtos.user.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginUserRequest {
    private final String username;
    private final String password;

    public LoginUserRequest(@JsonProperty("username") String username,@JsonProperty("password") String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
