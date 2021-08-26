package com.adventureit.mediaservice.repository;

import com.adventureit.mediaservice.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DocumentRepository extends JpaRepository<Document,Long> {
    Document findDocumentById(UUID id);
}
