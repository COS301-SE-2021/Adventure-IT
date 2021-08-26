package com.adventureit.mediaservice.repository;

import com.adventureit.mediaservice.entity.MediaInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MediaInfoRepository extends JpaRepository<MediaInfo,Long> {
    MediaInfo findMediaById(UUID id);
    List<MediaInfo> findAllByAdventureID(UUID id);
    List<MediaInfo> findAllByOwner(UUID id);
}
