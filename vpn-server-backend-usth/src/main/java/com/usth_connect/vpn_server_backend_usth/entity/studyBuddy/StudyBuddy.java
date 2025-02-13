package com.usth_connect.vpn_server_backend_usth.entity.studyBuddy;

import com.usth_connect.vpn_server_backend_usth.entity.Student;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "study_buddy")
public class StudyBuddy {
    @Id
    @Column(name = "Student_ID")
    private String studentId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "Student_ID")
    private Student student;

    @Column(name = "Descriptions")
    private String descriptions;

    @Column(name = "Personality")
    private String personality;

    @Column(name = "Communication_Style")
    private String communicationStyle;

    @Column(name = "Looking_For")
    private String lookingFor;

    @OneToMany(mappedBy = "studyBuddy")
    private List<StudyBuddyInterest> interests;

    @OneToMany(mappedBy = "studyBuddy")
    private List<StudyBuddyPlaceStudy> preferredPlaces;

    @OneToMany(mappedBy = "studyBuddy")
    private List<StudyBuddyTimeStudy> preferredTimes;

    @OneToMany(mappedBy = "studyBuddy")
    private List<FavoriteSubject> favoriteSubjects;

    // Getters and Setters

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getPersonality() {
        return personality;
    }

    public void setPersonality(String personality) {
        this.personality = personality;
    }

    public String getCommunicationStyle() {
        return communicationStyle;
    }

    public void setCommunicationStyle(String communicationStyle) {
        this.communicationStyle = communicationStyle;
    }

    public String getLookingFor() {
        return lookingFor;
    }

    public void setLookingFor(String lookingFor) {
        this.lookingFor = lookingFor;
    }

    public List<StudyBuddyInterest> getInterests() {
        return interests;
    }

    public void setInterests(List<StudyBuddyInterest> interests) {
        this.interests = interests;
    }

    public List<StudyBuddyPlaceStudy> getPreferredPlaces() {
        return preferredPlaces;
    }

    public void setPreferredPlaces(List<StudyBuddyPlaceStudy> preferredPlaces) {
        this.preferredPlaces = preferredPlaces;
    }

    public List<StudyBuddyTimeStudy> getPreferredTimes() {
        return preferredTimes;
    }

    public void setPreferredTimes(List<StudyBuddyTimeStudy> preferredTimes) {
        this.preferredTimes = preferredTimes;
    }

    public List<FavoriteSubject> getFavoriteSubjects() {
        return favoriteSubjects;
    }

    public void setFavoriteSubjects(List<FavoriteSubject> favoriteSubjects) {
        this.favoriteSubjects = favoriteSubjects;
    }
}

