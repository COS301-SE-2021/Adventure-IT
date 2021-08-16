package com.adventureit.mediaservice.Repository;

import com.adventureit.mediaservice.Entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<File,Long> {
    File findFileById(UUID id);
    List<File> findAllByAdventureID(UUID id);
    List<File> findAllByOwner(UUID id);
    List<File> findAllByType(String type);
}
