package com.adventureit.mediaservice.repository;

import com.adventureit.mediaservice.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<File,Long> {
    File findFileById(UUID id);
}
