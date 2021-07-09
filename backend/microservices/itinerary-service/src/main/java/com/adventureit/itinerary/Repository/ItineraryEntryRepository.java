package com.adventureit.itinerary.Repository;

import com.adventureit.itinerary.Entity.ItineraryEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ItineraryEntryRepository extends JpaRepository<ItineraryEntry,Long> {
    ItineraryEntry findItineraryEntryById(UUID id);
}
