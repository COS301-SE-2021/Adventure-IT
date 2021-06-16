package com.adventureit.adventureservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.adventureit.adventureservice.Entity.Adventure;
import java.util.List;
import java.util.UUID;

@Repository
public interface AdventureRepository extends JpaRepository<Adventure,Long>{
    Adventure findByID(UUID id);
}