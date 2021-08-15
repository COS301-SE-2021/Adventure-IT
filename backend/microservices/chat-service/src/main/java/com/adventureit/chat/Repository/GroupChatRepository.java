package com.adventureit.chat.Repository;

import com.adventureit.chat.Entity.GroupChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GroupChatRepository extends JpaRepository<GroupChat, UUID> {
    GroupChat findByGroupChatId(UUID groupChatId);
    List<GroupChat> findAllByParticipantsContaining(UUID id);
    GroupChat findAllByAdventureID(UUID adventureId);
}
