package com.usth_connect.vpn_server_backend_usth.controller;

import com.usth_connect.vpn_server_backend_usth.entity.Notification;
import com.usth_connect.vpn_server_backend_usth.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public List<Notification> getNotifications() {
        return notificationService.getAllNotifications();
    }

    @GetMapping("/organizer")
    public List<Notification> getNotificationsByOrganizerId(@RequestParam String studyYear, String major) {
        return notificationService.getNotificationsByOrganizerDetails(studyYear, major);
    }

}
