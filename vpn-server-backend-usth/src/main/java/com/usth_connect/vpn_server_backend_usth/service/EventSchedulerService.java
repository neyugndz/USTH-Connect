package com.usth_connect.vpn_server_backend_usth.service;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.google.auto.value.AutoOneOf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@Service
public class EventSchedulerService {
    private static final Logger LOGGER = Logger.getLogger(EventSchedulerService.class.getName());

    @Autowired
    private GoogleCalendarService googleCalendarService;

    @Autowired
    private EventService eventService;

    @Autowired
    private EventNotificationService eventNotificationService;

    @Scheduled(fixedRate = 60000)
    public void syncGoogleCalendar() {
        LOGGER.info("Starting Google Calendar sync...");

        try {
            // Define the calendar IDs to fetch events from
            List<String> calendarIds = Arrays.asList(
                    "ict.usthedu@gmail.com",
                    "e65gmijov921l1dbfq2p89ckvo@group.calendar.google.com",
                    "i7p4ol56gi8sqe0dq7uc3m0o4c@group.calendar.google.com",
                    "b69a46455e0f40497a09a7bdb1fb4a3651ee872c8371238487159d5b17944e54@group.calendar.google.com"
            );

            // Get the current time to define the time range
            DateTime timeMin = googleCalendarService.getStartOfWeek();
            DateTime timeMax = googleCalendarService.getEndOfWeek();

            LOGGER.info("Fetching events from " + timeMin + " to " + timeMax);

            // Fetch events from Google Calendar
            List<Event> events = googleCalendarService.getMultipleCalendarEvents(calendarIds, timeMin, timeMax);

            // Process the events and save them
            int savedEventsCount = 0;
            for (Event event : events) {
                eventService.saveOrUpdateEvent(event);
                LOGGER.info("Google Event ID: " + event.getId());
                savedEventsCount++;
            }

            // Create notifications for any event changes
            eventNotificationService.notifyUserOnEventChange(events);
            // Log the number of events processed
            LOGGER.info("Sync complete. Total events processed: " + savedEventsCount);

        } catch (IOException e) {
            LOGGER.severe("Error during Google Calendar sync: " + e.getMessage());
        }
    }
}
