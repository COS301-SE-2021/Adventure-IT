package com.adventureit.userservice.Requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class RegisterUserRequest {
    private UUID userID;
    private String fName;
    private String lName;
    private String email;
    private String username;

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
     * User service to set request first name
     * @param fName first name of user to set
     */
    public void setfName(String fName) {
        this.fName = fName;
    }

    /**
     * User service to set request last name
     * @param lName last name of user to set
     */
    public void setlName(String lName) {
        this.lName = lName;
    }

    /**
     * User service to set request email
     * @param email email of user to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * User service to get user ID
     */
    public UUID getUserID() {
        return userID;
    }
}