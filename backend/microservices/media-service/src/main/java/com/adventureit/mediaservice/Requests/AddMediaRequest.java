package com.adventureit.mediaservice.Requests;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public class AddMediaRequest {
    private UUID id;
    String type;
    String name;
    String description;
    UUID adventureID;
    UUID owner;
    String path;

    public AddMediaRequest(){}

    public AddMediaRequest(UUID id, String type, String name, String description, UUID adventureID, UUID owner, String path){
        this.id = id;
        this.name = name;
        this.description = description;
        this.adventureID = adventureID;
        this.owner = owner;
        this.path = path;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAdventureID(UUID adventureID) {
        this.adventureID = adventureID;
    }

    public UUID getAdventureID() {
        return adventureID;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
