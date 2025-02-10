package com.usth_connect.vpn_server_backend_usth.repository;

import com.usth_connect.vpn_server_backend_usth.entity.studyBuddy.Connection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ConnectionRepository extends JpaRepository<Connection, Long>, JpaSpecificationExecutor<Connection> {
    List<Connection> findByStudyBuddy1_StudentIdOrStudyBuddy2_StudentId(String studyBuddy1Id, String studyBuddy2Id);

    @Query(value = "SELECT * FROM study_connection c WHERE "
            + "(c.study_buddy_1_id = :studentId1 AND c.study_buddy_2_id = :studentId2) "
            + "OR (c.study_buddy_1_id = :studentId2 AND c.study_buddy_2_id = :studentId1)",
            nativeQuery = true)
    Optional<Connection> findExistingConnection(@Param("studentId1") String studentId1, @Param("studentId2") String studentId2);

//    @Query(value = "SELECT COUNT(*) > 0 FROM connection WHERE (study_buddy_1_id = :studentId1 AND study_buddy_2_id = :studentId2) OR (study_buddy_1_id = :studentId2 AND study_buddy_2_id = :studentId1)", nativeQuery = true)
//    boolean existsConnectionBetweenStudents(@Param("studentId1") String studentId1, @Param("studentId2") String studentId2);

}
