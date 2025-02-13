package com.usth_connect.vpn_server_backend_usth.repository;

import com.usth_connect.vpn_server_backend_usth.entity.schedule.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Integer> {
    boolean existsByGoogleEventId(String googleEventId);
    Optional<Event> findByGoogleEventId(String googleEventId);
}
