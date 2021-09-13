package com.adventureit.shareddtos.user.responses;

public class RegisterUserResponse {
    private final boolean success;
    private String message;

    /**
     * RegisterUserResponse constructor which takes the following parameters:
     * @param success success boolean for whether registration was successful or not
     * @param message message to accompany response
     */
    public RegisterUserResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

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
     * RegisterUserResponse service to set response message
     * @param message message to be set
     */
    public void setMessage(String message) {
        this.message = message;
    }
}