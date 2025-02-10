package com.usth_connect.vpn_server_backend_usth.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.usth_connect.vpn_server_backend_usth.Enum.Interest;
import com.usth_connect.vpn_server_backend_usth.Enum.StudyPlace;
import com.usth_connect.vpn_server_backend_usth.Enum.StudyTime;
import com.usth_connect.vpn_server_backend_usth.Enum.Subject;
import com.usth_connect.vpn_server_backend_usth.entity.Student;
import com.usth_connect.vpn_server_backend_usth.entity.studyBuddy.StudyBuddy;
import com.usth_connect.vpn_server_backend_usth.repository.ConnectionRepository;
import com.usth_connect.vpn_server_backend_usth.repository.StudyBuddyRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class StudyBuddyService {
    private final Logger LOGGER = Logger.getLogger(StudyBuddyService.class.getName());
    private final RestTemplate template = new RestTemplate();
    private final String flaskUrl = "http://127.0.0.1:5000";

    @Autowired
    private StudyBuddyRepository studyBuddyRepository;

    @Autowired
    private ConnectionService connectionService;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ConnectionRepository connectionRepository;


    @Transactional
    public StudyBuddy save(StudyBuddy studyBuddy) {
        return studyBuddyRepository.save(studyBuddy);
    }

    public List<StudyBuddy> saveAll(List<StudyBuddy> studyBuddies) {
        return studyBuddyRepository.saveAll(studyBuddies);
    }

    public Optional<StudyBuddy> findById(String studentId) {
        return studyBuddyRepository.findById(studentId);
    }

    public List<String> getRecommendations(String studentId) {

        // Fetch StudyBuddy from database
        Optional<StudyBuddy> optionalStudyBuddy = findById(studentId);

        // Check if a StudyBuddy was found
        if (optionalStudyBuddy.isEmpty()) {
            LOGGER.warning("No study buddy found for student ID: " + studentId);
            return new ArrayList<>();  // Return an empty list if no study buddy was found
        }

        StudyBuddy studyBuddy = optionalStudyBuddy.get();

        // Prepare data for Flask as URL parameter
        String name = studyBuddy.getName();
        String gender = studyBuddy.getGender();
        String major = studyBuddy.getStudent().getMajor();
        String interest = String.join(",", studyBuddy.getInterests());
        String communication = studyBuddy.getCommunicationStyle();
        String lookingFor = studyBuddy.getLookingFor();
        String favSubject = String.join(",", studyBuddy.getFavoriteSubjects());
        String location = String.join(",", studyBuddy.getPreferredPlaces());
        String time = String.join(",", studyBuddy.getPreferredTimes());
        String personality = studyBuddy.getPersonality();

        LOGGER.info("Prepared data for Flask: " + name + ", " + gender + ", " + major);

        // Construct URL with parameters
        // Step 1: Load the sample data first
        String sampleUrl = UriComponentsBuilder
                .fromHttpUrl(flaskUrl)
                .path("/train/sample/{name}/{gender}/{major}/{interest}/{communication}/{looking_for}/{fav_subject}/{location}/{time}/{personality}")
                .buildAndExpand(name, gender, major, interest, communication, lookingFor, favSubject, location, time, personality)
                .toUriString();

        // Send GET request to load sample data
        ResponseEntity<String> loadSampleResponse = template.getForEntity(sampleUrl, String.class);
        LOGGER.info("Sample data load response: " + loadSampleResponse.getBody());

        // Step 2: Proceed to get recommendations if sample data is loaded
        String recommendUrl = UriComponentsBuilder
                .fromHttpUrl(flaskUrl)
                .path("/train/sample/{name}/{gender}/{major}/{interest}/{communication}/{looking_for}/{fav_subject}/{location}/{time}/{personality}/recommend")
                .buildAndExpand(name, gender, major, interest, communication, lookingFor, favSubject, location, time, personality)
                .toUriString();

        // Send GET request for recommendations
        ResponseEntity<String> response = template.getForEntity(recommendUrl, String.class);

        LOGGER.info("Response received from Flask: " + response.getBody());

        // Parse the JSON response into a List of names
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> recommendations;
        try {
            // Deserialize the response into a List of recommendations (which are objects with various fields)
            recommendations = objectMapper.readValue(response.getBody(), new TypeReference<List<Map<String, Object>>>() {});

            LOGGER.info("Recommendations: " + recommendations);

            // Extract the names from the recommendations
            List<String> recommendationNames = new ArrayList<>();
            for (Map<String, Object> recommendation : recommendations) {
//                recommendationNames.add((String) recommendation.get("FullName"));
                String recommendedName = (String) recommendation.get("FullName");

                // Check if the current student is already connected with the recommended study buddy
                if (!connectionService.existsConnectionBetweenStudents(studentId, recommendedName)) {
                    recommendationNames.add(recommendedName); // Add to recommendations if no connection exists
                } else {
                    LOGGER.info("Skipping recommendation for " + recommendedName + " due to existing connection with " + studentId);
                }
            }

            return recommendationNames;
        } catch (Exception e) {
            throw new RuntimeException("Error parsing recommendations response from Flask: " + e.getMessage(), e);
        }
    }

    // Method to get detailed information of recommended study buddies
    public List<StudyBuddy> getRecommendationWithDetails(String studentId) {

        // Get the names of recommended study buddies
        List<String> recommendationNames = getRecommendations(studentId);

        // Fetch StudyBuddy entities from the db based on names
        List<StudyBuddy> recommendedStudyBuddies = new ArrayList<>();
        for (String name : recommendationNames) {
            Optional<StudyBuddy> studyBuddyOptional = studyBuddyRepository.findByName(name);

            // Check if the studyBuddy exists before adding it
            studyBuddyOptional.ifPresent(recommendedStudyBuddies::add);
        }
        return recommendedStudyBuddies;
    }
}
