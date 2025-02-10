package com.usth_connect.vpn_server_backend_usth.config;

import com.usth_connect.vpn_server_backend_usth.entity.studyBuddy.Connection;
import org.springframework.data.jpa.domain.Specification;

public class ConnectionSpecification {
    public static Specification<Connection> existsConnectionBetweenStudents(String studentId1, String studentId2) {
        return (root, query, criteriaBuilder) -> {
            // Accessing studentId from the StudyBuddy entity inside Connection
            return criteriaBuilder.or(
                    // Condition 1: studyBuddy1's studentId == studentId1 and studyBuddy2's studentId == studentId2
                    criteriaBuilder.and(
                            criteriaBuilder.equal(root.get("studyBuddy1").get("studentId"), studentId1),
                            criteriaBuilder.equal(root.get("studyBuddy2").get("studentId"), studentId2)
                    ),
                    // Condition 2: studyBuddy1's studentId == studentId2 and studyBuddy2's studentId == studentId1
                    criteriaBuilder.and(
                            criteriaBuilder.equal(root.get("studyBuddy1").get("studentId"), studentId2),
                            criteriaBuilder.equal(root.get("studyBuddy2").get("studentId"), studentId1)
                    )
            );
        };
    }
}
