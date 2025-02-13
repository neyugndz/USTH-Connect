package com.usth_connect.vpn_server_backend_usth.entity.vpn;

import com.usth_connect.vpn_server_backend_usth.entity.schedule.Event;
import jakarta.persistence.*;

@Entity
@Table(name = "vpn_event_sessions")
public class VpnEventSession {
    @EmbeddedId
    private VpnEventSessionKey id;

    @ManyToOne
    @MapsId("vpnSessionId")
    @JoinColumn(name = "VPN_Session_ID")
    private VpnSession vpnSession;

    @ManyToOne
    @MapsId("eventId")
    @JoinColumn(name = "Event_ID")
    private Event event;

    // Getters and Setters

    public VpnEventSessionKey getId() {
        return id;
    }

    public void setId(VpnEventSessionKey id) {
        this.id = id;
    }

    public VpnSession getVpnSession() {
        return vpnSession;
    }

    public void setVpnSession(VpnSession vpnSession) {
        this.vpnSession = vpnSession;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
