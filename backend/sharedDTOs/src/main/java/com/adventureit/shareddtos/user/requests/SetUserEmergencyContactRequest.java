package com.adventureit.shareddtos.user.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class SetUserEmergencyContactRequest {
    private final UUID userId;
    private final String email;

    public SetUserEmergencyContactRequest(@JsonProperty("userId")UUID userId, @JsonProperty("email") String email) {
        this.userId = userId;
        this.email = email;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }
}
