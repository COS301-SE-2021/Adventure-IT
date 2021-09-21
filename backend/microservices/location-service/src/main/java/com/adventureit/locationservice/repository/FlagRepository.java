package com.adventureit.locationservice.repository;

import com.adventureit.locationservice.entity.Flags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FlagRepository extends JpaRepository<Flags, UUID> {
    Flags getFlagsByUserID(UUID id);
}