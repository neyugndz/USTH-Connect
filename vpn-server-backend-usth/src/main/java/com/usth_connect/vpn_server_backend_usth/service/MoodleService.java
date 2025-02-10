package com.usth_connect.vpn_server_backend_usth.service;

import com.google.api.client.util.Value;
import com.usth_connect.vpn_server_backend_usth.component.MoodleProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

@Service
@ConfigurationProperties(prefix = "moodle")
public class MoodleService {
    private static final Logger LOGGER = Logger.getLogger(MoodleService.class.getName());
    private final MoodleProperties moodleProperties;
    private final RestTemplate restTemplate = new RestTemplate();

    public MoodleService(MoodleProperties moodleProperties) {
        this.moodleProperties = moodleProperties;
    }

    // Testing function to ensure the Moodle configuration
    public Map<String, Object> fetchSiteInfo() {
        String url = UriComponentsBuilder.fromHttpUrl(moodleProperties.getBaseUrl() + "/webservice/rest/server.php")
                .queryParam("wstoken",moodleProperties.getToken())
                .queryParam("moodlewsrestformat", "json")
                .queryParam("wsfunction", "core_webservice_get_site_info")
                .toUriString();

        return restTemplate.getForObject(url, Map.class);
    }

    // Moodle Service to fetch course info based on course ID
    public Map<String, Object> fetchCourses(Long courseId) {
        String url = UriComponentsBuilder.fromHttpUrl(moodleProperties.getBaseUrl() + "/webservice/rest/server.php")
                .queryParam("wstoken", moodleProperties.getToken())
                .queryParam("moodlewsrestformat", "json")
                .queryParam("wsfunction", "core_course_get_courses_by_field")
                .queryParam("field", "id")
                .queryParam("value", courseId)
                .toUriString();

        return restTemplate.getForObject(url, Map.class);
    }


    // Moodle Service to fetch list of courses info
    public List<Map<String, Object>> fetchAllCourses() {
        String url = UriComponentsBuilder.fromHttpUrl(moodleProperties.getBaseUrl() + "/webservice/rest/server.php")
                .queryParam("wstoken", moodleProperties.getToken())
                .queryParam("moodlewsrestformat", "json")
                .queryParam("wsfunction", "core_course_get_courses")
                .toUriString();

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Map<String, Object>>>() {}
        ).getBody();
    }

    // Moodle Service to fetch the activity of the course
    public List<Map<String, Object>> fetchActivities(Long courseId){
        String url = UriComponentsBuilder.fromHttpUrl(moodleProperties.getBaseUrl() + "/webservice/rest/server.php")
                .queryParam("wstoken", moodleProperties.getToken())
                .queryParam("moodlewsrestformat", "json")
                .queryParam("wsfunction", "core_course_get_contents")
                .queryParam("courseid", courseId)
                .toUriString();

        // Fetch the activities
        List<Map<String, Object>> activities = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Map<String, Object>>>() {}
        ).getBody();

        // Log the raw response body
        LOGGER.info("Raw response from Moodle for activities: " + activities);

        return activities;
    }

    // Moodle Service to fetch all activities
    public List<Map<String, Object>> fetchAllActivities(List<Long> courseIds) {
        List<Map<String, Object>> allActivities = new ArrayList<>();

        // Loop through each course ID and fetch its activities
        for (Long courseId : courseIds) {
            LOGGER.info("Fetching activities for course ID: " + courseId);
            List<Map<String, Object>> activities = fetchActivities(courseId);
            allActivities.addAll(activities);  // Accumulate activities from all courses
        }

        return allActivities;
    }


    // Moodle Service to fetch resources for an activity
    public List<Map<String, Object>> fetchResources(Long activityId) {
        String url = UriComponentsBuilder.fromHttpUrl(moodleProperties.getBaseUrl() + "/webservice/rest/server.php")
                .queryParam("wstoken", moodleProperties.getToken())
                .queryParam("moodlewsrestformat", "json")
                .queryParam("wsfunction", "core_activity_get_activity_resources")
                .queryParam("activityid", activityId)
                .toUriString();

        List<Map<String, Object>> resources = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Map<String, Object>>>() {}
        ).getBody();

        LOGGER.info("Raw response from Moodle for resources: " + resources);

        return resources;
    }

    // Moodle Service to fetch categories
    public List<Map<String, Object>> fetchCategories() {
        String url = UriComponentsBuilder.fromHttpUrl(moodleProperties.getBaseUrl() + "/webservice/rest/server.php")
                .queryParam("wstoken", moodleProperties.getToken())
                .queryParam("moodlewsrestformat", "json")
                .queryParam("wsfunction", "core_course_get_categories")
                .toUriString();

        // Log the constructed URL for debugging purposes
        LOGGER.info("Fetching categories from Moodle: " + url);

        // Make the API call and get the response
        ResponseEntity<List<Map<String, Object>>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Map<String, Object>>>() {}
        );

        // Log the raw response body
        List<Map<String, Object>> categories = responseEntity.getBody();
        LOGGER.info("Raw response from Moodle: " + categories);

        return categories;
    }

    // Helper method to retrieve all the Course IDs available
    public List<Long> fetchAllCourseIds() {
        LOGGER.info("Fetching all course IDs from Moodle server");

        // Fetch all courses
        List<Map<String, Object>> allCourses = fetchAllCourses();

        if (allCourses == null || allCourses.isEmpty()) {
            LOGGER.warning("No courses found in the Moodle server response");
            return List.of(); // If no courses found => Return an empty list
        }

        // Extract course IDs from the response
        List<Long> courseIds = allCourses.stream()
                .map(course -> {
                    Object id = course.get("id");

                    if(id instanceof Number) {
                        return ((Number) id).longValue();
                    } else {
                        LOGGER.warning("Invalid course ID format: " + id);
                        return null;
                    }
                })
                .filter(Objects::nonNull) // Remove null IDs
                .toList();

        LOGGER.info("Fetched " + courseIds.size() + " course IDs from Moodle");
        return courseIds;
    }




}
