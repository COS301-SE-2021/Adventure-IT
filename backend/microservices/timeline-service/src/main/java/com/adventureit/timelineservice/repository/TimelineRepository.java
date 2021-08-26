package com.adventureit.timelineservice.repository;

import com.adventureit.timelineservice.entity.Timeline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TimelineRepository extends JpaRepository<Timeline, UUID> {
    List<Timeline> findAllByAdventureId(UUID adventureID);
    void removeAllByAdventureId(UUID adventureID);
}
