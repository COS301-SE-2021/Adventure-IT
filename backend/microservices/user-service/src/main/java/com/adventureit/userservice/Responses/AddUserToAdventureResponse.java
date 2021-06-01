package com.adventureit.userservice.Responses;

public class AddUserToAdventureResponse {
    private boolean success;
    private String message;
    private String token;

    public AddUserToAdventureResponse(boolean success,String token, String message)
    {
        this.success = success;
        this.message = message;
        this.token =token;
    }
}
