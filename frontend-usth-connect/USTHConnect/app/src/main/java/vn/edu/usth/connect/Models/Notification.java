package vn.edu.usth.connect.Models;

import java.time.LocalDateTime;

public class Notification {
    private String message;
    private Integer notificationId;
    private String createdAt;
    private Integer organizerId;

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(Integer organizerId) {
        this.organizerId = organizerId;
    }
}
