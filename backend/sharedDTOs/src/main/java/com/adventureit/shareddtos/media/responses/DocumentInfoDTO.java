package com.adventureit.shareddtos.media.responses;


import java.util.UUID;


public class DocumentInfoDTO {

    private UUID id;
    private String type;
    private String name;
    private String description;
    private UUID owner;

    public DocumentInfoDTO(UUID id, String type, String name, String description, UUID owner) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.description = description;
        this.owner = owner;
    }

    public DocumentInfoDTO(String type, String name, String description, UUID owner) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.owner = owner;
    }

    public DocumentInfoDTO() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }
}
