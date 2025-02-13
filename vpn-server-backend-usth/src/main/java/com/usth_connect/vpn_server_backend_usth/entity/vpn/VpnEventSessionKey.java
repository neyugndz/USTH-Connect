package com.usth_connect.vpn_server_backend_usth.entity.vpn;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class VpnEventSessionKey implements Serializable {
    private Integer vpnSessionId;
    private Integer eventId;

    // Getters, Setters, hashCode, equals

    public Integer getVpnSessionId() {
        return vpnSessionId;
    }

    public void setVpnSessionId(Integer vpnSessionId) {
        this.vpnSessionId = vpnSessionId;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VpnEventSessionKey that = (VpnEventSessionKey) o;
        return Objects.equals(vpnSessionId, that.vpnSessionId) && Objects.equals(eventId, that.eventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vpnSessionId, eventId);
    }
}

