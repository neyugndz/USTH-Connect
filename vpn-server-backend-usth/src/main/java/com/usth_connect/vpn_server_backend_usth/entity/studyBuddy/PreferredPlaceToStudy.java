package com.usth_connect.vpn_server_backend_usth.entity.studyBuddy;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "prefer_place_to_study")
public class PreferredPlaceToStudy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Place")
    private String place;

    @OneToMany(mappedBy = "preferredPlace")
    private List<StudyBuddyPlaceStudy> studyBuddyPlaceStudies;

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public List<StudyBuddyPlaceStudy> getStudyBuddyPlaceStudies() {
        return studyBuddyPlaceStudies;
    }

    public void setStudyBuddyPlaceStudies(List<StudyBuddyPlaceStudy> studyBuddyPlaceStudies) {
        this.studyBuddyPlaceStudies = studyBuddyPlaceStudies;
    }
}

