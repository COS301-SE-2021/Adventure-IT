package com.adventureit.userservice.repository;

import com.adventureit.userservice.entities.PictureInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PictureInfoRepository extends JpaRepository<PictureInfo, UUID> {
    PictureInfo findPictureInfoById(UUID id);
    PictureInfo findPictureInfoByOwner(UUID id);
}
