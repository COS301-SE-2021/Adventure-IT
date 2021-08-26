package com.adventureit.mediaservice.repository;

import com.adventureit.mediaservice.entity.DocumentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DocumentInfoRepository extends JpaRepository<DocumentInfo,Long> {
    DocumentInfo findDocumentInfoById(UUID id);
    List<DocumentInfo> findAllByOwner(UUID id);
}
