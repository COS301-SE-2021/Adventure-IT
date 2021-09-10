package com.adventureit.shareddtos.adventure.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class CreateAdventureRequest{

    private String name;
    private String description;
    private final UUID ownerId;
    private final String startDate;
    private final String endDate;
    private String location;

    /**
     * This service will be used to generate a CreateAdventure request
     * @param name name of the Adventure
     *
     */
    public CreateAdventureRequest(@JsonProperty("name")String name, @JsonProperty("description")String description, @JsonProperty("ownerId")UUID ownerId, @JsonProperty("startDate")String sd,@JsonProperty("endDate") String ed, @JsonProperty("location") String location){
        this.name=name;
        this.description = description;
        this.ownerId = ownerId;
        this.startDate= sd;
        this.endDate=ed;
        this.location = location;
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

    public String getDescription(){
        return this.description;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
