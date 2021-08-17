package com.adventureit.mediaservice.Entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="media",schema = "public")
public class Media {
    @Id
    private UUID id;
    private String type;
    private String name;
    private String description;
    private UUID adventureID;
    private UUID owner;
    private boolean publicAccess = true;
    @Lob
    private byte[] data;

    public Media(){}

    public Media(UUID id, String type, String name, String description, UUID adventureID, UUID owner){
        this.id = id;
        this.name = name;
        this.description = description;
        this.adventureID = adventureID;
        this.owner = owner;
        this.type = type;
    }

    public Media(String type, String name, String description, UUID adventureID, UUID owner){
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.adventureID = adventureID;
        this.owner = owner;
        this.type = type;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setAdventureID(UUID adventureID) {
        this.adventureID = adventureID;
    }

    public UUID getAdventureID() {
        return adventureID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setPublicAccess(boolean publicAccess) {
        this.publicAccess = publicAccess;
    }

    public boolean getPublicAccess() {
        return publicAccess;
    }
}
