package com.adventureit.userservice.Requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterUserRequest {
    private String fName;
    private String lName;
    private String email;
    private String password;
    private String phoneNum;

    /**
     * Constructor for request object which takes in the following parameters:
     * @param fName = user first name
     * @param lName = user last name
     * @param email = user email
     * @param password = user password
     * @param phoneNum =use phone number
     */

    public RegisterUserRequest( @JsonProperty("fname") String fName,@JsonProperty("lname") String lName,@JsonProperty("email") String email,@JsonProperty("password") String password,@JsonProperty("phoneN") String phoneNum){
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.phoneNum = phoneNum;
        this.password = password;
    }


    /**********************GETTERS**********************/



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

    /*******************SETTERS********************/

    /**
     * User service to set request first name
     * @param fName
     */
    public void setfName(String fName) {
        this.fName = fName;
    }

    /**
     * User service to set request last name
     * @param lName
     */
    public void setlName(String lName) {
        this.lName = lName;
    }

    /**
     * User service to set request phone number
     * @param phoneNum
     */
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    /**
     * User service to set request email
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * User service to set request password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }




}