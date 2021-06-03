package com.adventureit.adventureservice.Requests;

import java.util.UUID;

public class AddUserToAdventureRequest {
    private UUID id;
    private UUID advID;

    /**
     * Constructor for AddUserToAdventure request object which takes in the following parameters:
     * @param id = user id
     * @param advID = adventure id
     */

    public AddUserToAdventureRequest(UUID id, UUID advID)
    {
        this.id = id;
        this.advID = advID;
    }

    /**********************GETTERS**********************/

    /**
     * AddUserToAdventure service to retrieve request user id
     * @return id
     */
    public UUID getUserid()
    {
        return this.id;
    }

    /**
     * AddUserToAdventure service to retrieve request adventure id
     * @return advID
     */
    public UUID getAdventureID()
    {
        return this.advID;
    }

    /**********************SETTERS**********************/

    /**
     * AddUserToAdventure service to set request user id
     * @param id
     */
    public void setUserid(UUID id)
    {
        this.id = id;
    }

    /**
     * AddUserToAdventure service to set request adventure id
     * @param advID
     */
    public void setAdventureID(UUID advID)
    {
        this.advID = advID;
    }
}
