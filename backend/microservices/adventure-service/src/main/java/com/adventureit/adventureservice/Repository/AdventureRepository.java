package com.adventureit.adventureservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.adventureit.adventureservice.Entity.Adventure;

import java.util.List;
import java.util.UUID;

@Repository
public interface AdventureRepository extends JpaRepository<Adventure,Long>{
    Adventure findById(UUID id);
    Adventure findAdventureByAdventureId(UUID id);
    List<Adventure> findByOwnerId(UUID ownerId);
    List<Adventure> findByAttendees(UUID attendeeId);
    List<Adventure> findAllByOwnerIdOrAttendeesContains(UUID id);
    void deleteAdventureByAdventureId(UUID adventureId);
}