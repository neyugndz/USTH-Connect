package com.usth_connect.vpn_server_backend_usth.service;

import org.springframework.stereotype.Service;

@Service
public class OrganizerService {
    public int calculateOrganizerId(String studyYear, String major) {
        if ("ICT".equalsIgnoreCase(major)) {
            switch (studyYear) {
                case "B2":
                    return 686; // Test: 691
                case "B3":
                    return 2;
            }
        } else if ("CS".equalsIgnoreCase(major)) {
            switch (studyYear) {
                case "B2":
                    return 18;
                case "B3":
                    return 689;
            }
        } else if ("DS".equalsIgnoreCase(major)) {
            switch (studyYear) {
                case "B2":
                    return 688;
                case "B3":
                    return 690;
            }
        }
        return 999;  // Return a default value if no matching case is found
    }
}
