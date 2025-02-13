package com.usth_connect.vpn_server_backend_usth.entity.schedule;

import com.usth_connect.vpn_server_backend_usth.entity.Student;
import jakarta.persistence.*;

@Entity
@Table(name = "event_participants")
public class EventParticipants {
    @EmbeddedId
    private EventParticipantKey id;

    @ManyToOne
    @MapsId("eventId")
    @JoinColumn(name = "Event_ID")
    private Event event;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "Student_ID")
    private Student student;

    // Getters and Setters
    public EventParticipantKey getId() {
        return id;
    }

    public void setId(EventParticipantKey id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}

