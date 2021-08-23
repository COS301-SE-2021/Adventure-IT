package com.adventureit.adventureservice.responses;

public class RemoveAdventureResponse {
    private final boolean success;
    private final String message;

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
