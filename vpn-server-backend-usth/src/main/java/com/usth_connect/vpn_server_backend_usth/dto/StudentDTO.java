package com.usth_connect.vpn_server_backend_usth.dto;

import com.usth_connect.vpn_server_backend_usth.Enum.Gender;
import com.usth_connect.vpn_server_backend_usth.Enum.StudyYear;

import java.util.Date;


public class StudentDTO {
    private String id;
    private String fullName;
    private String email;
    private Date dob;
    private String major;
    private Gender gender;
    private String phone;
    private StudyYear studyYear;

    public StudentDTO(String id, String fullName, String email, Date dob, String major, Gender gender, String phone, StudyYear studyYear) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.dob = dob;
        this.major = major;
        this.gender = gender;
        this.phone = phone;
        this.studyYear = studyYear;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public StudyYear getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(StudyYear studyYear) {
        this.studyYear = studyYear;
    }
}
