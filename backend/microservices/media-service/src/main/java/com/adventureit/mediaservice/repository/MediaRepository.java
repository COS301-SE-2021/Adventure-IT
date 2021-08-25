package com.adventureit.mediaservice.repository;

import com.adventureit.mediaservice.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MediaRepository extends JpaRepository<Media,Long> {
    Media findMediaById(UUID id);
}
