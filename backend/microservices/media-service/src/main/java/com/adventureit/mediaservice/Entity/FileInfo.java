package com.adventureit.mediaservice.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name="fileinfo",schema = "public")
public class FileInfo {
    @Id
    private UUID id;
    private String type;
    private String name;
    private String description;
    private UUID adventureID;
    private UUID owner;

    public FileInfo(){}

    public FileInfo(UUID id, String type, String name, String description, UUID adventureID, UUID owner) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.description = description;
        this.adventureID = adventureID;
        this.owner = owner;
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
}
