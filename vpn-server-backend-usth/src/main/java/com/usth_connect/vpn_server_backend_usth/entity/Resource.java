package com.usth_connect.vpn_server_backend_usth.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "resources")
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "Module_Code")
    private Module module;

    @Column(name = "Lecture_number")
    private Integer lectureNumber;

    @Column(name = "Reference_slide")
    private Integer[] referenceSlide;

    @Column(name = "Homework_link")
    private String[] homeworkLink;

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Integer getLectureNumber() {
        return lectureNumber;
    }

    public void setLectureNumber(Integer lectureNumber) {
        this.lectureNumber = lectureNumber;
    }

    public Integer[] getReferenceSlide() {
        return referenceSlide;
    }

    public void setReferenceSlide(Integer[] referenceSlide) {
        this.referenceSlide = referenceSlide;
    }

    public String[] getHomeworkLink() {
        return homeworkLink;
    }

    public void setHomeworkLink(String[] homeworkLink) {
        this.homeworkLink = homeworkLink;
    }
}
