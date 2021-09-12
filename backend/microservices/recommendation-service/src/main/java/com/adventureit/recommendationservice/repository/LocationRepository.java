package com.adventureit.recommendationservice.repository;

import com.adventureit.recommendationservice.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LocationRepository extends JpaRepository<Location, UUID> {
    Location findLocationByLocationId(UUID id);
}
