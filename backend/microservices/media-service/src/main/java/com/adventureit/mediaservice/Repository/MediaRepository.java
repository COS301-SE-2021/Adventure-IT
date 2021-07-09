package com.adventureit.mediaservice.Repository;

import com.adventureit.mediaservice.Entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MediaRepository extends JpaRepository<Media,Long> {
    Media findMediaById(UUID id);
}
