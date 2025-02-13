package com.usth_connect.vpn_server_backend_usth.entity.vpn;

import com.usth_connect.vpn_server_backend_usth.entity.Student;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "vpn_sessions")
public class VpnSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sessionId;

    @ManyToOne
    @JoinColumn(name = "Student_ID", referencedColumnName = "ID")
    private Student student;

    @Column(name = "Start_Time", nullable = false)
    private LocalDateTime startTime = LocalDateTime.now();

    @Column(name = "End_Time")
    private LocalDateTime endTime;

    @Column(name = "IP_Address")
    private String ipAddress;

    @Column(name = "Status", nullable = false)
    private String status;

    @OneToMany(mappedBy = "vpnSession")
    private List<VpnEventSession> vpnEventSessions;

    // Getters and Setters

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

