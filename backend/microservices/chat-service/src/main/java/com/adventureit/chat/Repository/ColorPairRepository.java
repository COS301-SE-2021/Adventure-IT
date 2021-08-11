package com.adventureit.chat.Repository;

import com.adventureit.chat.Entity.ColorPair;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.UUID;

public interface ColorPairRepository extends JpaRepository<ColorPair, UUID> {
    List<ColorPair> findAllByAdventureId(UUID adventureId);
}
