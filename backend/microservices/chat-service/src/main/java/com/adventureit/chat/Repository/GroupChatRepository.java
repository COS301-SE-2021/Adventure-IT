package com.adventureit.chat.Repository;

import com.adventureit.chat.Entity.Chat;
import com.adventureit.chat.Entity.DirectChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GroupChatRepository extends JpaRepository<DirectChat, UUID> {
    Chat findChatById(UUID id);
    List<Chat> findAllByParticipantsContaining(UUID id);
}
