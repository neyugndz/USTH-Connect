package vn.edu.usth.connect.Network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import vn.edu.usth.connect.Models.Moodle.Activity;
import vn.edu.usth.connect.Models.Moodle.Course;
import vn.edu.usth.connect.Models.Moodle.Resource;

public interface ResourceService {
    @GET("/api/v1/courses/activities-resources")
    Call<List<Course>> getResources(@Header("Authorization") String authHeader);

    // Endpoint to fetch all activities based on courseId
    @GET("/api/v1/activities/course/{courseId}")
    Call<List<Activity>> getActivities(
            @Header("Authorization") String authHeader,
            @Path("courseId") Long courseId);

    @GET("/api/v1/activities/{courseId}/activity/{activityId}/resources")
    Call<List<Resource>> getResources(
            @Header("Authorization") String authHeader,
            @Path("courseId") Long courseId,
            @Path("activityId") Long activityId
    );


}
