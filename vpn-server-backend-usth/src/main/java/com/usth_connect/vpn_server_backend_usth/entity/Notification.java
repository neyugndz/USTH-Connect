package com.usth_connect.vpn_server_backend_usth.entity;

import com.usth_connect.vpn_server_backend_usth.entity.schedule.Event;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer notificationId;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "eventId")
    private Event event;

    @Column(name = "Message", columnDefinition = "TEXT")
    private String message;

    @Column(name = "Created_At", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "Is_Read", nullable = false)
    private Boolean isRead = false;

    @ManyToOne
    @JoinColumn(name = "Organizer_ID", referencedColumnName = "id")
    private Organizer organizer;

    public Organizer getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

}