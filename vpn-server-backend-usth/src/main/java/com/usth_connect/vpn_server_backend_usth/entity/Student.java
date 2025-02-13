package com.usth_connect.vpn_server_backend_usth.entity;

import com.usth_connect.vpn_server_backend_usth.Enum.Gender;
import com.usth_connect.vpn_server_backend_usth.Enum.Role;
import com.usth_connect.vpn_server_backend_usth.Enum.StudyYear;
import com.usth_connect.vpn_server_backend_usth.entity.studyBuddy.StudyBuddy;
import com.usth_connect.vpn_server_backend_usth.entity.vpn.VpnSession;
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

    @Column(name = "FullName", nullable = false)
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

    @OneToMany(mappedBy = "student")
    private List<Notification> notifications;

    @OneToMany(mappedBy = "student")
    private List<VpnSession> vpnSessions;

    @OneToOne(mappedBy = "student")
    private StudyBuddy studyBuddy;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

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

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public List<VpnSession> getVpnSessions() {
        return vpnSessions;
    }

    public void setVpnSessions(List<VpnSession> vpnSessions) {
        this.vpnSessions = vpnSessions;
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
}
