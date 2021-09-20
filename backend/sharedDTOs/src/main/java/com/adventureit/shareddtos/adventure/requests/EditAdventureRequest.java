package com.adventureit.shareddtos.adventure.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class EditAdventureRequest {
    private UUID adventureId;
    private UUID userId;
    private String name;
    private String description;
    private String startDate;
    private String endDate;



    public EditAdventureRequest(@JsonProperty("adventureId")UUID adventureId,@JsonProperty("userId")UUID userId,@JsonProperty("name")String name, @JsonProperty("description")String description, @JsonProperty("startDate")String startDate, @JsonProperty("endDate") String endDate){
        this.name = name;
        this.userId = userId;
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

    public UUID getUserId() {
        return userId;
    }

    public boolean test()
    {
        if(name.equals("") || description.equals("") ||userId==null||adventureId==null|| startDate.equals("") || endDate.equals(""))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
