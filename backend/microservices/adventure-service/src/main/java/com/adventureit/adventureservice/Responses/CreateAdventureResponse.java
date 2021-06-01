package com.adventureit.adventureservice.Responses;
import com.adventureit.adventureservice.Entity.Adventure;

import java.util.UUID;

public class CreateAdventureResponse {

    private boolean success;
    private String message;
    private Adventure adventure;


    CreateAdventureResponse(){}

    public Adventure getAdventure() {
        return adventure;
    }


}