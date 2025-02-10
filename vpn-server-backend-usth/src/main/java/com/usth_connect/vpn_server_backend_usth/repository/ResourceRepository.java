package com.usth_connect.vpn_server_backend_usth.repository;

import com.usth_connect.vpn_server_backend_usth.entity.moodle.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    List<Resource> findByActivity_ActivityId(Long activityId);

    List<Resource> findByActivity_ActivityIdAndActivity_CourseId(Long activityId, Long courseId);
}
