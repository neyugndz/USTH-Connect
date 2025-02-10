package com.usth_connect.vpn_server_backend_usth.controller;

import com.usth_connect.vpn_server_backend_usth.entity.moodle.Course;
import com.usth_connect.vpn_server_backend_usth.entity.moodle.Resource;
import com.usth_connect.vpn_server_backend_usth.service.ActivityService;
import com.usth_connect.vpn_server_backend_usth.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {
    private static final Logger LOGGER = Logger.getLogger(CourseController.class.getName());
    @Autowired
    private CourseService courseService;

    @Autowired
    private ActivityService activityService;

    // Endpoint to save the Course
    @PostMapping("/save/{courseId}")
    public ResponseEntity<Course> saveCourse(@PathVariable Long courseId){
        Course course = courseService.saveCourseFromMoodle(courseId);
        return ResponseEntity.ok(course);
    }

    // Get all courses
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    // Get a course by its ID
    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getCourse(@PathVariable Long courseId) {
        Course course = courseService.getCourse(courseId);
        return ResponseEntity.ok(course);
    }

    // Save all courses
    @PostMapping("/save-all")
    public ResponseEntity<List<Course>> saveAllCourses() {
        List<Course> savedCourses = courseService.saveAllCoursesFromMoodle();
        return ResponseEntity.ok(savedCourses);
    }

    // Get course with activities and resources
    @GetMapping("/activities-resources")
    public ResponseEntity<List<Map<String, Object>>> getCoursesWithActivitiesAndResources() {
        List<Map<String, Object>> coursesData = activityService.getCoursesWithActivitiesAndResources();
        return ResponseEntity.ok(coursesData);
    }
}
