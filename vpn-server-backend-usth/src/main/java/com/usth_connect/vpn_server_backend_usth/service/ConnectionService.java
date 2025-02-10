package com.usth_connect.vpn_server_backend_usth.service;

import com.usth_connect.vpn_server_backend_usth.config.ConnectionSpecification;
import com.usth_connect.vpn_server_backend_usth.entity.studyBuddy.Connection;
import com.usth_connect.vpn_server_backend_usth.entity.studyBuddy.StudyBuddy;
import com.usth_connect.vpn_server_backend_usth.repository.ConnectionRepository;
import com.usth_connect.vpn_server_backend_usth.repository.StudyBuddyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class ConnectionService {
    private final Logger LOGGER = Logger.getLogger(ConnectionService.class.getName());

    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private StudyBuddyRepository studyBuddyRepository;

    public boolean existsConnectionBetweenStudents(String studentId1, String studentId2) {
        Specification<Connection> spec = ConnectionSpecification.existsConnectionBetweenStudents(studentId1, studentId2);

        // Use the Specification with count() to check if the connection exists
        long count = connectionRepository.count(spec);

        // If count > 0, then a connection exists
        return count > 0;
    }

    // Set up the connection between users
    public Connection createConnection(StudyBuddy studyBuddy1, StudyBuddy studyBuddy2) {

        // Check if the connection already exists
        Optional<Connection> existingConnection = connectionRepository.findExistingConnection(studyBuddy1.getStudentId(), studyBuddy2.getStudentId());
        if (existingConnection.isPresent()) {
            LOGGER.info("Connection already exists between " + studyBuddy1.getStudentId() + " and " + studyBuddy2.getStudentId());
            return existingConnection.get(); // Return the existing connection
        }


        Connection connection = new Connection();
        connection.setStudyBuddy1(studyBuddy1);
        connection.setStudyBuddy2(studyBuddy2);
        connection.setStatus("PENDING");

        // Save the info of the connection
        Connection savedConnection = connectionRepository.save(connection);
        LOGGER.info("Connection saved with ID: " + savedConnection.getId());

        addConnectionToOpponents(studyBuddy1, studyBuddy2);

        return savedConnection;
    }

    // Helper method to save opponents information when creating connection
    private void addConnectionToOpponents(StudyBuddy studyBuddy1, StudyBuddy studyBuddy2) {
        if (!studyBuddy1.getConnectedBuddies().contains(studyBuddy2)) {
            studyBuddy1.getConnectedBuddies().add(studyBuddy2);
        }
        if (!studyBuddy2.getConnectedBuddies().contains(studyBuddy1)) {
            studyBuddy2.getConnectedBuddies().add(studyBuddy1);
        }

        // Save updates for both study buddies
        studyBuddyRepository.save(studyBuddy1);
        studyBuddyRepository.save(studyBuddy2);

        LOGGER.info("Updated connected buddies for StudyBuddy IDs: " +
                studyBuddy1.getStudentId() + " and " + studyBuddy2.getStudentId());
    }

    // Change the status of the connection
    public Connection updateConnectionStatus(Long connectionId, String status) {
        Optional<Connection> connectionOpt = connectionRepository.findById(connectionId);
        if(connectionOpt.isPresent()) {
            Connection connection = connectionOpt.get();
            connection.setStatus(status);
            return connectionRepository.save(connection);
        }
        throw new IllegalArgumentException("Connection not found with ID: " + connectionId);
    }

    // Fetch the connection between StudyBuddies
    public List<Connection> getConnetionsForStudyBuddy(String studyBuddyId) {
        return connectionRepository.findByStudyBuddy1_StudentIdOrStudyBuddy2_StudentId(studyBuddyId, studyBuddyId);
    }

}
