package com.adventureit.chat.Repository;

import com.adventureit.chat.Entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChatRepository extends JpaRepository<Chat, UUID> {
    Chat findChatById(UUID id);
    Chat findByAdventureID(UUID adventureId);
    List<Chat> findAllByParticipantsContaining(UUID id);
}
