package com.usth_connect.vpn_server_backend_usth.entity.moodle;

import com.usth_connect.vpn_server_backend_usth.entity.Student;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "course")
public class Course {
    @Id
    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "description")
    private String description;

    @Column(name = "visibility")
    private Boolean visibility;

    @ManyToMany
    @JoinTable(
            name = "student_course", // Join table name
            joinColumns = @JoinColumn(name = "course_id"), // FK to Course
            inverseJoinColumns = @JoinColumn(name = "student_id") // FK to Student
    )
    private List<Student> students;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Activity> activities;

    public Course(Long courseId, String courseName, String shortName) {
        this.courseId = courseId;
        this.fullName = courseName;
        this.shortName = shortName;
    }

    public Course() {
    }
    // Getters and Setters

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getVisibility() {
        return visibility;
    }

    public void setVisibility(Boolean visibility) {
        this.visibility = visibility;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }
}
