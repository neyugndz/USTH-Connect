package com.usth_connect.vpn_server_backend_usth.entity.schedule;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.usth_connect.vpn_server_backend_usth.entity.Organizer;
import com.usth_connect.vpn_server_backend_usth.entity.MapLocation;
import com.usth_connect.vpn_server_backend_usth.entity.vpn.VpnEventSession;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eventId;

    @Column(name = "Event_Name")
    private String eventName;

    @Column(name = "Event_Description")
    private String eventDescription;

    @Column(name = "Event_Start")
    private LocalDateTime eventStart;

    @Column(name = "Event_End")
    private LocalDateTime eventEnd;

    @Column(name = "Google_Event_Id", unique = true)
    private String googleEventId;

    @ManyToOne
    @JoinColumn(name = "Location", referencedColumnName = "Location")
    @JsonIgnore
    private MapLocation location;

    @ManyToOne
    @JoinColumn(name = "Organizer_ID", referencedColumnName = "id")
    private Organizer organizer;

    @OneToMany(mappedBy = "event")
    private List<EventNotification> eventNotifications;

    @OneToMany(mappedBy = "event")
    private List<VpnEventSession> vpnEventSessions;

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

    public List<EventNotification> getEventNotifications() {
        return eventNotifications;
    }

    public void setEventNotifications(List<EventNotification> eventNotifications) {
        this.eventNotifications = eventNotifications;
    }

    public List<VpnEventSession> getVpnEventSessions() {
        return vpnEventSessions;
    }

    public void setVpnEventSessions(List<VpnEventSession> vpnEventSessions) {
        this.vpnEventSessions = vpnEventSessions;
    }
}
