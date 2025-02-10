package com.usth_connect.vpn_server_backend_usth.service;

import com.usth_connect.vpn_server_backend_usth.entity.moodle.Category;
import com.usth_connect.vpn_server_backend_usth.entity.moodle.Course;
import com.usth_connect.vpn_server_backend_usth.entity.moodle.Resource;
import com.usth_connect.vpn_server_backend_usth.repository.CategoryRepository;
import com.usth_connect.vpn_server_backend_usth.repository.CourseRepository;
import com.usth_connect.vpn_server_backend_usth.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Logger;

@Service
public class CourseService {
    private static final Logger LOGGER = Logger.getLogger(CourseService.class.getName());

    @Autowired
    private MoodleService moodleService;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // Saving basic course info from Moodle Service
    public Course saveCourseFromMoodle(Long courseId) {
        Map<String, Object> courseDetails = moodleService.fetchCourses(courseId);

        // Extracting the List of Courses
        List<Map<String, Object>> courses = (List<Map<String, Object>>) courseDetails.get("courses");
        if (courses == null || courses.isEmpty()) {
            throw new IllegalArgumentException("No course found for ID: " + courseId);
        }

        // Assuming Moodle returns the specific course as the first item in the list
        Map<String, Object> courseData = courses.get(0);

        // Check if the course already exists in the database
        Optional<Course> existingCourseOpt = courseRepository.findByCourseId(courseId);

        Course course;
        if (existingCourseOpt.isPresent()) {
            // Update existing course
            course = existingCourseOpt.get();
            System.out.println("Updating existing course: " + course.getShortName());
        } else {
            // Create a new course
            course = new Course();
            course.setCourseId(courseId);
            System.out.println("Creating new course: " + courseData.get("shortname"));
        }

        // Set/update course details
        course.setShortName((String) courseData.get("shortname"));
        course.setFullName((String) courseData.get("fullname"));
        course.setDescription((String) courseData.get("summary"));
        course.setVisibility(((Number) courseData.get("visible")).intValue() == 1); // Convert "visible" to boolean

        // Save and return the course
        return courseRepository.save(course);
    }

    // Saving basic a list of course info from Moodle Service
    public List<Course> saveAllCoursesFromMoodle() {
        List<Map<String, Object>> courses = moodleService.fetchAllCourses();

        if (courses == null || courses.isEmpty()) {
            throw new IllegalArgumentException("No courses found in Moodle.");
        }

        List<Course> savedCourses = new ArrayList<>();
        for (Map<String, Object> courseData : courses) {
            try {
                Long moodleCourseId = ((Number) courseData.get("id")).longValue();

                // Check for existing course in the database
                Optional<Course> existingCourseOpt = courseRepository.findByCourseId(moodleCourseId);

                Course course;
                if (existingCourseOpt.isPresent()) {
                    // Update existing course
                    course = existingCourseOpt.get();
                    System.out.println("Updating existing course: " + course.getShortName());
                } else {
                    // Create a new course
                    course = new Course();
                    course.setCourseId(moodleCourseId);
                    System.out.println("Creating new course: " + courseData.get("shortname"));
                }

                // Set/update course details
                course.setShortName((String) courseData.get("shortname"));
                course.setFullName((String) courseData.get("fullname"));
                course.setDescription((String) courseData.get("summary"));
                course.setVisibility(((Number) courseData.get("visible")).intValue() == 1);

                // Save the course
                savedCourses.add(courseRepository.save(course));

            } catch (Exception e) {
                // Log and skip any problematic course data
                LOGGER.info("Error saving course: " + courseData);
                e.printStackTrace();
            }
        }

        return savedCourses;
    }

    // Fetch all courses
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // Get Course based on id from Database
    public Course getCourse(Long courseId) {
        Optional<Course> course = courseRepository.findByCourseId(courseId);
        if (course.isPresent()) {
            return course.get();
        } else {
            throw new IllegalArgumentException("Course not found for ID: " + courseId);
        }
    }
}
