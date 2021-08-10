package com.adventureit.timelineservice.Repository;

import com.adventureit.timelineservice.Entity.Timeline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TimelineRepository extends JpaRepository<Timeline, UUID> {
    List<Timeline> findAllByAdventureID(UUID adventureID);
    Timeline findByTimelineID(UUID timelineID);
    void removeAllByAdventureID(UUID adventureID);
}
