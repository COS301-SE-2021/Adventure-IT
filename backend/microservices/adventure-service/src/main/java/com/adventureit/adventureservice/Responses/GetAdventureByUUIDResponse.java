package com.adventureit.adventureservice.Responses;

import com.adventureit.adventureservice.Entity.Adventure;

public class GetAdventureByUUIDResponse {
    private boolean success;
    private Adventure adventure;

    /**
     * This ojcect will store the response attributes from the GetAdventureByUUID service, currently a mock
     * adventure will be sent back for testing purposes but for future implementation an adventure will be found from the database
     *
     * @param success success attribute to indicate whether the service was successful
     * @param adventure adventure to be returned
     */

    public GetAdventureByUUIDResponse(boolean success, Adventure adventure){
        this.adventure = adventure;
        this.success = success;
    }

    public Adventure getAdventure(){
        return adventure;
    }

    public boolean isSuccess(){
        return success;
    }

    public void setSuccess(boolean success){
        this.success = success;
    }

    public void setAdventure(Adventure adventure){
        this.adventure = adventure;
    }
}
