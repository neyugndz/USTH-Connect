package com.usth_connect.vpn_server_backend_usth.repository;

import com.usth_connect.vpn_server_backend_usth.entity.MapLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MapLocationRepository extends JpaRepository<MapLocation, String> {
    Optional<MapLocation> findByLocation(String location);
}
