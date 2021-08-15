package com.adventureit.adventureservice.Entity;


import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
     * @param name
     * @param adventureId
     * @param ownerId
     */
    public Adventure(String name, String description, UUID adventureId, UUID ownerId, LocalDate sd, LocalDate ed, UUID location){
        this.name=name;
        this.description = description;
        this.adventureId=adventureId;
        this.ownerId = ownerId;
        this.attendees = new ArrayList<UUID>(List.of(ownerId));
        this.startDate=sd;
        this.endDate=ed;
        this.location = location;
    }


    /**
     * Adventure service to retrieve adventure's name
     * @return name
     */
    public String getName(){
        return name;
    }

    /**
     * Adventure service to set adventure's name
     * @param  name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Adventure service to retrieve adventure's id
     * @return id
     */
    public UUID getAdventureId(){
        return adventureId;
    }

    /**
     * Adventure service to retrieve adventure's owner
     * @return owner
     */
    public UUID getOwnerId(){
        return ownerId;
    }

    /**
     * Adventure service to set adventure's owner
     * @param  ownerId
     */
    public void setOwnerId(UUID ownerId){
        this.ownerId = ownerId;
    }

    /**
     * Adventure service to retrieve adventure's Containers
     * @return Containers
     */


    public void addAttendee(UUID attendeeID){
        this.attendees.add(attendeeID);
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

    public void setId(long id) {
        this.id = id;
    }

    public void setAdventureId(UUID adventureId) {
        this.adventureId = adventureId;
    }

    public void setLocation(UUID location) {
        this.location = location;
    }

    public void setAttendees(List<UUID> attendees) {
        this.attendees = attendees;
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
