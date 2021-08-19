package com.adventureit.mediaservice.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name="documentinfo",schema = "public")
public class DocumentInfo {
    @Id
    private UUID id;
    private String type;
    private String name;
    private String description;
    private UUID owner;

    public DocumentInfo(UUID id, String type, String name, String description, UUID owner) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.description = description;
        this.owner = owner;
    }

    public DocumentInfo(String type, String name, String description, UUID owner) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.owner = owner;
    }

    public DocumentInfo() {

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
