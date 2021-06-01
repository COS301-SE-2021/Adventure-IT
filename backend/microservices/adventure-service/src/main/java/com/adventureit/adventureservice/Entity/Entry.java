package com.adventureit.adventureservice.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long entryContainerID;

    // Default constructor
    public Entry(){}

    // Getters and setters



    public long getEntryContainerID() {
        return entryContainerID;
    }


    public void setEntryContainerID(long entryConID){this.entryContainerID=entryConID;}
}
