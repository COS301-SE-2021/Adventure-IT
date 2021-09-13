package com.adventureit.shareddtos.adventure.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class EditAdventureRequest {
    private UUID adventureId;
    private String name;
    private String description;
    private String startDate;
    private String endDate;
    private UUID userId;



    public EditAdventureRequest(@JsonProperty("adventureId")UUID adventureId,@JsonProperty("userId")UUID userId,@JsonProperty("name")String name, @JsonProperty("description")String description, @JsonProperty("startDate")String startDate, @JsonProperty("endDate") String endDate){
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.adventureId = adventureId;
        this.userId=userId;
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

    public UUID getUserId(){return userId;}

}
