package com.adventureit.recommendationservice.repository;

import com.adventureit.recommendationservice.entity.RecommendedLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RecommendedLocationRepository extends JpaRepository<RecommendedLocation, UUID> {
    RecommendedLocation findLocationByLocationId(UUID id);
}
