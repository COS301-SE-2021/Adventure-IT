package com.adventureit.adventureservice.Responses;

import java.util.UUID;

public class CreateItineraryResponse {
    private long ItineraryID;
    private boolean success;

    public CreateItineraryResponse(long id, boolean suc){
        this.ItineraryID=id;
        this.success=suc;
    }

    public long getItineraryID() {
        return ItineraryID;
    }

    public void setItineraryID(long itineraryID) {
        ItineraryID = itineraryID;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}