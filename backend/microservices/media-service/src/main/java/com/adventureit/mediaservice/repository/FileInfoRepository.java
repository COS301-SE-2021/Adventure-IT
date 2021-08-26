package com.adventureit.mediaservice.repository;

import com.adventureit.mediaservice.entity.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FileInfoRepository extends JpaRepository<FileInfo,Long> {
    FileInfo findFileInfoById(UUID id);
    List<FileInfo> findAllByAdventureID(UUID id);
    List<FileInfo> findAllByOwner(UUID id);
}
