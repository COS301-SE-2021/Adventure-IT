package com.adventureit.userservice.Responses;

import com.adventureit.userservice.Token.RegistrationToken;

public class RegisterUserResponse {
    private boolean success;
    private String message;

    /**
     * RegisterUserResponse constructor which takes the following parameters:
     * @param success
     * @param message
     */
    public RegisterUserResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
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

    /************************SETTERS****************************/
    /**
     * RegisterUserResponse service to set success boolean
     * @param success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * RegisterUserResponse service to set response message
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}