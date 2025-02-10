package com.usth_connect.vpn_server_backend_usth.entity.moodle;

import jakarta.persistence.*;

@Entity
@Table(name = "resources")
public class Resource {
    @Id
    private Long id;

    @Column(name = "Resource_Type")
    private String type;

    @Column(name = "Resource_Name")
    private String name;

    @Column(name = "course_id")
    private Long courseId;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @Column(name = "file_url")
    private String fileUrl;

    public Resource() {
    }

    public Resource(Long resourceId, String resourceName, String resourceType, String resourceUrl) {
        this.id = resourceId;
        this.name = resourceName;
        this.type = resourceType;
        this.fileUrl = resourceUrl;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
