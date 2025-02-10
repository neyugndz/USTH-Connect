package com.usth_connect.vpn_server_backend_usth.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.usth_connect.vpn_server_backend_usth.entity.schedule.Event;
import jakarta.persistence.*;
import org.locationtech.jts.geom.Geometry;

import java.util.List;

@Entity
@Table(name = "maps")
public class MapLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Location", unique = true, nullable = true , columnDefinition = "VARCHAR(255) DEFAULT 'No Location Provided'")
    private String location;

    @Column(name = "location_value", nullable = true)
    private String locationValue;

    @Column(name = "latitude", nullable = true)
    private Double latitude; // Latitude field

    @Column(name = "longitude", nullable = true)
    private Double longitude; // Longitude field

    @OneToMany(mappedBy = "location")
    @JsonBackReference
    private List<Event> events;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
        this.locationValue = this.location;
    }

    public String getLocationValue() {
        return locationValue;
    }

    public void setLocationValue(String locationValue) {
        this.locationValue = locationValue != null ? locationValue.trim() : "No Location Provided";
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
    @Override
    public String toString() {
        return location != null ? location : "No Location Provided";
    }
}
