package com.usth_connect.vpn_server_backend_usth.entity.schedule;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EventNotificationKey implements Serializable {
    private Integer eventId;
    private Integer notificationId;

    // Getters, Setters, hashCode, equals

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventNotificationKey that = (EventNotificationKey) o;
        return Objects.equals(eventId, that.eventId) && Objects.equals(notificationId, that.notificationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, notificationId);
    }
}

