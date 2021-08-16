package com.adventureit.mediaservice.Requests;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public class AddFileRequest {
    String type;
    String name;
    String description;
    UUID adventureID;
    UUID owner;
    MultipartFile file;

    public AddFileRequest(String type, String name, String description, UUID adventureID, UUID owner, MultipartFile file) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.adventureID = adventureID;
        this.owner = owner;
        this.file = file;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
