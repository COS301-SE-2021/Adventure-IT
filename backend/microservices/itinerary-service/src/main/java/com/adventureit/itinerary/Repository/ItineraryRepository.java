package com.adventureit.itinerary.Repository;

import com.adventureit.itinerary.Entity.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ItineraryRepository extends JpaRepository<Itinerary,UUID> {
    Itinerary findItineraryById(UUID id);
    Itinerary getItineraryById(UUID itineraryContainerId);
    Itinerary findItineraryByIdAndDeleted(UUID id, Boolean deleted);
    List<Itinerary> findAllByAdventureID(UUID id);
}