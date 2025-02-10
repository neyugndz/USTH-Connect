package com.usth_connect.vpn_server_backend_usth.service;

import com.usth_connect.vpn_server_backend_usth.dto.EventDTO;
import com.usth_connect.vpn_server_backend_usth.entity.Notification;
import com.usth_connect.vpn_server_backend_usth.entity.schedule.Event;
import com.usth_connect.vpn_server_backend_usth.repository.EventRepository;
import com.usth_connect.vpn_server_backend_usth.repository.MapLocationRepository;
import com.usth_connect.vpn_server_backend_usth.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

@Service
public class EventNotificationService {
    private static final Logger LOGGER = Logger.getLogger(EventNotificationService.class.getName());
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private MapLocationRepository mapLocationRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Transactional
    public void notifyUserOnChangeOrNewEvent(com.google.api.services.calendar.model.Event googleEvent, Event localEvent, boolean isNewEvent) {
        // Map the Google event to the DTO
        EventDTO googleEventDTO = mapToEventDTO(googleEvent);

        StringBuilder changeMessage = new StringBuilder();

        if (isNewEvent) {
            // Generate notification for a new event
            changeMessage.append("New event created: \"").append(googleEventDTO.getEventName()).append("\". ");

            if (googleEventDTO.getEventStart() != null) {
                changeMessage.append("Starts at -> ").append(formatDateTime(googleEventDTO.getEventStart())).append(". ");
            }
            if (googleEventDTO.getEventEnd() != null) {
                changeMessage.append("Ends at -> ").append(formatDateTime(googleEventDTO.getEventEnd())).append(". ");
            }
            if (googleEventDTO.getLocationValue() != null && !googleEventDTO.getLocationValue().isEmpty()) {
                changeMessage.append("Location -> '").append(googleEventDTO.getLocationValue()).append("'. ");
            }
        } else {
            // Generate notification for an updated event
            changeMessage.append("Event \"").append(googleEventDTO.getEventName()).append("\" updated: ");

            boolean hasChanges = false;

            if (googleEventDTO.getEventName() != null && !googleEventDTO.getEventName().equals(localEvent.getEventName())) {
                changeMessage.append("Name -> '").append(googleEventDTO.getEventName()).append("'. ");
                hasChanges = true;
            }
            if (googleEventDTO.getEventDescription() != null && !googleEventDTO.getEventDescription().equals(localEvent.getEventDescription())) {
                changeMessage.append("Description updated. ");
                hasChanges = true;
            }
            if (googleEventDTO.getEventStart() != null && !googleEventDTO.getEventStart().equals(localEvent.getEventStart())) {
                changeMessage.append("Starts at -> ").append(formatDateTime(googleEventDTO.getEventStart())).append(". ");
                hasChanges = true;
            }
            if (googleEventDTO.getEventEnd() != null && !googleEventDTO.getEventEnd().equals(localEvent.getEventEnd())) {
                changeMessage.append("Ends at -> ").append(formatDateTime(googleEventDTO.getEventEnd())).append(". ");
                hasChanges = true;
            }
            if (googleEventDTO.getLocationValue() != null && !googleEventDTO.getLocationValue().trim().equals(localEvent.getLocationValue().trim())) {
                changeMessage.append("Location -> '").append(googleEventDTO.getLocationValue()).append("'. ");
                hasChanges = true;
            }

            if (!hasChanges) {
                return; // No changes detected, no need to create a notification.
            }
        }

        // Create the notification
        Notification notification = new Notification();
        notification.setMessage(changeMessage.toString().trim());
        notification.setCreatedAt(LocalDateTime.now());
        notification.setRead(false);

        // Add organizer information
        if (localEvent.getOrganizer() != null) {
            notification.setOrganizer(localEvent.getOrganizer());
        }

        // Save notification
        notificationRepository.save(notification);

        // Associate the notification with the event
        localEvent.getNotifications().add(notification);

        // Save the updated event
        eventRepository.save(localEvent);
    }

//    @Transactional
//    public void notifyUserOnChange(com.google.api.services.calendar.model.Event googleEvent, Event localEvent) {
//        // Map the Google event to the DTO
//        EventDTO googleEventDTO = mapToEventDTO(googleEvent);
//
//        // Initialize a concise change message
//        StringBuilder changeMessage = new StringBuilder("Event \"" + googleEventDTO.getEventName() + "\" updated: ");
//
//        boolean hasChanges = false;
//
//        // Compare event name (summary)
//        if (googleEventDTO.getEventName() != null && !googleEventDTO.getEventName().equals(localEvent.getEventName())) {
//            changeMessage.append("Name -> '").append(googleEventDTO.getEventName()).append("'. ");
//            hasChanges = true;
//        }
//
//        // Compare event description
//        if (googleEventDTO.getEventDescription() != null && !googleEventDTO.getEventDescription().equals(localEvent.getEventDescription())) {
//            changeMessage.append("Description updated. ");
//            hasChanges = true;
//        }
//
//        // Compare event start time
//        if (googleEventDTO.getEventStart() != null && !googleEventDTO.getEventStart().equals(localEvent.getEventStart())) {
//            String formattedStart = formatDateTime(googleEventDTO.getEventStart());
//            changeMessage.append("Starts at -> ").append(formattedStart).append(". ");
//            hasChanges = true;
//        }
//
//        // Compare event end time
//        if (googleEventDTO.getEventEnd() != null && !googleEventDTO.getEventEnd().equals(localEvent.getEventEnd())) {
//            String formattedEnd = formatDateTime(googleEventDTO.getEventEnd());
//            changeMessage.append("Ends at -> ").append(formattedEnd).append(". ");
//            hasChanges = true;
//        }
//
//        // Compare event location
//        if (googleEventDTO.getLocationValue() != null && !googleEventDTO.getLocationValue().trim().equals(localEvent.getLocationValue().trim())) {
//            changeMessage.append("Location -> '").append(googleEventDTO.getLocationValue()).append("'. ");
//            hasChanges = true;
//        }
//
//        if (hasChanges) {
//            // Create the notification
//            Notification notification = new Notification();
//            notification.setMessage(changeMessage.toString().trim());
//            notification.setCreatedAt(LocalDateTime.now());
//            notification.setRead(false);
//
//            // Add organizer information
//            if (localEvent.getOrganizer() != null) {
//                notification.setOrganizer(localEvent.getOrganizer());
//            }
//
//            // Save notification
//            notificationRepository.save(notification);
//
//            // Directly associate the notification with the event
//            localEvent.getNotifications().add(notification);
//
//            // Save the updated event
//            eventRepository.save(localEvent);
//        }
//    }


    // Format the LocalDateTime to HH:MM DD/MM/YYYY
    private String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "Unknown Time";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        return dateTime.format(formatter);
    }

    public EventDTO mapToEventDTO(com.google.api.services.calendar.model.Event googleEvent) {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setEventName(googleEvent.getSummary());
        eventDTO.setEventDescription(googleEvent.getDescription());
        eventDTO.setEventStart(convertToLocalDateTime(googleEvent.getStart().getDateTime()));
        eventDTO.setEventEnd(convertToLocalDateTime(googleEvent.getEnd().getDateTime()));
//        eventDTO.setLocation(getMapLocationFromString(googleEvent.getLocation()));
        eventDTO.setLocationValue(googleEvent.getLocation() != null ? googleEvent.getLocation() : "No Location Provided");
        return eventDTO;
    }

    // Convert from LocalDateTime to Date and Reverse
    private LocalDateTime convertToLocalDateTime(com.google.api.client.util.DateTime googleDateTime) {
        if (googleDateTime == null) {
            return null;
        }
        Instant instant = Instant.ofEpochMilli(googleDateTime.getValue());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
}
