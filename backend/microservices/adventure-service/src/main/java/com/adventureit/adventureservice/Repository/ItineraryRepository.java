package main.java.com.adventureit.adventureservice.Repository;

import com.adventureit.adventureservice.Entity.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItineraryRepository extends JpaRepository<Itinerary,Long> {
    Itinerary findByID(Long id);
    List<Itinerary> findByAdventureID(Long adventureID);
    List<Itinerary> findAll();
}
