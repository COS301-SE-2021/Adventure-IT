package com.adventureit.adventureservice.Repository;

import com.adventureit.adventureservice.Entity.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChecklistRepository extends JpaRepository<Checklist,Long> {
    Checklist findByid(Long id);
    List<Checklist> findByAdventureID(Long adventureID);
    List<Checklist> findAll();
}
