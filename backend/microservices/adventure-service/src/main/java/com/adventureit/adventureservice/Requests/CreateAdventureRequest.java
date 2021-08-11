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
    private String startDate;
    private String endDate;
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

    public void setOwner(UUID ownerId){
        this.ownerId = ownerId;
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

//    //public void setEndDate(LocalDate endDate) {
//        this.endDate = endDate;
//    }

//    public void setStartDate(LocalDate startDate) {
//        this.startDate = startDate;
//    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public void setGroup(ArrayList<String> group) {
        this.group = group;
    }
}
