package com.adventureit.adventureservice.Requests;

import com.adventureit.adventureservice.Entity.EntryContainer;
import java.util.ArrayList;
import java.util.UUID;

public class CreateAdventureRequest{

    private String name;
    private UUID id;
    private UUID ownerId;
    private ArrayList<String> group;
    private ArrayList<EntryContainer> Containers;

    /**
     * This service will be used to generate a CreateAdventure request
     * @param name name of the Adventure
     * @param id ID of the Adventure
     */
    public CreateAdventureRequest(String name, UUID id, UUID ownerId){
        this.name=name;
        this.id=id;
        this.ownerId = ownerId;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public UUID getId(){
        return id;
    }

    public void setId(UUID id){
        this.id = id;
    }

    public UUID getOwnerId(){
        return ownerId;
    }

    public void setOwner(UUID ownerId){
        this.ownerId = ownerId;
    }

}
