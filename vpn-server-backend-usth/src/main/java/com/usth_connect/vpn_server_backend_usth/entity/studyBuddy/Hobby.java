package com.usth_connect.vpn_server_backend_usth.entity.studyBuddy;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "hobbies")
public class Hobby {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Interest")
    private String interest;

    @OneToMany(mappedBy = "hobby")
    private List<StudyBuddyInterest> studyBuddyInterests;

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public List<StudyBuddyInterest> getStudyBuddyInterests() {
        return studyBuddyInterests;
    }

    public void setStudyBuddyInterests(List<StudyBuddyInterest> studyBuddyInterests) {
        this.studyBuddyInterests = studyBuddyInterests;
    }
}

