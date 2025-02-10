package vn.edu.usth.connect.Models.StudyBuddy;

import java.util.List;

public class StudyBuddy {
    private String studentId;
    private String name;
    private String major;
    private String gender;
    private String personality;
    private String communicationStyle;
    private String lookingFor;
    private List<String> interests;
    private List<String> favoriteSubjects;
    private List<String> preferredPlaces;
    private List<String> preferredTimes;

    // Constructors
    public StudyBuddy() {
    }

    public StudyBuddy(String studentId, String name, String gender, String major, String personality,
                      String communicationStyle, String lookingFor, List<String> interests,
                      List<String> favoriteSubjects, List<String> preferredPlaces, List<String> preferredTimes) {
        this.studentId = studentId;
        this.name = name;
        this.gender = gender;
        this.major = major;
        this.personality = personality;
        this.communicationStyle = communicationStyle;
        this.lookingFor = lookingFor;
        this.interests = interests;
        this.favoriteSubjects = favoriteSubjects;
        this.preferredPlaces = preferredPlaces;
        this.preferredTimes = preferredTimes;
    }

    public StudyBuddy(String studentId, String name) {
    }

    // Getters and Setters
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }

    public List<String> getFavoriteSubjects() {
        return favoriteSubjects;
    }

    public void setFavoriteSubjects(List<String> favoriteSubjects) {
        this.favoriteSubjects = favoriteSubjects;
    }

    public List<String> getPreferredPlaces() {
        return preferredPlaces;
    }

    public void setPreferredPlaces(List<String> preferredPlaces) {
        this.preferredPlaces = preferredPlaces;
    }

    public List<String> getPreferredTimes() {
        return preferredTimes;
    }

    public void setPreferredTimes(List<String> preferredTimes) {
        this.preferredTimes = preferredTimes;
    }
}

