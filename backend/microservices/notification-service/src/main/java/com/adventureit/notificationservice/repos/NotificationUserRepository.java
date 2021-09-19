package com.adventureit.notificationservice.repos;

import com.adventureit.notificationservice.entity.NotificationUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificationUserRepository extends JpaRepository<NotificationUser, UUID> {
    public NotificationUser findNotificationUserByUserId(UUID userId);
}
