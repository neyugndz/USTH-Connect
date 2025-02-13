//package com.usth_connect.vpn_server_backend_usth.component;
//
//import com.google.api.client.util.DateTime;
//import com.google.api.services.calendar.model.Event;
//import com.usth_connect.vpn_server_backend_usth.controller.CalendarController;
//import com.usth_connect.vpn_server_backend_usth.service.EventService;
//import com.usth_connect.vpn_server_backend_usth.service.GoogleCalendarService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.Calendar;
//import java.util.List;
//
//@Component
//public class CalendarFetchTask {
//
//    @Autowired
//    private CalendarController calendarController;
//
//    @Scheduled(fixedRate = 600000)
//    public void fetchAndSaveEvents() {
//        calendarController.fetchEvents();
//    }
//}
