package com.usth_connect.vpn_server_backend_usth.entity.studyBuddy;

import jakarta.persistence.*;

@Entity
@Table(name = "study_buddy_place_study")
public class StudyBuddyPlaceStudy {
    @EmbeddedId
    private StudyBuddyPlaceStudyKey id;

    @ManyToOne
    @MapsId("studyBuddyId")
    @JoinColumn(name = "Study_Buddy_ID")
    private StudyBuddy studyBuddy;

    @ManyToOne
    @MapsId("placeId")
    @JoinColumn(name = "Place_ID")
    private PreferredPlaceToStudy preferredPlace;

    // Getters and Setters

    public StudyBuddyPlaceStudyKey getId() {
        return id;
    }

    public void setId(StudyBuddyPlaceStudyKey id) {
        this.id = id;
    }

    public StudyBuddy getStudyBuddy() {
        return studyBuddy;
    }

    public void setStudyBuddy(StudyBuddy studyBuddy) {
        this.studyBuddy = studyBuddy;
    }

    public PreferredPlaceToStudy getPreferredPlace() {
        return preferredPlace;
    }

    public void setPreferredPlace(PreferredPlaceToStudy preferredPlace) {
        this.preferredPlace = preferredPlace;
    }
}

