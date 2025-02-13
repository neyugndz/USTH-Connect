package com.usth_connect.vpn_server_backend_usth.entity.studyBuddy;

import jakarta.persistence.*;

@Entity
@Table(name = "study_buddy_interests")
public class StudyBuddyInterest {
    @EmbeddedId
    private StudyBuddyInterestKey id;

    @ManyToOne
    @MapsId("studyBuddyId")
    @JoinColumn(name = "Study_Buddy_ID")
    private StudyBuddy studyBuddy;

    @ManyToOne
    @MapsId("hobbyId")
    @JoinColumn(name = "Hobby_ID")
    private Hobby hobby;

    // Getters and Setters
    public StudyBuddyInterestKey getId() {
        return id;
    }

    public void setId(StudyBuddyInterestKey id) {
        this.id = id;
    }

    public StudyBuddy getStudyBuddy() {
        return studyBuddy;
    }

    public void setStudyBuddy(StudyBuddy studyBuddy) {
        this.studyBuddy = studyBuddy;
    }

    public Hobby getHobby() {
        return hobby;
    }

    public void setHobby(Hobby hobby) {
        this.hobby = hobby;
    }
}

