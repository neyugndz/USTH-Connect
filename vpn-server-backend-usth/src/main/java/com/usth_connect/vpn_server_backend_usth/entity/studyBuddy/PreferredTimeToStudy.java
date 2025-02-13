package com.usth_connect.vpn_server_backend_usth.entity.studyBuddy;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "prefer_time_to_study")
public class PreferredTimeToStudy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Time")
    private String time;

    @OneToMany(mappedBy = "preferredTime")
    private List<StudyBuddyTimeStudy> studyBuddyTimeStudies;

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<StudyBuddyTimeStudy> getStudyBuddyTimeStudies() {
        return studyBuddyTimeStudies;
    }

    public void setStudyBuddyTimeStudies(List<StudyBuddyTimeStudy> studyBuddyTimeStudies) {
        this.studyBuddyTimeStudies = studyBuddyTimeStudies;
    }
}

