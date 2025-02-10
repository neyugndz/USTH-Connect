package com.usth_connect.vpn_server_backend_usth.controller;

import com.usth_connect.vpn_server_backend_usth.entity.moodle.Resource;
import com.usth_connect.vpn_server_backend_usth.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/resources")
public class ResourceController {

    @Autowired
    private ActivityService activityService;

    @PostMapping("/save-all")
    public ResponseEntity<List<Resource>> saveAllResources() {
        try {
            List<Resource> savedResources = activityService.saveAllResources();
            return ResponseEntity.ok(savedResources);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
