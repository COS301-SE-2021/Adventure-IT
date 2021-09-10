package com.adventureit.shareddtos.adventure.responses;
import com.adventureit.shareddtos.adventure.AdventureDTO;

public class CreateAdventureResponse {

    private boolean success;
    private String message;
    private AdventureDTO adventure;

    /**
     * This object will store the response attributes from the GetAdventureByUUID service, currently a mock
     * adventure will be sent back for testing purposes but for future implementation an adventure will be found from the database
     */

    public CreateAdventureResponse(){

    }

    public CreateAdventureResponse(boolean success, String message, AdventureDTO adventure) {
        this.success = success;
        this.message = message;
        this.adventure = adventure;
    }

    public CreateAdventureResponse(boolean success, AdventureDTO adventure) {
        this.success = success;
        this.message = "Adventure was successfully created";
        this.adventure = adventure;
    }

    public AdventureDTO getAdventure(){
        return this.adventure;
    }

    public String getMessage(){
        return this.message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public void setAdventure(AdventureDTO adventure){
        this.adventure = adventure;
    }

    public boolean isSuccess(){
        return this.success;
    }
}