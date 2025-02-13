package com.usth_connect.vpn_server_backend_usth.entity.studyBuddy;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class StudyBuddyTimeStudyKey implements Serializable {
    private String studyBuddyId;
    private Integer timeId;

    // Getters, Setters, hashCode, equals

    public String getStudyBuddyId() {
        return studyBuddyId;
    }

    public void setStudyBuddyId(String studyBuddyId) {
        this.studyBuddyId = studyBuddyId;
    }

    public Integer getTimeId() {
        return timeId;
    }

    public void setTimeId(Integer timeId) {
        this.timeId = timeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudyBuddyTimeStudyKey that = (StudyBuddyTimeStudyKey) o;
        return Objects.equals(studyBuddyId, that.studyBuddyId) && Objects.equals(timeId, that.timeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studyBuddyId, timeId);
    }
}

