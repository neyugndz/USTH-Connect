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

    @Column(name = "Location", unique = true)
    private String location;

    @OneToMany(mappedBy = "location")
    @JsonBackReference // Prevent circular reference in the reverse relationship
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
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
