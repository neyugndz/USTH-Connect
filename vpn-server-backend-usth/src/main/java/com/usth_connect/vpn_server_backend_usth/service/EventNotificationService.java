package com.usth_connect.vpn_server_backend_usth.service;

import com.usth_connect.vpn_server_backend_usth.entity.MapLocation;
import com.usth_connect.vpn_server_backend_usth.entity.Notification;
import com.usth_connect.vpn_server_backend_usth.entity.schedule.Event;
import com.usth_connect.vpn_server_backend_usth.entity.schedule.EventNotification;
import com.usth_connect.vpn_server_backend_usth.entity.schedule.EventNotificationKey;
import com.usth_connect.vpn_server_backend_usth.repository.EventNotificationRepository;
import com.usth_connect.vpn_server_backend_usth.repository.EventRepository;
import com.usth_connect.vpn_server_backend_usth.repository.MapLocationRepository;
import com.usth_connect.vpn_server_backend_usth.repository.NotificationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class EventNotificationService {
    private static final Logger LOGGER = Logger.getLogger(EventNotificationService.class.getName());
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventNotificationRepository eventNotificationRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private MapLocationRepository mapLocationRepository;

    @Transactional
    public void notifyUserOnEventChange(List<com.google.api.services.calendar.model.Event> fetchedGoogleEvents) {
        // Map Google Event to interal Events
        List<Event> fetchedEvents = fetchedGoogleEvents.stream()
                .map(this::mapGoogleEventToEntity)
                .toList();

        // Retrieve exisiting events from the database
        List<Event> existingEvents = eventRepository.findAll();

        // Compare fetch events with existing events
        for (Event fetchedEvent: fetchedEvents) {
            // Find matching event from the database based on id
            LOGGER.info("Processing event: " + fetchedEvent.getEventName());
            LOGGER.info("Fetched Google Event ID: " + fetchedEvent.getGoogleEventId());
            Event existingEvent = existingEvents.stream()
                    .filter(event ->
                            event.getGoogleEventId() != null && event.getGoogleEventId().equals(fetchedEvent.getGoogleEventId()))
                    .findFirst()
                    .orElse(null);

            if(existingEvent != null) {
                boolean eventChanged = false;
                StringBuilder changeMessage = new StringBuilder("Event " + fetchedEvent.getEventName() + " has been updated. Changes: ");

                // Track changes in event attributes and accumulate the changes into a single message
                if (!safeEquals(fetchedEvent.getEventName(), existingEvent.getEventName())) {
                    eventChanged = true;
                    changeMessage.append("Name, ");
                    LOGGER.info("Event name changed.");
                }
                if (!safeEquals(fetchedEvent.getEventDescription(), existingEvent.getEventDescription())) {
                    eventChanged = true;
                    changeMessage.append("Description, ");
                    LOGGER.info("Event description changed.");
                }
                if (!fetchedEvent.getEventStart().equals(existingEvent.getEventStart())) {
                    eventChanged = true;
                    changeMessage.append("Start time, ");
                    LOGGER.info("Event start time changed.");
                }
                if (!fetchedEvent.getEventEnd().equals(existingEvent.getEventEnd())) {
                    eventChanged = true;
                    changeMessage.append("End time, ");
                    LOGGER.info("Event end time changed.");
                }
                if (!fetchedEvent.getLocation().equals(existingEvent.getLocation())) {
                    eventChanged = true;
                    changeMessage.append("Location, ");
                    LOGGER.info("Event location changed.");
                }

                // Notify users if there are changes
                if (eventChanged) {
                    String finalMessage = changeMessage.toString();

                    if (finalMessage.endsWith(", ")) {
                        finalMessage = finalMessage.substring(0, finalMessage.length() - 2);  // Remove trailing comma
                    }
                    // Check if the notification already exists based on the change message
                    Optional<Notification> existingNotification = notificationRepository.findByMessage(finalMessage);
                    if (existingNotification.isPresent()) {
                        LOGGER.info("Notification already exists. Skipping creation.");
                        return;  // Skip if notification already exists
                    }
                    LOGGER.info("Event " + fetchedEvent.getEventName() + " has changes. Creating notification.");

                    // Create and save the new notification
                    Notification notification = new Notification();
                    notification.setMessage(finalMessage);
                    notification.setRead(false);  // Set to false since it's a new notification
                    notification.setCreatedAt(LocalDateTime.now());  // Set creation timestamp
                    notificationRepository.save(notification);

                    // Ensure the event is saved if it hasn't been persisted yet
                    if (existingEvent.getGoogleEventId() == null) {
                        eventRepository.save(existingEvent);  // Save the event if it hasn't been saved
                    }

                    // Check if the EventNotification already exists for the same event and notification
                    Optional<EventNotification> existingEventNotification = eventNotificationRepository
                            .findByEventAndNotification(fetchedEvent, notification);
                    if (existingEventNotification.isPresent()) {
                        LOGGER.info("EventNotification already exists. Skipping creation.");
                        return;  // Skip if EventNotification already exists
                    }

                    // Create and save the new EventNotification to link event and notification
                    EventNotification eventNotification = new EventNotification();
                    eventNotification.setEvent(fetchedEvent);
                    eventNotification.setNotification(notification);
                    eventNotificationRepository.save(eventNotification);

                    LOGGER.info("Notification created for event: " + fetchedEvent.getEventName());
                } else {
                    LOGGER.info("No change detected for event: " + fetchedEvent.getEventName());
                }
            } else {
                LOGGER.warning("No matching event found for: " + fetchedEvent.getEventName());
            }
        }
    }

    private boolean safeEquals(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equals(str2);
    }

    private Event mapGoogleEventToEntity(com.google.api.services.calendar.model.Event googleEvent) {
        Event event = new Event();
        event.setGoogleEventId(googleEvent.getId());
        event.setEventName(googleEvent.getSummary());
        event.setEventDescription(googleEvent.getDescription());
        event.setEventStart(googleEvent.getStart().getDateTime() != null
                ? convertToLocalDateTime(googleEvent.getStart().getDateTime())
                : convertDateToLocalDateTime(googleEvent.getStart().getDate()));

        event.setEventEnd(googleEvent.getEnd().getDateTime() != null
                ? convertToLocalDateTime(googleEvent.getEnd().getDateTime())
                : convertDateToLocalDateTime(googleEvent.getEnd().getDate()));

        event.setLocation(getMapLocationFromString(googleEvent.getLocation()));
        LOGGER.info("Mapped Event Google ID: " + event.getGoogleEventId());
        LOGGER.info("Mapped Event Location: " + event.getLocation());
        return event;
    }


    private static EventNotification getEventNotification(Event fetchedEvent, Notification notification) {
        EventNotification eventNotification = new EventNotification();
        EventNotificationKey eventNotificationKey = new EventNotificationKey();
        eventNotificationKey.setEventId(fetchedEvent.getEventId());
        eventNotificationKey.setNotificationId(notification.getNotificationId());
        eventNotification.setId(eventNotificationKey);
        eventNotification.setEvent(fetchedEvent);
        eventNotification.setNotification(notification);
        return eventNotification;
    }

    // Convert from LocalDateTime to Date and Reverse
    private LocalDateTime convertToLocalDateTime(com.google.api.client.util.DateTime googleDateTime) {
        if (googleDateTime == null) {
            return null;
        }
        Instant instant = Instant.ofEpochMilli(googleDateTime.getValue());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    private LocalDateTime convertDateToLocalDateTime(com.google.api.client.util.DateTime googleDate) {
        if (googleDate == null) {
            return null;
        }
        Instant instant = Instant.ofEpochMilli(googleDate.getValue());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate().atStartOfDay();
    }

//    private MapLocation getMapLocationFromString(String locationName) {
//        LOGGER.info("Fetching MapLocation for location name: " + locationName);
//
//        // Check if location exists in the database
//        MapLocation mapLocation = mapLocationRepository.findByLocation(locationName)
//                .orElseGet(() -> {
//                    LOGGER.info("Location " + locationName + " not found in the database. Creating a new MapLocation.");
//
//                    // If not found, create a new MapLocation
//                    MapLocation newLocation = new MapLocation();
//                    newLocation.setLocation(locationName);
//
//                    // Save the new MapLocation to the database
//                    mapLocationRepository.save(newLocation);
//
//                    LOGGER.info("Created and saved new MapLocation: " + newLocation);
//                    return newLocation;
//                });
//
//        LOGGER.info("Fetched MapLocation: " + mapLocation);
//        return mapLocation;
//    }


    private MapLocation getMapLocationFromString(String locationName) {
        // Check if location exists in the database
        return mapLocationRepository.findByLocation(locationName)
                .orElseGet(() -> {
                    // If not found, create a new MapLocation
                    MapLocation newLocation = new MapLocation();
                    newLocation.setLocation(locationName);
                    mapLocationRepository.save(newLocation);
                    return newLocation;
                });
    }

}
