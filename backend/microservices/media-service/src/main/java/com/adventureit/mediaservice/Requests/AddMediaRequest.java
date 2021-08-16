package com.adventureit.mediaservice.Requests;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public class AddMediaRequest {
    String type;
    String name;
    String description;
    UUID adventureID;
    UUID owner;
    MultipartFile file;

    public AddMediaRequest(){}

    public AddMediaRequest(String type, String name, String description, UUID adventureID, UUID owner, MultipartFile file){
        this.name = name;
        this.description = description;
        this.adventureID = adventureID;
        this.owner = owner;
        this.file = file;
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

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
