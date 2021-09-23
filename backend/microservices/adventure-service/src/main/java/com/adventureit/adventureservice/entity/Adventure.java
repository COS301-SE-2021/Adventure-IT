package com.adventureit.adventureservice.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Adventure class
 * This class defines the attributes of the Adventure entity to set up the table in the database
 */

@Entity
public class Adventure{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private UUID adventureId;
    private UUID ownerId;
    @ElementCollection (fetch = FetchType.EAGER)
    private List<UUID> attendees;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private UUID location;


    /**
     * Default constructor
     */
    public Adventure(){}

    /**
     * Adventure model Constructor which takes in the following parameters:
     * @param name - The name of the adventure
     * @param description - The description of the adventure
     * @param adventureId - The UUID of the adventure
     * @param ownerId - The UUID of the creator of the adventure
     * @param sd - The start date of the adventure
     * @param ed - The end date of the adventure
     * @param location - The UUID of the location of the adventure
     */
    public Adventure(String name, String description, UUID adventureId, UUID ownerId, LocalDate sd, LocalDate ed, UUID location){
        this.name=name;
        this.description = description;
        this.adventureId=adventureId;
        this.ownerId = ownerId;
        this.attendees = new ArrayList<>(List.of(ownerId));
        this.startDate=sd;
        this.endDate=ed;
        this.location = location;
    }

    /**
     * Getters and Setters
     */
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public UUID getAdventureId(){
        return adventureId;
    }

    public UUID getOwnerId(){
        return ownerId;
    }

    public List<UUID> getAttendees(){
        return this.attendees;
    }

    public LocalDate getStartDate(){return this.startDate;}

    public LocalDate getEndDate(){return this.endDate;}

    public String getDescription() {
        return description;
    }

    public long getId() {
        return id;
    }

    public void setLocation(UUID location) {
        this.location = location;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getLocation() {
        return location;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

}
