package com.usth_connect.vpn_server_backend_usth.entity.studyBuddy;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "study_connection")
public class Connection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "study_buddy_1_id", nullable = false)
    private StudyBuddy studyBuddy1;

    @ManyToOne
    @JoinColumn(name = "study_buddy_2_id", nullable = false)
    private StudyBuddy studyBuddy2;

    @Column(name = "status", nullable = false)
    private String status = "PENDING";

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StudyBuddy getStudyBuddy1() {
        return studyBuddy1;
    }

    public void setStudyBuddy1(StudyBuddy studyBuddy1) {
        this.studyBuddy1 = studyBuddy1;
    }

    public StudyBuddy getStudyBuddy2() {
        return studyBuddy2;
    }

    public void setStudyBuddy2(StudyBuddy studyBuddy2) {
        this.studyBuddy2 = studyBuddy2;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
