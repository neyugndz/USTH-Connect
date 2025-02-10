package com.usth_connect.vpn_server_backend_usth.controller;

import com.usth_connect.vpn_server_backend_usth.entity.studyBuddy.Connection;
import com.usth_connect.vpn_server_backend_usth.entity.studyBuddy.Message;
import com.usth_connect.vpn_server_backend_usth.entity.studyBuddy.StudyBuddy;
import com.usth_connect.vpn_server_backend_usth.repository.StudyBuddyRepository;
import com.usth_connect.vpn_server_backend_usth.service.ConnectionService;
import com.usth_connect.vpn_server_backend_usth.service.MessageService;
import com.usth_connect.vpn_server_backend_usth.service.StudyBuddyService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/connections")
public class ConnectionController {
    private final Logger LOGGER = Logger.getLogger(ConnectionController.class.getName());

    @Autowired
    private ConnectionService connectionService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private StudyBuddyRepository studyBuddyRepository;

    // Configure a connection between two StudyBuddies
    @PostMapping("/create")
    public ResponseEntity<Connection> createConnection(
            @RequestParam String studyBuddy1Id,
            @RequestParam String studyBuddy2Id) {

        StudyBuddy studyBuddy1 = studyBuddyRepository.findByStudentId(studyBuddy1Id);
        StudyBuddy studyBuddy2 = studyBuddyRepository.findByStudentId(studyBuddy2Id);

        if (studyBuddy1 == null || studyBuddy2 == null) {
            LOGGER.warning("StudyBuddy not found for provided IDs: "
                    + studyBuddy1Id + " or " + studyBuddy2Id);
            return ResponseEntity.badRequest().build();
        }

        Connection connection = connectionService.createConnection(studyBuddy1, studyBuddy2);

        return ResponseEntity.ok(connection);
    }

    // Update the status of the connection between studyBuddies
    @PatchMapping("/{connectionId}/status")
    public ResponseEntity<Connection> updateConnectionStatus(
            @PathVariable Long connectionId,
            @RequestParam String status) {
        Connection updatedConnection = connectionService.updateConnectionStatus(connectionId, status);
        return ResponseEntity.ok(updatedConnection);
    }

    // Fetch all the connections for a certain study buddy
    @GetMapping("/{studyBuddyId}")
    public ResponseEntity<?> getConnections(@PathVariable String studyBuddyId) {
        // Check if the study Buddy is existed
        StudyBuddy studyBuddy = studyBuddyRepository.findByStudentId(studyBuddyId);
        if(studyBuddy == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("StudyBuddy with ID " + studyBuddyId + " not found.");
        }
        List<Connection> connections = connectionService.getConnetionsForStudyBuddy(studyBuddyId);
        if (connections.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("StudyBuddy with ID " + studyBuddyId + " has no connections.");
        }
        return ResponseEntity.ok(connections);
    }

    // Send a message between two users
    @PostMapping("/{connectionId}/messages/send")
    public ResponseEntity<Message> sendMessage(
            @RequestParam String receiverId,
            @PathVariable Long connectionId,
            @RequestParam String senderId,
            @RequestParam String content) {

        Message message = messageService.sendMessage(connectionId, senderId, receiverId, content);
        return ResponseEntity.ok(message);
    }


    // Fetch all messages for a specific connection
    @GetMapping("/{connectionId}/messages")
    public ResponseEntity<?> getMessage(@PathVariable Long connectionId) {
        List<Message> messages = messageService.getMessages(connectionId);

        if(messages.isEmpty()) {
            LOGGER.info("No messages found for connection ID: " + connectionId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("No messages found for connection ID: " + connectionId);
        }

        return ResponseEntity.ok(messages);
    }
}
