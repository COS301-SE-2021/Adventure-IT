package com.adventureit.shareddtos.user.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class UpdatePictureRequest {
    private final String path;
    private UUID id;


    public UpdatePictureRequest(@JsonProperty("path") String path,@JsonProperty("id") String id){
        this.path = path;
        this.id = UUID.fromString(id);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }
}
