package com.adventureit.userservice.Requests;

import java.util.UUID;

public class AddUserToAdventureRequest {
    private UUID id;
    private String adventureName;

    public AddUserToAdventureRequest(UUID id, String adventureName)
    {
        this.id = id;
        this.adventureName = adventureName;

        //getUser(this.id)
    }
}
