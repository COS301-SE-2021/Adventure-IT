package com.adventureit.shareddtos.user.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class EditUserProfileRequest {
    private final UUID userId ;
    private String username ;
    private String firstName;
    private String lastName;
    private String email;

    public EditUserProfileRequest(@JsonProperty("userId")UUID userId,@JsonProperty("username") String username,@JsonProperty("firstName") String firstName,@JsonProperty("lastName") String lastName,@JsonProperty("email") String email) {
        this.userId = userId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}
