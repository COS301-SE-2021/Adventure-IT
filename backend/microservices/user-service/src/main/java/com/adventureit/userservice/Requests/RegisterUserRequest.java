package com.adventureit.userservice.Requests;

public class RegisterUserRequest {
    private String fName;
    private String lName;
    private String email;
    private String password;
    private String phoneNum;


    public RegisterUserRequest(String fName,String lName,String email,String password,String phoneNum){
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.phoneNum = phoneNum;
        this.password = password;
    }
    /*GETTERS*/
    public String getfName() {
        return fName;
    }
    public String getlName() {
        return lName;
    }
    public String getPhoneNum() {
        return phoneNum;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    /*SETTERS*/
    public void setPassword(String password) {
        this.password = password;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setlName(String lName) {
        this.lName = lName;
    }
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
    public void setfName(String fName) {
        this.fName = fName;
    }
}