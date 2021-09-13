package com.adventureit.locationservice.repository;

import com.adventureit.locationservice.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LocationRepository extends JpaRepository<Location, UUID> {
    Location findLocationById(UUID id);
    Location findLocationByPlaceID(String placeID);
}