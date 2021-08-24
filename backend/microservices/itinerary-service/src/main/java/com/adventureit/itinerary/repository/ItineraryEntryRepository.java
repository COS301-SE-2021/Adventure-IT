package com.adventureit.itinerary.repository;

import com.adventureit.itinerary.entity.ItineraryEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ItineraryEntryRepository extends JpaRepository<ItineraryEntry,Long> {
    ItineraryEntry findItineraryEntryById(UUID id);
    List<ItineraryEntry> findAllByEntryContainerID(UUID itineraryID);
    ItineraryEntry findItineraryEntryByIdAndEntryContainerID(UUID entryId, UUID itineraryID);
}
