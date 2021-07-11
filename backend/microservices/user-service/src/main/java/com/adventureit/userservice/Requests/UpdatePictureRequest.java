package com.adventureit.userservice.Requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class UpdatePictureRequest {
    private String path;
    private UUID id;

    public UpdatePictureRequest(){}

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

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
