package com.usth_connect.vpn_server_backend_usth.service;

import com.usth_connect.vpn_server_backend_usth.entity.Notification;
import com.usth_connect.vpn_server_backend_usth.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private OrganizerService organizerService;

    public Notification saveNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public Optional<Notification> getNotificationById(String id) {
        return notificationRepository.findById(id);
    }

    public void deleteNotification(String id) {
        notificationRepository.deleteById(id);
    }

    // New method to fetch notifications by organizerId
    public List<Notification> getNotificationsByOrganizerDetails(String studyYear, String major) {
        // Dynamically calculate the organizerId based on the studyYear and major
        int organizerId = organizerService.calculateOrganizerId(studyYear, major);

        // Fetch notifications based on the calculated organizerId
        return notificationRepository.findByOrganizerId(organizerId);
    }
}
