package com.adventureit.notificationservice.Repos;

import com.adventureit.notificationservice.Entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {
     List<Notification> getNotificationByUserIDAndReadDateTime(UUID userId,Date date);


}