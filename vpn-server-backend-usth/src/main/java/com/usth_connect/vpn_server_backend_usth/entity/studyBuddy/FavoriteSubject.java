package com.usth_connect.vpn_server_backend_usth.entity.studyBuddy;

import jakarta.persistence.*;

@Entity
@Table(name = "favorite_subject")
public class FavoriteSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "Study_Buddy_ID")
    private StudyBuddy studyBuddy;

    @Column(name = "Favorite_subject")
    private String favoriteSubject;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public StudyBuddy getStudyBuddy() {
        return studyBuddy;
    }

    public void setStudyBuddy(StudyBuddy studyBuddy) {
        this.studyBuddy = studyBuddy;
    }

    public String getFavoriteSubject() {
        return favoriteSubject;
    }

    public void setFavoriteSubject(String favoriteSubject) {
        this.favoriteSubject = favoriteSubject;
    }
}

