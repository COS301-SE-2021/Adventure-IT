package com.adventureit.locationservice.Entity;

import com.fasterxml.jackson.databind.util.JSONPObject;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Location {
    @Id
    private UUID id;
    String photo_reference;
    String formattedAddress;
    String place_id;


    public Location(){}

    public Location(UUID id, String photo_reference, String formattedAddress, String place_id){
        this.id = id;
        this.photo_reference = photo_reference;
        this.formattedAddress = formattedAddress;
        this.place_id = place_id;
    }

    public Location(String photo_reference, String formattedAddress, String place_id){
        this.id = UUID.randomUUID();
        this.photo_reference = photo_reference;
        this.formattedAddress = formattedAddress;
        this.place_id = place_id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPhoto_reference() {
        return photo_reference;
    }

    public void setPhoto_reference(String photo_reference) {
        this.photo_reference = photo_reference;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }
}
