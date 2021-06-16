package com.adventureit.adventureservice.Entity;

import com.adventureit.userservice.Entities.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "\"adventure\"")
public class Adventure{

    private String name;
    private UUID id;
    private User owner;
    private ArrayList<String> group;
    private ArrayList<EntryContainer> Containers;

    /**
     * Default constructor for User Model
     */
    public Adventure(){}

    /**
     * Adventure model Constructor which takes in the following parameters:
     * @param name
     * @param id
     * @param owner
     * @param group
     * @param containers
     */
    public Adventure(String name, UUID id, User owner , ArrayList<String> group ,ArrayList<EntryContainer> containers){
        this.name=name;
        this.id=id;
        this.owner = owner;
        this.group = group;
        this.Containers = containers;
    }

    /**
     * Adventure service to retrieve adventure's name
     * @return name
     */
    @Basic
    @Column(name = "name")
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
    @Id
    @Column(name = "id")
    public UUID getId(){
        return id;
    }

    /**
     * Adventure service to set adventure's id
     * @param  id
     */
    public void setId(UUID id){
        this.id = id;
    }

    /**
     * Adventure service to retrieve adventure's owner
     * @return owner
     */
    public User getOwner(){
        return owner;
    }

    /**
     * Adventure service to set adventure's owner
     * @param  owner
     */
    public void setOwner(User owner){
        this.owner = owner;
    }

    /**
     * Adventure service to retrieve adventure's group
     * @return group
     */
    public ArrayList<String> getGroup(){
        return group;
    }

    /**
     * Adventure service to set adventure's group
     * @param  group
     */
    public void setGroup(ArrayList<String> group){
        this.group = group;
    }

    /**
     * Adventure service to retrieve adventure's Containers
     * @return Containers
     */
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Column
    public ArrayList<EntryContainer> getContainers(){
        return Containers;
    }

    /**
     * Adventure service to set adventure's Containers
     * @param  containers
     */
    public void setContainers(ArrayList<EntryContainer> containers){
        this.Containers = containers;
    }

}
