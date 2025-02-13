package com.usth_connect.vpn_server_backend_usth.repository;

import com.usth_connect.vpn_server_backend_usth.entity.Notification;
import com.usth_connect.vpn_server_backend_usth.entity.schedule.Event;
import com.usth_connect.vpn_server_backend_usth.entity.schedule.EventNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventNotificationRepository extends JpaRepository<EventNotification, String> {
    Optional<EventNotification> findByEventAndNotification(Event event, Notification notification);
}
