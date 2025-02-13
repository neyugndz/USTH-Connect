package com.usth_connect.vpn_server_backend_usth.entity.studyBuddy;

import jakarta.persistence.*;

@Entity
@Table(name = "study_buddy_time_study")
public class StudyBuddyTimeStudy {
    @EmbeddedId
    private StudyBuddyTimeStudyKey id;

    @ManyToOne
    @MapsId("studyBuddyId")
    @JoinColumn(name = "Study_Buddy_ID")
    private StudyBuddy studyBuddy;

    @ManyToOne
    @MapsId("timeId")
    @JoinColumn(name = "Time_ID")
    private PreferredTimeToStudy preferredTime;

    // Getters and Setters

    public StudyBuddyTimeStudyKey getId() {
        return id;
    }

    public void setId(StudyBuddyTimeStudyKey id) {
        this.id = id;
    }

    public StudyBuddy getStudyBuddy() {
        return studyBuddy;
    }

    public void setStudyBuddy(StudyBuddy studyBuddy) {
        this.studyBuddy = studyBuddy;
    }

    public PreferredTimeToStudy getPreferredTime() {
        return preferredTime;
    }

    public void setPreferredTime(PreferredTimeToStudy preferredTime) {
        this.preferredTime = preferredTime;
    }
}

