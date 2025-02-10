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
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public List<EventDTO> getEvents() {
        logger.info("Retrieving all events from the database");
        return eventService.getAllEvents();
    }

    // Get events based on organizer ID
    @GetMapping("/organizer")
    public ResponseEntity<List<com.usth_connect.vpn_server_backend_usth.entity.schedule.Event>> getEventsForUser(@RequestParam String studyYear, String major) {

        List<com.usth_connect.vpn_server_backend_usth.entity.schedule.Event> events = eventService.getEventsByOrganizer(studyYear, major);
        return ResponseEntity.ok(events);
    }
}
