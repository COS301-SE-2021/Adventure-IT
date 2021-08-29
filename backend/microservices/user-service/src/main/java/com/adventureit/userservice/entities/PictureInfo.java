package com.adventureit.userservice.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class PictureInfo {
    @Id
    private UUID id;
    private String type;
    private String name;
    private UUID owner;

    public PictureInfo(UUID id, String type, String name, UUID owner) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.owner = owner;
    }

    public PictureInfo() {

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

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }
}
