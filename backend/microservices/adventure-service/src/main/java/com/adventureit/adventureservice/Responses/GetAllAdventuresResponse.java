package com.adventureit.adventureservice.Responses;

import com.adventureit.adventureservice.Entity.Adventure;

import java.util.List;

public class GetAllAdventuresResponse {
    private List<Adventure> adventures;

    public GetAllAdventuresResponse(List<Adventure> adventures){
        this.adventures = adventures;
    }

    public List<Adventure> getAdventures(){
        return this.adventures;
    }
}
