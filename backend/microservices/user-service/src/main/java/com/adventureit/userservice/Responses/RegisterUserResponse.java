package com.adventureit.userservice.Responses;

public class RegisterUserResponse {
    private boolean success;
    private String message;
    private String token;

    public RegisterUserResponse(boolean success,String token, String message) {
        this.success = success;
        this.message = message;
        this.token =token;
    }

    /*GETTERS */
    public boolean isSuccess(){
        return success;
    }
    public String getMessage() {
        return message;
    }
    public String getToken() {
        return token;
    }
    /*SETTERS*/
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}