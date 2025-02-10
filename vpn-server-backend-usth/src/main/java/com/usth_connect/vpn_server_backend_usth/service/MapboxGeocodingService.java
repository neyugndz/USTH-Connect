package com.usth_connect.vpn_server_backend_usth.service;

import com.usth_connect.vpn_server_backend_usth.dto.CoordinatesDTO;
import com.usth_connect.vpn_server_backend_usth.entity.MapLocation;
import com.usth_connect.vpn_server_backend_usth.repository.MapLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class MapboxGeocodingService {
    private static final Logger LOGGER = Logger.getLogger(MapboxGeocodingService.class.getName());
//    private static final String NOMINATIM_URL = "https://nominatim.openstreetmap.org/search";
    @Value("${mapbox.access-token}")
    private String accessToken;

    private static final String MAPBOX_URL = "https://api.mapbox.com/geocoding/v5/mapbox.places";


    @Autowired
    private MapLocationRepository mapLocationRepository;

    // Call OpenStreetMap's Nominatim API to get latitude and longitude from an address
    public String getCoordinatesFromAddress(String address) {
        try {
            // Encode address to handle spaces and special characters
            String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8.toString());
            LOGGER.info("Encoded address: " + encodedAddress);

            URI uri = UriComponentsBuilder.fromHttpUrl(MAPBOX_URL + "/" + encodedAddress + ".json")
                    .queryParam("access_token", accessToken) // Use the injected token
                    .queryParam("limit", 1) // Limit results to the most relevant
                    .build()
                    .toUri();

            LOGGER.info("Request URL: " + uri);


            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(uri, String.class);

            LOGGER.info("API Response: " + response);

            // Parse the JSON response
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonResponse = mapper.readTree(response);

            if (jsonResponse.has("features") && jsonResponse.get("features").size() > 0) {
                JsonNode firstFeature = jsonResponse.get("features").get(0);
                String lat = firstFeature.get("geometry").get("coordinates").get(1).asText();
                String lon = firstFeature.get("geometry").get("coordinates").get(0).asText();
                return "Latitude: " + lat + ", Longitude: " + lon;
            } else {
                return "No results found for the given address.";
            }
        } catch (Exception e) {
            return "An error occurred while fetching coordinates: " + e.getMessage();
        }
    }

    // Fetch latitude and longitude from the database using partial matching
    public CoordinatesDTO getCoordinatesFromDatabase(String location) {
        try {
            location = location.trim();
            LOGGER.info("Querying for location: " + location);

            List<MapLocation> mapLocations = mapLocationRepository.findByLocationContaining(location);

            if (mapLocations.isEmpty()) {
                return null; // No matching location found
            } else if (mapLocations.size() == 1) {
                MapLocation mapLocation = mapLocations.get(0);
                return new CoordinatesDTO(mapLocation.getLatitude(), mapLocation.getLongitude());
            } else {
                // If multiple locations are found, you may return the first one or handle it differently
                MapLocation mapLocation = mapLocations.get(0); // Example: return the first match
                return new CoordinatesDTO(mapLocation.getLatitude(), mapLocation.getLongitude());
            }
        } catch (Exception e) {
            LOGGER.severe("Error while fetching location: " + e.getMessage());
            return null;
        }
    }

    // Method to save address to the database
    public void saveLocation(String location, Double latitude, Double longtitude) {
        MapLocation mapLocation = new MapLocation();
        mapLocation.setLocation(location);
        mapLocation.setLatitude(latitude);
        mapLocation.setLongitude(longtitude);

        // Save to db
        mapLocationRepository.save(mapLocation);
    }
}
