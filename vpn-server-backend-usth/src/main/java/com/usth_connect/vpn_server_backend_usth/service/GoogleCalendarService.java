package com.usth_connect.vpn_server_backend_usth.service;


import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.Value;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.google.auth.http.HttpCredentialsAdapter;

import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoogleCalendarService {
    private static final String APPLICATION_NAME = "USTH Connect";
    private Calendar calendarService;

    public GoogleCalendarService() throws IOException {
        FileInputStream serviceAccountStream = new FileInputStream("D:\\USTH\\Project\\usth-connect\\resources\\service_acc\\usth-connect-json.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccountStream).createScoped(List.of("https://www.googleapis.com/auth/calendar.readonly"));

        calendarService = new Calendar.Builder(new NetHttpTransport(), GsonFactory.getDefaultInstance(), new HttpCredentialsAdapter(credentials))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public Events fetchEvents(String calendarId, DateTime timeMin, DateTime timeMax) throws IOException {
        return calendarService.events().list(calendarId)
                .setTimeMin(timeMin)
                .setTimeMax(timeMax)
                .setMaxResults(50)
                .setSingleEvents(true)
                .execute();
    }

    public DateTime getStartOfWeek() {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.set(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.MONDAY);
        calendar.set(java.util.Calendar.HOUR_OF_DAY, 0);
        calendar.set(java.util.Calendar.MINUTE, 0);
        calendar.set(java.util.Calendar.SECOND, 0);
        calendar.set(java.util.Calendar.MILLISECOND, 0);
        return new DateTime(calendar.getTime());
    }

    public DateTime getEndOfWeek() {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.set(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.SUNDAY);

        if (calendar.get(java.util.Calendar.DAY_OF_WEEK) == java.util.Calendar.SUNDAY) {
            calendar.add(java.util.Calendar.WEEK_OF_YEAR, 1);
        }
        calendar.set(java.util.Calendar.HOUR_OF_DAY, 23);
        calendar.set(java.util.Calendar.MINUTE, 59);
        calendar.set(java.util.Calendar.SECOND, 59);
        calendar.set(java.util.Calendar.MILLISECOND, 999);
        return new DateTime(calendar.getTime());
    }

    public List<Event> getMultipleCalendarEvents(List<String> calendarIds, DateTime timeMin, DateTime timeMax) throws IOException {
        List<Event> allEvents = new ArrayList<>();
        for (String calendarId : calendarIds) {
            Events events = fetchEvents(calendarId, timeMin, timeMax);
            allEvents.addAll(events.getItems());
        }
        return allEvents;
    }

}
