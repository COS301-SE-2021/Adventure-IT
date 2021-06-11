package com.adventureit.adventureservice.Responses;

public class RemoveItineraryResponse {
    private boolean success;

    public RemoveItineraryResponse(boolean suc){
        this.success=suc;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}