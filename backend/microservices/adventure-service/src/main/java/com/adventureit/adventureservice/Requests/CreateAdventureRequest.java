package com.adventureit.adventureservice.Requests;

import com.adventureit.adventureservice.Entity.EntryContainer;
import com.adventureit.adventureservice.Entity.User;

import java.util.ArrayList;
import java.util.UUID;

public class CreateAdventureRequest {

    private String name;
    private UUID id;
    private User owner;
    private ArrayList<String> group;
    private ArrayList<EntryContainer> itinerary;
    private ArrayList<EntryContainer> checklists;



    public CreateAdventureRequest(String name, UUID id, User owner , ArrayList<String> group ,ArrayList<EntryContainer> itinerary, ArrayList<EntryContainer> checklists){
        this.name=name;
        this.id=id;
        this.owner = owner;
        this.group = group;
        this.itinerary = itinerary;
        this.checklists = checklists;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public ArrayList<String> getGroup() {
        return group;
    }

    public void setGroup(ArrayList<String> group) {
        this.group = group;
    }

    public ArrayList<EntryContainer> getChecklists() {
        return checklists;
    }

    public void setChecklists(ArrayList<EntryContainer> checklists) {
        this.checklists = checklists;
    }

    public ArrayList<EntryContainer> getItinerary() {
        return itinerary;
    }

    public void setItinerary(ArrayList<EntryContainer> itinerary) {
        this.itinerary = itinerary;
    }
}