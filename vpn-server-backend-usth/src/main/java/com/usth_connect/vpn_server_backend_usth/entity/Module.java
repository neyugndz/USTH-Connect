package com.usth_connect.vpn_server_backend_usth.entity;
import com.usth_connect.vpn_server_backend_usth.entity.schedule.Schedule;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "module")
public class Module {
    @Id
    private String moduleCode;

    @Column(name = "Major")
    private String major;

    @Column(name = "Module_Name")
    private String moduleName;

    @Column(name = "Lecturers")
    private String lecturers;

    @OneToMany(mappedBy = "module")
    private List<Schedule> schedules;

    @OneToMany(mappedBy = "module")
    private List<Resource> resources;

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getLecturers() {
        return lecturers;
    }

    public void setLecturers(String lecturers) {
        this.lecturers = lecturers;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }
}
