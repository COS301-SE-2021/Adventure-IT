package com.adventureit.locationservice.Responses;

import java.util.UUID;

public class LocationResponseDTO {
    private UUID id;
    String photoReference;
    String formattedAddress;
    String placeId;

    public LocationResponseDTO(UUID id, String photoReference, String formattedAddress, String placeId){
        this.id =id;
        this.photoReference = photoReference;
        this.formattedAddress = formattedAddress;
        this.placeId = placeId;
    }

    public LocationResponseDTO(){}

    public String getPhotoReference() {
        return photoReference;
    }

    public UUID getId() {
        return id;
    }

    public String getPlaceId() {
        return placeId;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }
}
