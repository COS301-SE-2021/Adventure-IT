package com.adventureit.adventureservice.Responses;

import com.adventureit.adventureservice.Entity.Adventure;

import java.util.List;

public class GetAdventuresByUserUUIDResponse {
    private List<Adventure> adventures;

    public GetAdventuresByUserUUIDResponse(List<Adventure> adventures){
        this.adventures = adventures;
    }

    public List<Adventure> getAdventures(){
        return this.adventures;
    }


}
