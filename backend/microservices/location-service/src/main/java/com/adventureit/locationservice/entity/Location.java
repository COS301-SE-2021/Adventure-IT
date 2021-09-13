package com.adventureit.locationservice.entity;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class Location {
    @Id
    private UUID id;
    String photoReference;
    String formattedAddress;
    String placeID;
    String country;
    @ElementCollection
    List<String> types;
    private String name;


    public Location(){}

    public Location(UUID id, String photoReference, String formattedAddress, String placeID, String country, List<String> types, String name){
        this.id = id;
        this.photoReference = photoReference;
        this.formattedAddress = formattedAddress;
        this.placeID = placeID;
        this.country = country;
        this.types = types;
        this.name=name;
    }

    public Location(String photoReference, String formattedAddress, String placeID, String country, List<String> types, String name){
        this.id = UUID.randomUUID();
        this.photoReference = photoReference;
        this.formattedAddress = formattedAddress;
        this.placeID = placeID;
        this.country = country;
        this.types = types;
        this.name=name;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
