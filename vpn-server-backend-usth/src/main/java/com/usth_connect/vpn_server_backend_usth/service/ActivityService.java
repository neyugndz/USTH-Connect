package com.usth_connect.vpn_server_backend_usth.service;

import com.usth_connect.vpn_server_backend_usth.entity.moodle.Activity;
import com.usth_connect.vpn_server_backend_usth.entity.moodle.Course;
import com.usth_connect.vpn_server_backend_usth.entity.moodle.Resource;
import com.usth_connect.vpn_server_backend_usth.repository.ActivityRepository;
import com.usth_connect.vpn_server_backend_usth.repository.CourseRepository;
import com.usth_connect.vpn_server_backend_usth.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class ActivityService {
    private static final Logger LOGGER = Logger.getLogger(ActivityService.class.getName());

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private MoodleService moodleService;

    // Save activity from the Course
    public List<Activity> saveActivity(Long courseId) {
        LOGGER.info("Started saving activities for course ID: " + courseId);

        List<Map<String, Object>> activities = moodleService.fetchActivities(courseId);
        if (activities == null || activities.isEmpty()) {
            LOGGER.warning("No activities found for course ID: " + courseId);
            throw new IllegalArgumentException("No activities found for course ID: " + courseId);
        }

        List<Activity> savedActivities = new ArrayList<>();

        for (Map<String, Object> activityData : activities) {
            LOGGER.info("Processing activity ID: " + activityData.get("id"));

            Long activityId = ((Number) activityData.get("id")).longValue();

            // Check if the activity already exists in the database
            Optional<Activity> existingActivityOpt = activityRepository.findByActivityIdAndCourseId(activityId, courseId);
            Activity activity;
            if (existingActivityOpt.isPresent()) {
                // If activity exists, use the existing one
                activity = existingActivityOpt.get();
                LOGGER.info("Activity exists in the database, updating it.");
            } else {
                // If not, create a new Activity
                activity = new Activity();
                activity.setActivityId(activityId);
                activity.setCourseId(courseId);  // Set the courseId directly
                activity.setResources(new ArrayList<>());
                LOGGER.info("Creating new activity with ID: " + activityId);
            }

            try {
                // Set activity properties
                activity.setActivityName((String) activityData.get("name"));
                activity.setActivityType((String) activityData.get("modname"));
                activity.setCompletionStatus((String) activityData.get("completionstatus"));

                // Save resources related to this activity
                List<Map<String, Object>> resourceDataList = (List<Map<String, Object>>) activityData.getOrDefault("modules", new ArrayList<>());

                // Update or create new resources and safely replace the existing collection
                LOGGER.info("Resource data for activity ID: " + activityId + " - " + resourceDataList);
                activity.setResources(saveResources(activity, resourceDataList, courseId));

                // Save the activity to the database
                savedActivities.add(activityRepository.save(activity));
                LOGGER.info("Activity saved successfully with ID: " + activityId);
            } catch (Exception e) {
                LOGGER.severe("Error occurred while processing activity ID: " + activityId + ". Error: " + e.getMessage());
            }
        }
        LOGGER.info("Finished saving activities for course ID: " + courseId);
        return savedActivities;
    }

    // Save all activities from all courses
    public List<Activity> saveAllActivities() {
        LOGGER.info("Started saving all activities from Moodle server to the database...");

        List<Activity> savedActivities = new ArrayList<>();

        try {
            // Fetch all course IDs from Moodle
            List<Long> courseIds = moodleService.fetchAllCourseIds();
            LOGGER.info("Fetched " + courseIds.size() + " courses from Moodle");

            for (Long courseId : courseIds) {
                try {
                    LOGGER.info("Processing course ID: " + courseId);

                    // Save activities for the course and retrieve them
                    List<Activity> activities = saveActivity(courseId);

                    // Add saved activities to the list
                    savedActivities.addAll(activities);
                } catch (Exception e) {
                    LOGGER.severe("Error processing course ID: " + courseId + ". Error: " + e.getMessage());
                }
            }

            LOGGER.info("Successfully saved " + savedActivities.size() + " activities from all courses to the database");
        } catch (Exception e) {
            LOGGER.severe("Error occurred while saving all activities: " + e.getMessage());
        }

        return savedActivities;
    }


    // Save resources for an activity
    private List<Resource> saveResources(Activity activity, List<Map<String, Object>> resourceDataList, Long courseId) {
        List<Resource> resources = new ArrayList<>();
        for (Map<String, Object> resourceData : resourceDataList) {
            try {
                LOGGER.info("Saving resource: " + resourceData.get("name"));

                // Extract data from resourceData Map
                Long resourceId = ((Number) resourceData.get("id")).longValue();
                String type = (String) resourceData.get("modname");
                String name = (String) resourceData.get("name");
                String fileUrl = (String) resourceData.get("url");

                // Log for debugging
                LOGGER.info("Resource ID: " + resourceId);
                LOGGER.info("Resource Type: " + type);
                LOGGER.info("Resource Name: " + name);
                LOGGER.info("Resource File URL: " + fileUrl);

                // Create a new Resource entity and set the values
                Resource resource = new Resource();
                resource.setActivity(activity); // Associate resource with the activity
                resource.setId(resourceId);  // Set resource ID
                resource.setType(type);  // Set resource type
                resource.setName(name);  // Set resource name
                resource.setFileUrl(fileUrl);  // Set file URL for the resource
                resource.setCourseId(courseId);

                resources.add(resourceRepository.save(resource));
            } catch (Exception e) {
                LOGGER.severe("Error occurred while saving resource: " + resourceData.get("name") + ". Error: " + e.getMessage());
            }
        }
        return resources;
    }

    // Fetch resources related to an activity
    public List<Resource> getResourcesForActivity(Long activityId, Long courseId) {
        LOGGER.info("Fetching resources for activity ID: " + activityId + " and course ID: " + courseId);

        // Fetch the resources from the database
        List<Resource> resources = resourceRepository.findByActivity_ActivityIdAndActivity_CourseId(activityId, courseId);

        if (resources == null || resources.isEmpty()) {
            LOGGER.warning("No resources found for activity ID: " + activityId + " and course ID: " + courseId);
            throw new IllegalArgumentException("No resources found for activity ID: " + activityId + " and course ID: " + courseId);
        }

        LOGGER.info("Successfully fetched " + resources.size() + " resources for activity ID: " + activityId);
        return resources;
    }

    // Get all activities
    public List<Activity> getAllActivities(Long courseId) {
        return activityRepository.findByCourseId(courseId);
    }

    // Get activity based on id of course from Database
    public List<Activity> getActivity(Long courseId) {
        // Fetch the list of activities associated with the given courseId
        List<Activity> activities = activityRepository.findByCourseId(courseId);

        if (activities != null && !activities.isEmpty()) {
            return activities;
        } else {
            throw new IllegalArgumentException("No activities found for Course ID: " + courseId);
        }
    }


    // Fetch all the resources from Moodle
    public List<Resource> fetchAllResources() {
        LOGGER.info("Started fetching all resources from Moodle server...");

        List<Resource> allResources = new ArrayList<>();

        try {
            // Fetch all course IDs from Moodle
            List<Long> courseIds = moodleService.fetchAllCourseIds();
            LOGGER.info("Fetched " + courseIds.size() + " courses from Moodle");

            for (Long courseId : courseIds) {
                try {
                    LOGGER.info("Processing course ID: " + courseId);

                    // Save activities for the course and retrieve them
                    List<Activity> activities = saveActivity(courseId);

                    // For each activity, fetch and accumulate resources
                    for (Activity activity : activities) {
                        List<Resource> resources = resourceRepository.findByActivity_ActivityIdAndActivity_CourseId(
                                activity.getActivityId(), courseId);

                        allResources.addAll(resources);
                    }
                } catch (Exception e) {
                    LOGGER.severe("Error processing course ID: " + courseId +". Error: " + e.getMessage());
                }
            }
            LOGGER.info("Successfully fetched " + allResources.size() + " resources from all courses");
        } catch (Exception e) {
            LOGGER.info("Error occurred while fetching all resources: " +e.getMessage());
        }

        return allResources;
    }

    // Fetch courses with their activities and resources
    public List<Map<String, Object>> getCoursesWithActivitiesAndResources() {
        List<Map<String, Object>> result = new ArrayList<>();

        // Fetch all courses from the database
        List<Course> courses = courseRepository.findAll();

        for (Course course : courses) {
            Map<String, Object> courseData = new HashMap<>();
            courseData.put("courseId", course.getCourseId());
            courseData.put("courseName", course.getFullName());

            // Fetch activities for the course
            List<Activity> activities = activityRepository.findByCourseId(course.getCourseId());
            List<Map<String, Object>> activitiesData = new ArrayList<>();

            for (Activity activity : activities) {
                Map<String, Object> activityData = new HashMap<>();
                activityData.put("activityId", activity.getActivityId());
                activityData.put("activityName", activity.getActivityName());

                // Fetch resources for the activity
                List<Resource> resources = resourceRepository.findByActivity_ActivityIdAndActivity_CourseId(
                        activity.getActivityId(), course.getCourseId());
                List<Map<String, Object>> resourcesData = resources.stream().map(resource -> {
                    Map<String, Object> resourceData = new HashMap<>();
                    resourceData.put("resourceId", resource.getId());
                    resourceData.put("resourceName", resource.getName());
                    resourceData.put("resourceUrl", resource.getFileUrl());
                    return resourceData;
                }).collect(Collectors.toList());

                activityData.put("resources", resourcesData);
                activitiesData.add(activityData);
            }

            courseData.put("activities", activitiesData);
            result.add(courseData);
        }

        return result;
    }


    // Save all resources from all courses
    public List<Resource> saveAllResources() {
        LOGGER.info("Started saving all resources from Moodle server to the database...");

        List<Resource> savedResources = new ArrayList<>();

        try {
            // Fetch all course IDs from Moodle
            List<Long> courseIds = moodleService.fetchAllCourseIds();
            LOGGER.info("Fetched " + courseIds.size() + " courses from Moodle");

            for (Long courseId : courseIds) {
                try {
                    LOGGER.info("Processing course ID: " + courseId);

                    // Save activities for the course and retrieve them
                    List<Activity> activities = saveActivity(courseId);

                    // For each activity, save resources and accumulate them
                    for (Activity activity : activities) {
                        List<Resource> resources = resourceRepository.findByActivity_ActivityIdAndActivity_CourseId(
                                activity.getActivityId(), courseId);

                        for (Resource resource : resources) {
                            try {
                                // Replace 'localhost' with '100.69.153.113' in the resource URL
                                String originalUrl = resource.getFileUrl();
                                if (originalUrl.contains("localhost")) {
                                    String updatedUrl = originalUrl.replace("localhost", "100.69.153.113");
                                    resource.setFileUrl(updatedUrl);
                                    LOGGER.info("Updated URL for resource: " + resource.getName() + " to " + updatedUrl);
                                }

                                // Check if the resource already exists in the database
                                Optional<Resource> existingResourceOpt = resourceRepository.findById(resource.getId());
                                if (existingResourceOpt.isPresent()) {
                                    LOGGER.info("Resource already exists: " + resource.getName() + ", updating...");
                                    resourceRepository.save(resource); // Update existing resource
                                } else {
                                    LOGGER.info("Saving new resource: " + resource.getName());
                                    resourceRepository.save(resource); // Save new resource
                                }
                                savedResources.add(resource);
                            } catch (Exception e) {
                                LOGGER.severe("Error saving resource: " + resource.getName() + ". Error: " + e.getMessage());
                            }
                        }
                    }
                } catch (Exception e) {
                    LOGGER.severe("Error processing course ID: " + courseId + ". Error: " + e.getMessage());
                }
            }
            LOGGER.info("Successfully saved " + savedResources.size() + " resources from all courses to the database");
        } catch (Exception e) {
            LOGGER.severe("Error occurred while saving all resources: " + e.getMessage());
        }

        return savedResources;
    }
}
