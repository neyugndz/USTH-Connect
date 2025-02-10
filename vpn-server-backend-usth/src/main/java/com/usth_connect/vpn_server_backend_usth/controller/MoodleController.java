package com.usth_connect.vpn_server_backend_usth.controller;

import com.usth_connect.vpn_server_backend_usth.service.MoodleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/moodle")
public class MoodleController {
    @Autowired
    private MoodleService moodleService;

    @GetMapping("/site-info")
    public Map<String, Object> getSiteInfo() {
        return moodleService.fetchSiteInfo();
    }

    @GetMapping("/courses/{courseId}")
    public Map<String, Object> getCourses(@PathVariable Long courseId) {
        return moodleService.fetchCourses(courseId);
    }
}
