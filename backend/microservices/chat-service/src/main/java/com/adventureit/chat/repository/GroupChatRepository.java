package com.adventureit.chat.repository;

import com.adventureit.chat.entity.GroupChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GroupChatRepository extends JpaRepository<GroupChat, UUID> {
    GroupChat findGroupChatByGroupChatId(UUID groupChatId);
    GroupChat getGroupChatByGroupChatId(UUID groupChatId);
    GroupChat getByGroupChatId(UUID groupChatId);
    List<GroupChat> findAllByParticipantsContaining(UUID id);
    GroupChat findAllByAdventureID(UUID adventureId);
}
