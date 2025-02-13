package com.usth_connect.vpn_server_backend_usth.entity.schedule;

import com.usth_connect.vpn_server_backend_usth.entity.Module;
import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "schedules")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "Module_Code")
    private com.usth_connect.vpn_server_backend_usth.entity.Module module;

    @Column(name = "Module_Name")
    private String moduleName;

    @Column(name = "Start_time")
    private LocalTime startTime;

    @Column(name = "End_time")
    private LocalTime endTime;

    @Column(name = "Location_ID")
    private Integer locationId;

    @Column(name = "Room")
    private String room;

    @Column(name = "Reference_slide")
    private Integer referenceSlide;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public com.usth_connect.vpn_server_backend_usth.entity.Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Integer getReferenceSlide() {
        return referenceSlide;
    }

    public void setReferenceSlide(Integer referenceSlide) {
        this.referenceSlide = referenceSlide;
    }
}
