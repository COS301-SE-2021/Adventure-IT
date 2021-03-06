package com.adventureit.shareddtos.adventure.responses;

import com.adventureit.shareddtos.adventure.AdventureDTO;

public class GetAdventureByUUIDResponse {
    private boolean success;
    private AdventureDTO adventure;


    public GetAdventureByUUIDResponse(){}
    /**
     * This object will store the response attributes from the GetAdventureByUUID service, currently a mock
     * adventure will be sent back for testing purposes but for future implementation an adventure will be found from the database
     *
     * @param success success attribute to indicate whether the service was successful
     * @param adventure adventure to be returned
     */


    public GetAdventureByUUIDResponse(boolean success, AdventureDTO adventure){
        this.adventure = adventure;
        this.success = success;
    }

    public AdventureDTO getAdventure(){
        return adventure;
    }

    public boolean isSuccess(){
        return success;
    }


    public void setAdventure(AdventureDTO adventure){
        this.adventure = adventure;
    }
}
