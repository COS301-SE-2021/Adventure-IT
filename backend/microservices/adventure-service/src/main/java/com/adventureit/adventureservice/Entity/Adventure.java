package com.adventureit.adventureservice.Entity;


import javax.persistence.*;
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
    @OneToMany
    private List<EntryContainer> Containers;


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
    public Adventure(String name, UUID adventureId, UUID ownerId){
        this.name=name;
        this.adventureId=adventureId;
        this.ownerId = ownerId;
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
    public List<EntryContainer> getContainers(){
        return Containers;
    }

    /**
     * Adventure service to set adventure's Containers
     * @param  containers
     */
    public void setContainers(List<EntryContainer> containers){
        this.Containers = containers;
    }

}
