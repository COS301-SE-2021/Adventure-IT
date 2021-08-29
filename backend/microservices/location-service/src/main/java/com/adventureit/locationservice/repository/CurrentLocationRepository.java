package com.adventureit.locationservice.repository;

import com.adventureit.locationservice.entity.CurrentLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CurrentLocationRepository extends JpaRepository<CurrentLocation, UUID> {
    CurrentLocation findCurrentLocationById(UUID id);
    CurrentLocation findCurrentLocationByUserID(UUID id);
}
