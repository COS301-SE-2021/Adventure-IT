package com.adventureit.userservice.Requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginUserRequest {
    private final String email;
    private final String password;

    public LoginUserRequest(@JsonProperty("email") String email,@JsonProperty("password") String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
