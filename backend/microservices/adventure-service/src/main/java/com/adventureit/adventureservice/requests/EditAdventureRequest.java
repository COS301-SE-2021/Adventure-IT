package com.adventureit.adventureservice.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class EditAdventureRequest {
    private String name;
    private String description;
    private UUID ownerId;
    private String startDate;
    private String endDate;
    private String location;


    public EditAdventureRequest(@JsonProperty("name")String name, @JsonProperty("description")String description, @JsonProperty("ownerId")UUID ownerId, @JsonProperty("startDate")String sd, @JsonProperty("endDate") String ed, @JsonProperty("location") String location){
        this.name = name;
        this.description = description;
        this.ownerId = ownerId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
    }

    public String getName() {
        if (this.name == null){
            return "";
        }
        return name;
    }

    public String getDescription() {
        if (this.description == null){
            return "";
        }
        return description;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getLocation() {
        return location;
    }
}
