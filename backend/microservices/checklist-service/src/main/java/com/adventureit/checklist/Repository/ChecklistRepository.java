package com.adventureit.checklist.Repository;

import com.adventureit.checklist.Entity.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChecklistRepository extends JpaRepository<Checklist,Long> {
    Checklist findChecklistById(UUID id);
    Checklist findChecklistByIdAndDeleted(UUID id, Boolean deleted);
    List<Checklist> findAllByDeletedEquals(Boolean deleted);
}
