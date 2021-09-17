package com.adventureit.shareddtos.location.responses;

import java.util.UUID;

public class RecommendedLocationResponseDTO extends LocationResponseDTO{
    private Boolean liked;

    public RecommendedLocationResponseDTO(UUID id, String photoReference, String formattedAddress, String placeId, String name, Boolean liked) {
        super(id, photoReference, formattedAddress, placeId, name);
        this.liked = liked;
    }

    public Boolean getLiked() {
        return liked;
    }

    public RecommendedLocationResponseDTO(Boolean liked) {
        this.liked = liked;
    }
}
