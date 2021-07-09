package com.adventureit.adventureservice.Repository;

import com.adventureit.adventureservice.Entity.EntryContainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EntryContainerRepository extends JpaRepository<EntryContainer,Long> {
    EntryContainer findEntryContainerById(UUID id);
}
