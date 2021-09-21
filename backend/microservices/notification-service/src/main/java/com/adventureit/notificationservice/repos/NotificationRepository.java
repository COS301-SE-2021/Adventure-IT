package com.adventureit.notificationservice.repos;

import com.adventureit.notificationservice.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {
     List<Notification> getNotificationByUserIDAndReadDateTime(UUID userId,Date date);
     List<Notification> getNotificationByUserID(UUID userId);
     void removeAllByUserIDAndReadDateTime(UUID userId,Date date);

}