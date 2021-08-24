package com.adventureit.checklist.repository;

import com.adventureit.checklist.entity.ChecklistEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChecklistEntryRepository extends JpaRepository<ChecklistEntry,Long> {
    ChecklistEntry findChecklistEntryById(UUID id);
    List<ChecklistEntry> findAllByEntryContainerID(UUID checklistID);
}