package com.adventureit.adventureservice.Responses;

public class AddUserToAdventureResponse {
    private boolean success;
    private String message;

    /**
     * AddUserToAdventure response constructor takes the following parameters:
     * @param success
     * @param message
     */
    public AddUserToAdventureResponse(boolean success, String message)
    {
        this.success = success;
        this.message = message;
    }

    /******************************GETTERS***************************/

    /**
     * AddUserToAdventureResponse service to retrieve success boolean
     * @return this.success
     */
    public boolean isSuccess()
    {
        return this.success;
    }

    /**
     * AddUserToAdventure service to retrieve message
     * @return this.message
     */
    public String getMessage()
    {
        return this.message;
    }

    /******************************SETTERS***************************/

    /**
     * AddUserToAdventure response service to set success boolean
     * @param success
     */
    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    /**
     * AddUserToAdventure response service to set message string
     * @param message
     */
    public void setMessage(String message)
    {
        this.message = message;
    }
}
