package com.adventureit.userservice.Requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterUserRequest {
    private String fName;
    private String lName;
    private String email;
    private String password;
    private String phoneNum;
    private String username;

    /**
     * Constructor for request object which takes in the following parameters:
     * @param fName = user first name
     * @param lName = user last name
     * @param email = user email
     * @param password = user password
     * @param phoneNum =use phone number
     */

    public RegisterUserRequest( @JsonProperty("firstName") String fName,@JsonProperty("lastName") String lName, @JsonProperty("username") String uName,@JsonProperty("email") String email,@JsonProperty("password") String password,@JsonProperty("phoneNumber") String phoneNum){
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.phoneNum = phoneNum;
        this.password = password;
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
     * RegisterUserRequest service to retrieve request phone number
     * @return phoneNum
     */
    public String getPhoneNum() {
        return phoneNum;
    }

    /**
     * RegisterUserRequest service to retrieve request email
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * RegisterUserRequest service to retrieve request password
     * @return password
     */
    public String getPassword() {
        return password;
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
     * User service to set request phone number
     * @param phoneNum phone number of user to set
     */
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    /**
     * User service to set request email
     * @param email email of user to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * User service to set request password
     * @param password password of user to set
     */
    public void setPassword(String password) {
        this.password = password;
    }




}