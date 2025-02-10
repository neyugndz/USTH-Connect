package com.usth_connect.vpn_server_backend_usth.repository;

import com.usth_connect.vpn_server_backend_usth.entity.studyBuddy.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByConnectionIdOrderByCreatedAtAsc(Long connectionId);
}
