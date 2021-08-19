package com.adventureit.mediaservice.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name="document",schema = "public")
public class Document {
    @Id
    private UUID id;
    private String type;
    private String name;
    private String description;
    private UUID owner;
    @Lob
    private byte[] data;

    public Document(UUID id, String type, String name, String description, UUID owner) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.description = description;
        this.owner = owner;
    }

    public Document(String type, String name, String description, UUID owner, byte[] data) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.data = data;
    }

    public Document() {
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

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
