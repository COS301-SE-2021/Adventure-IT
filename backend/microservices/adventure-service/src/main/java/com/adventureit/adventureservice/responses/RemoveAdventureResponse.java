package com.adventureit.adventureservice.responses;

public class RemoveAdventureResponse {
    private boolean success;
    private String message;

    public RemoveAdventureResponse(boolean success, String message){
        this.success = success;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }
}
