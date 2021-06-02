package com.adventureit.adventureservice.Responses;
import com.adventureit.adventureservice.Entity.Adventure;

import java.util.UUID;

public class CreateAdventureResponse {

    private boolean success;
    private String message;
    private Adventure adventure;


    public CreateAdventureResponse(){}

    public CreateAdventureResponse(boolean success){
        this.success = success;
        this.message = "Adventure was not successfully created";
    }

    public CreateAdventureResponse(boolean success, Adventure adventure){
        this.success = true;
        this.message = "Adventure was successfully created";

    }

    public Adventure getAdventure() {
        return this.adventure;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setAdventure(Adventure adventure) {
        this.adventure = adventure;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return this.success;
    }
}