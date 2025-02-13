package com.usth_connect.vpn_server_backend_usth.controller;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.usth_connect.vpn_server_backend_usth.dto.EventDTO;
import com.usth_connect.vpn_server_backend_usth.service.EventNotificationService;
import com.usth_connect.vpn_server_backend_usth.service.EventService;
import com.usth_connect.vpn_server_backend_usth.service.GoogleCalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/events")
public class CalendarController {
    private static final Logger logger = Logger.getLogger(CalendarController.class.getName());

    @Autowired
    private GoogleCalendarService googleCalendarService;

    @Autowired
    private EventService eventService;

    @Autowired
    private EventNotificationService eventNotificationService;

    @GetMapping("/fetch-events")
    public ResponseEntity<Map<String, Object>> fetchEvents() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<String> calendarIds = List.of(
                    "ict.usthedu@gmail.com",
                    "e65gmijov921l1dbfq2p89ckvo@group.calendar.google.com",
                    "i7p4ol56gi8sqe0dq7uc3m0o4c@group.calendar.google.com",
                    "b69a46455e0f40497a09a7bdb1fb4a3651ee872c8371238487159d5b17944e54@group.calendar.google.com");

            // Log before calling the service
            logger.info("Fetching events from the following calendars: " + calendarIds);
            // Define time range - start of the current week (Monday) to the end of the current week (Sunday)
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            startCalendar.set(Calendar.HOUR_OF_DAY, 0);
            startCalendar.set(Calendar.MINUTE, 0);
            startCalendar.set(Calendar.SECOND, 0);
            DateTime timeMin = new DateTime(startCalendar.getTime(), startCalendar.getTimeZone());

            Calendar endCalendar = Calendar.getInstance();
            endCalendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            endCalendar.add(Calendar.WEEK_OF_YEAR, 1);
            endCalendar.set(Calendar.HOUR_OF_DAY, 23);
            endCalendar.set(Calendar.MINUTE, 59);
            endCalendar.set(Calendar.SECOND, 59);
            DateTime timeMax = new DateTime(endCalendar.getTime(), endCalendar.getTimeZone());

            logger.info("Fetching events from " + timeMin + " to " + timeMax);

            // Fetch events from each calendar within the specified time range
            List<Event> events = googleCalendarService.getMultipleCalendarEvents(calendarIds, timeMin, timeMax);
            int savedEventsCount = 0;

            List<Map<String, String>> savedEventsDetails = new ArrayList<>();
            for (Event event : events) {
                eventService.saveOrUpdateEvent(event);
                savedEventsCount++;
                savedEventsDetails.add(Map.of(
                        "Event Name", event.getSummary(),
                        "Start Time", event.getStart().getDateTime() != null ? event.getStart().getDateTime().toString() : "N/A",
                        "End Time", event.getEnd().getDateTime() != null ? event.getEnd().getDateTime().toString() : "N/A",
                        "Description", event.getDescription() != null ? event.getDescription() : "N/A"
                ));
            }

            // Notify users of event changes
            eventNotificationService.notifyUserOnEventChange(events);
            // Prepare the response map
            response.put("message", "Events fetched and saved successfully!");
            response.put("totalEventsFetched", events.size());
            response.put("totalEventsSaved", savedEventsCount);
            response.put("eventsDetails", savedEventsDetails);

            return ResponseEntity.ok(response);
        } catch (IOException e) {
            // Handle exceptions and prepare error response
            response.put("message", "Error fetching events");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping
    public List<EventDTO> getEvents() {
        logger.info("Retrieving all events from the database");
        return eventService.getAllEvents(); // Implement method in EventService to return events
    }
}
