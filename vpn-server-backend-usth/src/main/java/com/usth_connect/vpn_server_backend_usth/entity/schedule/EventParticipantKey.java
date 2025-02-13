package com.usth_connect.vpn_server_backend_usth.entity.schedule;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EventParticipantKey implements Serializable {
    private Integer eventId;
    private String studentId;

    // Getters, Setters, hashCode, equals

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventParticipantKey that = (EventParticipantKey) o;
        return Objects.equals(eventId, that.eventId) && Objects.equals(studentId, that.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, studentId);
    }
}

