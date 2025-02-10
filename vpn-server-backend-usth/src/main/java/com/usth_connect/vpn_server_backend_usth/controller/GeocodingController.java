package com.usth_connect.vpn_server_backend_usth.controller;

import com.usth_connect.vpn_server_backend_usth.dto.CoordinatesDTO;
import com.usth_connect.vpn_server_backend_usth.service.MapboxGeocodingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/location")
public class GeocodingController {

    @Autowired
    private MapboxGeocodingService mapboxGeocodingService;

    // Endpoint to get coordinates for the given address
    @GetMapping("/get-coordinates")
    public ResponseEntity<String> getCoordinates(@RequestParam String address) {
            String location = mapboxGeocodingService.getCoordinatesFromAddress(address);
            return ResponseEntity.ok(location);
    }

    // Endpoint to save the location along with latitude and longitude
    @PostMapping("/save-location")
    public ResponseEntity<String> saveLocation(@RequestParam String address) {
        try {
            // Get coordinates for the address
            String locationResult = mapboxGeocodingService.getCoordinatesFromAddress(address);

            if (locationResult.contains("Latitude")) {
                // Extract latitude and longitude from the result
                String[] coordinates = locationResult.split(",");
                String latitudeStr = coordinates[0].split(":")[1].trim();
                String longitudeStr = coordinates[1].split(":")[1].trim();

                Double latitude = Double.parseDouble(latitudeStr);
                Double longitude = Double.parseDouble(longitudeStr);

                mapboxGeocodingService.saveLocation(address, latitude, longitude);

                return ResponseEntity.ok("Location saved successfully!");
            } else {
                return ResponseEntity.status(400).body("Failed to get coordinates for the provided address.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while saving the location: " + e.getMessage());
        }
    }

    // Endpoint to fetch coordinates for given location from the database
    @GetMapping("/get-coordinates-from-db")
    public ResponseEntity<CoordinatesDTO> getCoordinatesFromDb(@RequestParam String location) {
        // Call the service to fetch coordinates from the db
        CoordinatesDTO coordinatesDTO = mapboxGeocodingService.getCoordinatesFromDatabase(location);
        if(coordinatesDTO == null ) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok(coordinatesDTO);
    }

}
