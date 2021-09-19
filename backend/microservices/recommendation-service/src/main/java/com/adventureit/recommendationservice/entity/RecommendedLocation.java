package com.adventureit.recommendationservice.entity;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;
import java.util.UUID;

@Entity
public class RecommendedLocation {
    @Id
    private final UUID locationId;
    @ElementCollection
    private List<String> types;
    private long likes=0;
    private long visits=0;
    private String locationString = "default";

    public RecommendedLocation(){
        this.locationId = UUID.randomUUID();
    }

    public RecommendedLocation(UUID id, String locationString){
        this.locationId = id;
        this.locationString = locationString;
    }

    public void like(){
        this.likes++;
    }

    public void visit(){
        this.visits++;
    }

    public long getLikes(){
        return this.likes;
    }


    public long getVisits(){
        return this.visits;
    }

    public UUID getLocationId(){
        return this.locationId;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public String getLocationString() {
        return locationString;
    }

    public void setLocationString(String locationString) {
        this.locationString = locationString;
    }
}
