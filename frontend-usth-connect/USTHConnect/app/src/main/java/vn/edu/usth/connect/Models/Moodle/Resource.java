package vn.edu.usth.connect.Models.Moodle;

import com.google.gson.annotations.SerializedName;

public class Resource {

    private Long resourceId;
    @SerializedName("name")
    private String resourceName;

    @SerializedName("fileUrl")
    private String resourceUrl;

    // Getters and Setters

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }
}
