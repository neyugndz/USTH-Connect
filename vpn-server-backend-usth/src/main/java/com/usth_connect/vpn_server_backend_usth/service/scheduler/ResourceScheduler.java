package com.usth_connect.vpn_server_backend_usth.service.scheduler;

import com.usth_connect.vpn_server_backend_usth.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class ResourceScheduler {
    private static final Logger LOGGER = Logger.getLogger(ResourceScheduler.class.getName());

    @Autowired
    private ActivityService activityService;

    @Scheduled(fixedRate =  12 * 60 * 60 * 1000)
    public void scheduleSaveAllResources() {
        LOGGER.info("Starting scheduled task: Save all resources from Moodle");
        activityService.saveAllResources();
        LOGGER.info("Scheduled task completed: Save all resources from Moodle");
    }

}
