package vn.edu.usth.connect.Models.StudyBuddy;

import java.time.LocalDateTime;

public class Connection {

    private Long id;
    private StudyBuddy studyBuddy1;
    private StudyBuddy studyBuddy2;
    private String status;
    private String createdAt;
    private String updatedAt;

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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}