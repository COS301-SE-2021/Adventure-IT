package com.adventureit.adventureservice.Requests;

import com.adventureit.adventureservice.Entity.EntryContainer;
import com.adventureit.userservice.Entities.User;
import java.util.ArrayList;
import java.util.UUID;

public class CreateAdventureRequest{

    private String name;
    private UUID id;
    private User owner;
    private ArrayList<String> group;
    private ArrayList<EntryContainer> Containers;

    /**
     * This service will be used to generate a CreateAdventure request
     * @param name name of the Adventure
     * @param id ID of the Adventure
     * @param owner owner of the Adventure
     * @param group groups of the Adventure
     * @param Containers Containers of the Adventure
     */
    public CreateAdventureRequest(String name, UUID id, User owner , ArrayList<String> group ,ArrayList<EntryContainer> containers){
        this.name=name;
        this.id=id;
        this.owner = owner;
        this.group = group;
        this.Containers = containers;
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

    public User getOwner(){
        return owner;
    }

    public void setOwner(User owner){
        this.owner = owner;
    }

    public ArrayList<String> getGroup(){
        return group;
    }

    public void setGroup(ArrayList<String> group){
        this.group = group;
    }

    public ArrayList<EntryContainer> getContainers(){
        return Containers;
    }

    public void setContainers(ArrayList<EntryContainer> containers){
        this.Containers = containers;
    }

}
