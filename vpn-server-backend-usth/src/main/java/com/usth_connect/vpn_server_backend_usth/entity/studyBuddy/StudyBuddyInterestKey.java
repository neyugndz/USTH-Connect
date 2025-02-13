package com.usth_connect.vpn_server_backend_usth.entity.studyBuddy;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class StudyBuddyInterestKey implements Serializable {
    private String studyBuddyId;
    private Integer hobbyId;

    // Getters, Setters, hashCode, equals

    public String getStudyBuddyId() {
        return studyBuddyId;
    }

    public void setStudyBuddyId(String studyBuddyId) {
        this.studyBuddyId = studyBuddyId;
    }

    public Integer getHobbyId() {
        return hobbyId;
    }

    public void setHobbyId(Integer hobbyId) {
        this.hobbyId = hobbyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudyBuddyInterestKey that = (StudyBuddyInterestKey) o;
        return Objects.equals(studyBuddyId, that.studyBuddyId) && Objects.equals(hobbyId, that.hobbyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studyBuddyId, hobbyId);
    }
}

