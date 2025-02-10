package vn.edu.usth.connect.Schedule.TimeTable.Hour;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Locale;

import vn.edu.usth.connect.Schedule.TimeTable.Event.Event;

public class HourEvent {

    LocalTime startTime;
    LocalTime endTime;
    ArrayList<Event> events;

    public HourEvent(LocalTime startTime, LocalTime endTime, ArrayList<Event> events) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.events = events;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }
}
