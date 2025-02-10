package com.usth_connect.vpn_server_backend_usth.controller;


import com.usth_connect.vpn_server_backend_usth.entity.Student;
import com.usth_connect.vpn_server_backend_usth.entity.studyBuddy.StudyBuddy;
import com.usth_connect.vpn_server_backend_usth.repository.StudentRepository;
import com.usth_connect.vpn_server_backend_usth.repository.StudyBuddyRepository;
import com.usth_connect.vpn_server_backend_usth.service.MoodleService;
import com.usth_connect.vpn_server_backend_usth.service.StudyBuddyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/studyBuddy")
public class StudyBuddyController {
    private static final Logger LOGGER = Logger.getLogger(StudyBuddyController.class.getName());

    @Autowired
    private StudyBuddyService studyBuddyService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudyBuddyRepository studyBuddyRepository;

    // Endpoint to save StudyBuddy from the FE
    @PostMapping("/save")
    public ResponseEntity<StudyBuddy> saveStudyBuddy(@RequestBody StudyBuddy studyBuddy) {
        LOGGER.info("Received Body: " + studyBuddy);

        StudyBuddy studyBuddy1 = studyBuddyService.save(studyBuddy);
        LOGGER.info("Saved StudyBuddy: " + studyBuddy1);
        return ResponseEntity.ok(studyBuddy1);
    }

    @PostMapping("/saveAll")
    public ResponseEntity<List<StudyBuddy>> saveStudyBuddies(@RequestBody List<StudyBuddy> studyBuddies) {
        LOGGER.info("Received Body: " + studyBuddies);

        List<StudyBuddy> savedStudyBuddies = studyBuddyService.saveAll(studyBuddies);
        LOGGER.info("Saved StudyBuddies: " + savedStudyBuddies);
        return ResponseEntity.ok(savedStudyBuddies);
    }

    @GetMapping("/{studentId}")
    public Optional<StudyBuddy> getStudyBuddy(@PathVariable String studentId) {
        return studyBuddyRepository.findById(studentId);
    }

    @GetMapping("/{studentId}/recommendations")
    public ResponseEntity<List<String>> getRecommendations(@PathVariable String studentId) {
        List<String> recommendations = studyBuddyService.getRecommendations(studentId);
        return ResponseEntity.ok(recommendations);
    }

    @GetMapping("/{studentId}/recommendations/details")
    public ResponseEntity<List<StudyBuddy>> getRecommendationWithDetails(@PathVariable String studentId) {
        List<StudyBuddy> recommendedStudyBuddies = studyBuddyService.getRecommendationWithDetails(studentId);
        return ResponseEntity.ok(recommendedStudyBuddies);
    }
}
