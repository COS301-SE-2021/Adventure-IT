package com.adventureit.chat.Repository;

import com.adventureit.chat.Entity.DirectChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DirectChatRepository extends JpaRepository<DirectChat, UUID> {
    DirectChat findByDirectChatId(UUID directChatId);
    List<DirectChat> findAllByParticipantsContaining(UUID id);
}
