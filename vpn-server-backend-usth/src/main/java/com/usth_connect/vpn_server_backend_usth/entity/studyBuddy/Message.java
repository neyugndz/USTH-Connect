package com.usth_connect.vpn_server_backend_usth.entity.studyBuddy;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "connection_id", nullable = false)
    private Connection connection;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private StudyBuddy sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private StudyBuddy receiver;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_read", nullable = false)
    private boolean isRead = false;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public StudyBuddy getSender() {
        return sender;
    }

    public void setSender(StudyBuddy sender) {
        this.sender = sender;
    }

    public StudyBuddy getReceiver() {
        return receiver;
    }

    public void setReceiver(StudyBuddy receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
