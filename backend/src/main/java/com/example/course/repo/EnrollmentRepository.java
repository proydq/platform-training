package com.example.course.repo;

import com.example.course.entity.Enrollment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment,String> {
    Optional<Enrollment> findByUserIdAndCourseId(String userId,String courseId);
    Page<Enrollment> findByUserId(String userId, Pageable pageable);
}
