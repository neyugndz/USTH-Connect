package com.usth_connect.vpn_server_backend_usth.repository;

import com.usth_connect.vpn_server_backend_usth.entity.studyBuddy.StudyBuddy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudyBuddyRepository extends JpaRepository<StudyBuddy, String> {
    Optional<StudyBuddy> findByName(String name);

    StudyBuddy findByStudentId(String studentId);

    @Query("SELECT DISTINCT sb FROM StudyBuddy sb " +
            "LEFT JOIN FETCH sb.favoriteSubjects sbfs " +
            "LEFT JOIN FETCH sb.interests sbi " +
            "LEFT JOIN FETCH sb.preferredPlaces sbpp " +
            "LEFT JOIN FETCH sb.preferredTimes sbpt " +
            "WHERE sb.studentId = :studentId")
    Optional<StudyBuddy> findDistinctByStudentId(@Param("studentId") String studentId);
}
