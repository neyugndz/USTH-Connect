package com.usth_connect.vpn_server_backend_usth.service.scheduler;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.usth_connect.vpn_server_backend_usth.repository.EventRepository;
import com.usth_connect.vpn_server_backend_usth.service.EventNotificationService;
import com.usth_connect.vpn_server_backend_usth.service.EventService;
import com.usth_connect.vpn_server_backend_usth.service.GoogleCalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class EventSchedulerService {
    private static final Logger LOGGER = Logger.getLogger(EventSchedulerService.class.getName());

    @Autowired
    private GoogleCalendarService googleCalendarService;

    @Autowired
    private EventService eventService;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventNotificationService eventNotificationService;

    @Scheduled(fixedRate = 600000)
    public void syncGoogleCalendar() {
        LOGGER.info("Starting Google Calendar sync...");

        try {

            // Define the calendar IDs to fetch events from
            List<String> calendarIds = Arrays.asList(
                    "ict.usthedu@gmail.com", // B3-ICT
                    "e65gmijov921l1dbfq2p89ckvo@group.calendar.google.com", // B2-CS
                    "c_nafscvjc69aupft3m9abiujc9k@group.calendar.google.com", // B2-DS
                    "i7p4ol56gi8sqe0dq7uc3m0o4c@group.calendar.google.com", // B2-ICT
                    "6e1mlnn0diviabrod9kf6dj5dc@group.calendar.google.com", // B3-CS
                    "c_c5d8462bf26df40fcc2bdc65b9306c61e8c9a87c521b22812d61f4db9e8090a7@group.calendar.google.com", // B3-DS
                    "6ed1b9972c99908a3bc9652f827155b424b468fada94e0086e88da1acf78e551@group.calendar.google.com" // Test Endpoint
            );

            // Get the current time to define the time range
            DateTime timeMin = googleCalendarService.getStartOfYear();
            DateTime timeMax = googleCalendarService.getEndOfYear();

            LOGGER.info("Fetching events from " + timeMin + " to " + timeMax);

            // Fetch events from Google Calendar
            List<Event> googleEvents = googleCalendarService.getMultipleCalendarEvents(calendarIds, timeMin, timeMax);

            // Process the events and save them
            int savedEventsCount = 0;
            for (Event googleEvent : googleEvents) {
                // Check if the event already exists in the db
                Optional<com.usth_connect.vpn_server_backend_usth.entity.schedule.Event> existingEvent = eventRepository.findByGoogleEventId(googleEvent.getId());

                if (existingEvent.isPresent()) {
                    // Event exists, compare with existing local event and notify on changes
                    com.usth_connect.vpn_server_backend_usth.entity.schedule.Event localEvent = existingEvent.get();
                    eventNotificationService.notifyUserOnChangeOrNewEvent(googleEvent, localEvent, false);

                    // Update the existing event with new data
                    eventService.saveOrUpdateEvent(googleEvent);
                    eventNotificationService.notifyUserOnChangeOrNewEvent(googleEvent, localEvent, true);
                } else {
                    // If not exist, create a new one
                    eventService.saveOrUpdateEvent(googleEvent);
                }

                savedEventsCount++;
            }

            // Log the number of events processed
            LOGGER.info("Sync complete. Total events processed: " + savedEventsCount);

        } catch (IOException e) {
            LOGGER.severe("Error during Google Calendar sync: " + e.getMessage());
        }
    }
}
