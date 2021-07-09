package com.adventureit.userservice.Responses;

import java.util.UUID;

public class GetUserByUUIDDTO {
    private final UUID userID;
    private final String username;
    private final String firstname;
    private final String lastname;
    private final String email;
    private final String phoneNumber;

    public GetUserByUUIDDTO(UUID userID, String username, String firstname, String lastname, String email, String phoneNumber) {
        this.userID = userID;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public UUID getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
