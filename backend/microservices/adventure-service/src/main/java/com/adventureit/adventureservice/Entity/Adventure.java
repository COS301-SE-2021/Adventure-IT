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
    @ElementCollection
    private List<UUID> attendees;
    @ElementCollection
    private List<UUID> Containers;
    private LocalDate date;
    private String description;


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
    public Adventure(String name, String description, UUID adventureId, UUID ownerId, LocalDate date){
        this.name=name;
        this.description = description;
        this.adventureId=adventureId;
        this.ownerId = ownerId;
        this.attendees = new ArrayList<UUID>();
        this.Containers = new ArrayList<UUID>();
        this.date = date;
    }

    public Adventure(String s, String s1, UUID randomUUID, UUID mockOwnerID, LocalDate of, LocalDate of1) {
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
    public List<UUID> getContainers(){
        return Containers;
    }

    /**
     * Adventure service to set adventure's Containers
     * @param  containers
     */
    public void setContainers(List<UUID> containers){
        this.Containers = containers;
    }

    public void addContainer(UUID container){
        this.Containers.add(container);
    }

    public void addAttendee(UUID attendeeID){
        this.attendees.add(attendeeID);
    }

    public List<UUID> getAttendees(){
        return this.attendees;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }
}
