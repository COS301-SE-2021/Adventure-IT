package com.adventureit.shareddtos.location.responses;

import java.util.List;

public class LocationsResponseDTO {
    List<LocationResponseDTO> locations;

    public LocationsResponseDTO(List<LocationResponseDTO> l){
        this.locations = l;
    }
}
