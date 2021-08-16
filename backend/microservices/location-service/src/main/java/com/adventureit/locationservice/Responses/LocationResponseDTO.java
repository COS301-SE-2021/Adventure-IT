package com.adventureit.locationservice.Responses;

import java.util.UUID;

public class LocationResponseDTO {
    private UUID id;
    String photo_reference;
    String formattedAddress;
    String place_id;

    public LocationResponseDTO(UUID id, String photo_reference, String formattedAddress, String place_id){
        this.id =id;
        this.photo_reference = photo_reference;
        this.formattedAddress = formattedAddress;
        this.place_id = place_id;
    }

    public String getPhoto_reference() {
        return photo_reference;
    }

    public UUID getId() {
        return id;
    }

    public String getPlace_id() {
        return place_id;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setPhoto_reference(String photo_reference) {
        this.photo_reference = photo_reference;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }
}
