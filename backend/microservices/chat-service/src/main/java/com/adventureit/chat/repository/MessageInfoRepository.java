package com.adventureit.chat.repository;

import com.adventureit.chat.entity.Message;
import com.adventureit.chat.entity.MessageInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageInfoRepository extends JpaRepository<MessageInfo, UUID> {
    MessageInfo findMessageInfoById(UUID id);
    List<MessageInfo> findAllByChatID(UUID id);
}
