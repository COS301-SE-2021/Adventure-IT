package com.adventureit.recommendationservice.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class RecommendedUser {
    @Id
    private final UUID userId;
    @ManyToMany
    private List<RecommendedLocation> likedLocations;
    @ManyToMany
    private List<RecommendedLocation> visitedLocations;

    public RecommendedUser(){
        this.userId = UUID.randomUUID();
        this.likedLocations = new ArrayList<>();
        this.visitedLocations = new ArrayList<>();
    }

    public RecommendedUser(UUID id){
        this.userId = id;
    }

    public void likeLocation(RecommendedLocation location){
        System.out.println("Is being added");
    this.likedLocations.add(location);
    }

    public void visitLocation(RecommendedLocation location){
        this.visitedLocations.add(location);
    }

    public Boolean hasLiked(RecommendedLocation location){
        return this.likedLocations.contains(location);
    }

    public Boolean hasVisited(RecommendedLocation location){
        return this.visitedLocations.contains(location);
    }

    public UUID getUserId(){
        return this.userId;
    }
}
