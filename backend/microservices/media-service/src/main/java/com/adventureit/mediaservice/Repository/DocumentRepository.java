package com.adventureit.mediaservice.Repository;

import com.adventureit.mediaservice.Entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DocumentRepository extends JpaRepository<Document,Long> {
    Document findDocumentById(UUID id);
}
