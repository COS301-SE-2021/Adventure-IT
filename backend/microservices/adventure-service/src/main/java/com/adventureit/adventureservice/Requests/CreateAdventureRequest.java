package com.adventureit.adventureservice.Requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class CreateAdventureRequest{

    private RestTemplate restTemplate;

    private String name;
    private String description;

    private UUID ownerId;
    private ArrayList<String> group;
    private LocalDate startDate;
    private LocalDate endDate;
    private UUID location;

    /**
     * This service will be used to generate a CreateAdventure request
     * @param name name of the Adventure
     * @param id ID of the Adventure
     */
    public CreateAdventureRequest(@JsonProperty("name")String name, @JsonProperty("description")String description, @JsonProperty("ownerId")UUID ownerId, @JsonProperty("startDate")String sd,@JsonProperty("endDate") String ed){
        this.name=name;
        this.description = description;
        this.ownerId = ownerId;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy");
        this.startDate= LocalDate.parse(sd,formatter);
        this.endDate=LocalDate.parse(ed,formatter);
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

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public UUID getLocation() {
        return location;
    }

    public void setLocation(UUID location) {
        this.location = location;
    }
}
