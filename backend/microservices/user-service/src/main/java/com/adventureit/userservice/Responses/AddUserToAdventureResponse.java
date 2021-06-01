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

    public boolean isSuccess()
    {
        return this.success;
    }

    public String getMessage()
    {
        return this.message;
    }

    public String getToken()
    {
        return this.token;
    }

    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public void setToken(String token)
    {
        this.token = token;
    }
}
