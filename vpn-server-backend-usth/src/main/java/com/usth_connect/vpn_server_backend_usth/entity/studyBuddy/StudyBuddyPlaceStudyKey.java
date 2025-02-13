package com.usth_connect.vpn_server_backend_usth.entity.studyBuddy;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class StudyBuddyPlaceStudyKey implements Serializable {
    private String studyBuddyId;
    private Integer placeId;

    // Getters, Setters, hashCode, equals

    public Integer getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

    public String getStudyBuddyId() {
        return studyBuddyId;
    }

    public void setStudyBuddyId(String studyBuddyId) {
        this.studyBuddyId = studyBuddyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudyBuddyPlaceStudyKey that = (StudyBuddyPlaceStudyKey) o;
        return Objects.equals(studyBuddyId, that.studyBuddyId) && Objects.equals(placeId, that.placeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studyBuddyId, placeId);
    }
}

