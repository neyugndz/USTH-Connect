package com.usth_connect.vpn_server_backend_usth.entity.schedule;

import com.usth_connect.vpn_server_backend_usth.entity.Notification;
import jakarta.persistence.*;

@Entity
@Table(name = "event_notifications")
public class EventNotification {
    @EmbeddedId
    private EventNotificationKey id;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("eventId")
    @JoinColumn(name = "Event_ID")
    private Event event;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId("notificationId")
    @JoinColumn(name = "Notification_ID")
    private Notification notification;

    // Getters and Setters

    public EventNotificationKey getId() {
        return id;
    }

    public void setId(EventNotificationKey id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
}

