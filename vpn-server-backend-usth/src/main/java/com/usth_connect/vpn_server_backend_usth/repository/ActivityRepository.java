package com.usth_connect.vpn_server_backend_usth.repository;

import com.usth_connect.vpn_server_backend_usth.entity.moodle.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    Optional<Activity> findByActivityId(Long activityId);

    List<Activity> findByCourseId(Long courseId);

    Optional<Activity> findByActivityIdAndCourseId(Long activityId, Long courseId);
}
