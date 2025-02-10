package com.usth_connect.vpn_server_backend_usth.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.usth_connect.vpn_server_backend_usth.Enum.Gender;
import com.usth_connect.vpn_server_backend_usth.Enum.Role;
import com.usth_connect.vpn_server_backend_usth.Enum.StudyYear;
import com.usth_connect.vpn_server_backend_usth.entity.moodle.Course;
import com.usth_connect.vpn_server_backend_usth.entity.studyBuddy.StudyBuddy;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="student")
public class Student implements UserDetails {
    @Id
    private String id;

    @Column(name = "FullName")
    private String fullName;

    @Column(name = "Gender")
    private Gender gender;

    @Column(name = "Major")
    private String major;

    @Column(name = "phone")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "DoB")
    private Date dob;

    @Column(name = "Study_Year")
    private StudyYear studyYear;

    // Refer to the student field in the StudyBuddy
    @JsonManagedReference
    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    private StudyBuddy studyBuddy;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany(mappedBy = "students")
    private List<Course> courses;

    @Column(name = "sip_username")
    private String sipUsername;

    @Column(name = "sip_password")
    private String sipPassword;

    @Column(name = "sip_domain")
    private String sipDomain;


    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public StudyYear getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(StudyYear studyYear) {
        this.studyYear = studyYear;
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
        // Update the associated StudyBuddy entity
        if (studyBuddy != null) {
            studyBuddy.setMajor(major);
        }
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public StudyBuddy getStudyBuddy() {
        return studyBuddy;
    }

    public void setStudyBuddy(StudyBuddy studyBuddy) {
        this.studyBuddy = studyBuddy;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public String getSipUsername() {
        return sipUsername;
    }

    public void setSipUsername(String sipUsername) {
        this.sipUsername = sipUsername;
    }

    public String getSipPassword() {
        return sipPassword;
    }

    public void setSipPassword(String sipPassword) {
        this.sipPassword = sipPassword;
    }

    public String getSipDomain() {
        return sipDomain;
    }

    public void setSipDomain(String sipDomain) {
        this.sipDomain = sipDomain;
    }
}
