package com.usth_connect.vpn_server_backend_usth.entity.schedule;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.usth_connect.vpn_server_backend_usth.entity.Notification;
import com.usth_connect.vpn_server_backend_usth.entity.Organizer;
import com.usth_connect.vpn_server_backend_usth.entity.MapLocation;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eventId;

    @Column(name = "Event_Name",  columnDefinition = "TEXT")
    private String eventName;

    @Column(name = "Event_Description",  columnDefinition = "TEXT")
    private String eventDescription;

    @Column(name = "Event_Start")
    private LocalDateTime eventStart;

    @Column(name = "Event_End")
    private LocalDateTime eventEnd;

    @Column(name = "Google_Event_Id", unique = true)
    private String googleEventId;

    @Column(name = "location_value")
    private String locationValue;

    @ManyToOne
    @JoinColumn(name = "Location", referencedColumnName = "Location")
    @JsonIgnore
    private MapLocation location;

    @ManyToOne
    @JoinColumn(name = "Organizer_ID", referencedColumnName = "id")
    private Organizer organizer;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Notification> notifications;

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public LocalDateTime getEventStart() {
        return eventStart;
    }

    public void setEventStart(LocalDateTime eventStart) {
        this.eventStart = eventStart;
    }

    public LocalDateTime getEventEnd() {
        return eventEnd;
    }

    public void setEventEnd(LocalDateTime eventEnd) {
        this.eventEnd = eventEnd;
    }

    public String getGoogleEventId() {
        return googleEventId;
    }

    public void setGoogleEventId(String googleEventId) {
        this.googleEventId = googleEventId;
    }

    public String getLocationValue() {
        return locationValue;
    }

    public void setLocationValue(String locationValue) {
        if (locationValue == null || locationValue.trim().isEmpty()) {
            this.locationValue = "No Location Provided";
        } else {
            this.locationValue = locationValue.trim();
        }
    }

    public MapLocation getLocation() {
        return location;
    }

    public void setLocation(MapLocation location) {
        this.location = location;
    }

    public Organizer getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }

    public Set<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
    }

    // Add/remove helpers for the relationship
    public void addNotification(Notification notification) {
        this.notifications.add(notification);
        notification.setEvent(this); // Maintain the relationship consistency
    }

    public void removeNotification(Notification notification) {
        this.notifications.remove(notification);
        notification.setEvent(null); // Nullify the relationship
    }
}
