package com.usth_connect.vpn_server_backend_usth.entity;

import com.usth_connect.vpn_server_backend_usth.entity.schedule.EventNotification;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer notificationId;

    @ManyToOne
    @JoinColumn(name = "Student_ID", referencedColumnName = "ID")
    private Student student;

    @Column(name = "Message")
    private String message;

    @Column(name = "Created_At", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "Is_Read", nullable = false)
    private Boolean isRead = false;

    @OneToOne(mappedBy = "notification")
    private EventNotification eventNotification;

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
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

    public EventNotification getEventNotification() {
        return eventNotification;
    }

    public void setEventNotification(EventNotification eventNotification) {
        this.eventNotification = eventNotification;
    }
}