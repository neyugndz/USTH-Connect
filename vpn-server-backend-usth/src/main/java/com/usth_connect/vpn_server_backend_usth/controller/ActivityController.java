package com.usth_connect.vpn_server_backend_usth.controller;

import com.usth_connect.vpn_server_backend_usth.entity.moodle.Activity;
import com.usth_connect.vpn_server_backend_usth.entity.moodle.Resource;
import com.usth_connect.vpn_server_backend_usth.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/activities")
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    // Endpoint to save activities for a given courseId
    @PostMapping("/save/{courseId}")
    public ResponseEntity<List<Activity>> saveActivities(@PathVariable Long courseId) {
        try {
            List<Activity> activities = activityService.saveActivity(courseId);  // Call saveActivity to save and return the activity
            if (activities != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(activities);  // Return the saved activity with status 201 (Created)
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);  // If no activity saved, return 400 (Bad Request)
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);  // Handle errors with 500 status
        }
    }

    // Endpoint to save all activities from all courses
    @PostMapping("/save-all")
    public ResponseEntity<List<Activity>> saveAllActivities() {
        try {
            List<Activity> activities = activityService.saveAllActivities();  // Call saveAllActivities to save and return the activities
            if (activities != null && !activities.isEmpty()) {
                return ResponseEntity.status(HttpStatus.CREATED).body(activities);  // Return the saved activities with status 201 (Created)
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);  // If no activities saved, return 204 (No Content)
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);  // Handle errors with 500 status
        }
    }

    // Get all activities for a specific course
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Activity>> getAllActivities(@PathVariable Long courseId) {
        List<Activity> activities = activityService.getAllActivities(courseId);
        return ResponseEntity.ok(activities);
    }

    // Endpoint to fetch the Resources
    @GetMapping("/{courseId}/activity/{activityId}/resources")
    public List<Resource> getResources(@PathVariable Long courseId, @PathVariable Long activityId) {
        return activityService.getResourcesForActivity(activityId, courseId);
    }

}
