package com.adventureit.locationservice.Entity;

import com.fasterxml.jackson.databind.util.JSONPObject;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Location {
    @Id
    private UUID id;
    @Lob
    String location;

    public Location(){}

    public Location(UUID id, String location){
        this.id = id;
        this.location = location;
    }


}
