package com.adventureit.userservice.Responses;

public class RegisterUserResponse {
    private boolean success;
    private String message;
    private String token;

    /**
     * RegisterUserResponse constructor which takes the following parameters:
     * @param success
     * @param token
     * @param message
     */
    public RegisterUserResponse(boolean success,String token, String message) {
        this.success = success;
        this.message = message;
        this.token =token;
    }

    /******************************GETTERS***************************/
    /**
     * RegisterUserResponse service to retrieve success bool
     * @return success
     */
    public boolean isSuccess(){
        return success;
    }

    /**
     * RegisterUserResponse service to retrieve response message
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * RegisterUserResponse service to retrieve response token
     * @return token
     */
    public String getToken() {
        return token;
    }

    /************************SETTERS****************************/
    /**
     * RegisterUserResponse service to set success boolean
     * @param success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * RegisterUserResponse service to set response token
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * RegisterUserResponse service to set response message
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}