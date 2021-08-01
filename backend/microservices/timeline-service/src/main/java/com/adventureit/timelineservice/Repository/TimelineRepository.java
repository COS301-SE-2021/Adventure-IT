package com.adventureit.timelineservice.Repository;

import com.adventureit.timelineservice.Entity.Timeline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TimelineRepository extends JpaRepository<Timeline, UUID> {

}
