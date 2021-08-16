package com.adventureit.mediaservice.Repository;

import com.adventureit.mediaservice.Entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MediaRepository extends JpaRepository<Media,Long> {
    Media findMediaById(UUID id);
    List<Media> findAllByAdventureID(UUID id);
    List<Media> findAllByOwner(UUID id);
    List<Media> findAllByType(String type);
}
