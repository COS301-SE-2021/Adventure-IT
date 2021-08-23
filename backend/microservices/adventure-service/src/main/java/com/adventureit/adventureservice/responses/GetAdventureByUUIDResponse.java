package com.adventureit.adventureservice.responses;

import com.adventureit.adventureservice.entity.Adventure;

public class GetAdventureByUUIDResponse {
    private final boolean success;
    private Adventure adventure;

    /**
     * This object will store the response attributes from the GetAdventureByUUID service, currently a mock
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


    public void setAdventure(Adventure adventure){
        this.adventure = adventure;
    }
}
