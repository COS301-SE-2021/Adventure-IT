package com.adventureit.adventureservice.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class EditAdventureRequest {
    private UUID adventureId;
    private String name;
    private String description;
    private String startDate;
    private String endDate;



    public EditAdventureRequest(@JsonProperty("adventureId")UUID adventureId,@JsonProperty("name")String name, @JsonProperty("description")String description, @JsonProperty("startDate")String sd, @JsonProperty("endDate") String ed){
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.adventureId = adventureId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public UUID getAdventureId() {
        return adventureId;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

}
