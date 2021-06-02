package com.adventureit.adventureservice.Responses;

public class AddUserToAdventureResponse {
    private boolean success;
    private String message;

    public AddUserToAdventureResponse(boolean success, String message)
    {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess()
    {
        return this.success;
    }

    public String getMessage()
    {
        return this.message;
    }

    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}
