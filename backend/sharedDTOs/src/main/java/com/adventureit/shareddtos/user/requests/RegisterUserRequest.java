package com.adventureit.shareddtos.user.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class RegisterUserRequest {
    private final UUID userID;
    private final String fName;
    private final String lName;
    private final String email;
    private final String username;

    /**
     * Constructor for request object which takes in the following parameters:
     * @param fName = user first name
     * @param lName = user last name
     * @param email = user email
     */

    public RegisterUserRequest(@JsonProperty("userID") UUID userID, @JsonProperty("firstName") String fName,@JsonProperty("lastName") String lName, @JsonProperty("username") String uName,@JsonProperty("email") String email){
        this.userID = userID;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.username = uName;
    }

    public String getUsername() {
        return username;
    }

    /**
     * RegisterUserRequest service to retrieve request first name
     * @return fName
     */
    public String getfName() {
        return fName;
    }

    /**
     * RegisterUserRequest service to retrieve request last name
     * @return lName
     */
    public String getlName() {
        return lName;
    }

    /**
     * RegisterUserRequest service to retrieve request email
     * @return email
     */
    public String getEmail() {
        return email;
    }


    /**
     * User service to get user ID
     */
    public UUID getUserID() {
        return userID;
    }
}