package com.adventureit.adventureservice.Responses;

import com.adventureit.adventureservice.Entity.Adventure;
import com.adventureit.adventureservice.Requests.GetAdventureByUUIDRequest;

public class GetAdventureByUUIDResponse {
    private boolean success;
    private Adventure adventure;

    public GetAdventureByUUIDResponse(boolean success, Adventure adventure){
        this.adventure = adventure;
        this.success = success;
    }

    public Adventure getAdventure() {
        return adventure;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setAdventure(Adventure adventure) {
        this.adventure = adventure;
    }
}
