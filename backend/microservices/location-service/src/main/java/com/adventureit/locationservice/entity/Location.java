package com.adventureit.locationservice.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Location {
    @Id
    private UUID id;
    String photoReference;
    String formattedAddress;
    String placeID;


    public Location(){}

    public Location(UUID id, String photoReference, String formattedAddress, String placeID){
        this.id = id;
        this.photoReference = photoReference;
        this.formattedAddress = formattedAddress;
        this.placeID = placeID;
    }

    public Location(String photoReference, String formattedAddress, String placeID){
        this.id = UUID.randomUUID();
        this.photoReference = photoReference;
        this.formattedAddress = formattedAddress;
        this.placeID = placeID;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPhotoReference() {
        return photoReference;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public String getPlaceID() {
        return placeID;
    }

    public void setPlaceID(String placeID) {
        this.placeID = placeID;
    }
}
