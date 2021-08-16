package com.adventureit.chat.Repository;

import com.adventureit.chat.Entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID>{
    Message findMessageById(UUID id);
    List<Message> findAllByChatId(UUID chatId);
}
