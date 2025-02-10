package com.usth_connect.vpn_server_backend_usth.repository;

import com.usth_connect.vpn_server_backend_usth.entity.MapLocation;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MapRepository extends JpaRepository<MapLocation, Integer> {
}
