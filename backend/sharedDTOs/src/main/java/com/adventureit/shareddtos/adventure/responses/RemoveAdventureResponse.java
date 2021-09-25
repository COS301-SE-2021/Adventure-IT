package com.adventureit.shareddtos.adventure.responses;

public class RemoveAdventureResponse {
    private boolean success;
    private String message;
    private boolean deleteAdventure;

    public RemoveAdventureResponse() {
    }

    public RemoveAdventureResponse(boolean success, String message, boolean deleteAdventure){
        this.success = success;
        this.message = message;
        this.deleteAdventure = deleteAdventure;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isDeleteAdventure() {
        return deleteAdventure;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDeleteAdventure(boolean deleteAdventure) {
        this.deleteAdventure = deleteAdventure;
    }
}
