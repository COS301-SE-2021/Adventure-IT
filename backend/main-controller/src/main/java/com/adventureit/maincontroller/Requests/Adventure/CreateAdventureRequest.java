package com.adventureit.maincontroller.Requests.Adventure;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class CreateAdventureRequest {

    private String name;
    private String description;
    private UUID id;
    private UUID ownerId;
    private ArrayList<String> group;
    private LocalDate startDate;
    private LocalDate endDate;

    /**
     * This service will be used to generate a CreateAdventure request
     * @param name name of the Adventure
     */
    public CreateAdventureRequest(@JsonProperty("name")String name,@JsonProperty("description") String description,@JsonProperty("ownerId") UUID ownerId,@JsonProperty("startDate") LocalDate sd,@JsonProperty("endDate") LocalDate ed){
        this.name=name;
        this.description = description;
        this.ownerId = ownerId;
        this.startDate=sd;
        this.endDate=ed;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }


    public UUID getOwnerId(){
        return ownerId;
    }

    public void setOwner(UUID ownerId){
        this.ownerId = ownerId;
    }

    public String getDescription(){
        return this.description;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public String getEndDate() {
        return this.endDate.toString();
    }
}