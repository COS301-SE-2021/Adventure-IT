package com.adventureit.adventureservice.Responses;

import java.util.UUID;

public class CreateItineraryResponse {
    private long ItineraryID;

    public CreateItineraryResponse(long id){
        this.ItineraryID=id;
    }

    public long getItineraryID() {
        return ItineraryID;
    }

    public void setItineraryID(long itineraryID) {
        ItineraryID = itineraryID;
    }
}