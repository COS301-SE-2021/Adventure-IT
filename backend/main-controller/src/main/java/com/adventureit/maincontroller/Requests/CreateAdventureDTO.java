package com.adventureit.maincontroller.Requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

public class CreateAdventureDTO {
    private String name;
    private String description;
    private UUID ownerId;
    private ArrayList<String> group;
    private String startDate;
    private String endDate;
    private String location;

    public CreateAdventureDTO(@JsonProperty("name")String name, @JsonProperty("description")String description, @JsonProperty("ownerId")UUID ownerId, @JsonProperty("startDate")String sd, @JsonProperty("endDate") String ed, @JsonProperty("location") String location) {
        this.name = name;
        this.description = description;
        this.ownerId = ownerId;
        this.startDate = ed;
        this.endDate = ed;
        this.location = location;
    }

}
