package com.adventureit.adventureservice.Requests;

import java.util.UUID;

/**
 *
 * The params taken in by constructor and what they represent:
 * 1. String title: the title of the itinerary
 * 2. String description: a small description of the itinerary
 * 3. UUID adventureID: the ID of the adventure that the itinerary will be attached to if it's added successfully
 * 4. UUID userID: the ID of the user that wishes to add the itinerary to the adventure
 *
 */

public class CreateItineraryRequest {
    private String title;
    private String description;
    private UUID adventureID;
    private UUID userID;


    public CreateItineraryRequest(String title, String description, UUID adventureID, UUID userID){
        this.title=title;
        this.description=description;
        this.adventureID=adventureID;
        this.userID=userID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getAdventureID() {
        return adventureID;
    }

    public void setAdventureID(UUID adventureID) {
        this.adventureID = adventureID;
    }

    public UUID getUserID() {
        return userID;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
    }
}