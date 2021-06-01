package com.adventureit.adventureservice.Responses;
import com.adventureit.adventureservice.Entity.Adventure;

import java.util.UUID;

public class CreateAdventureResponse {

    private boolean success;
    private String message;
    private Adventure adventure;


    public CreateAdventureResponse(){}

    public Adventure getAdventure() {
        return adventure;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public void setAdventure(Adventure adventure) {
        this.adventure = adventure;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}