package com.example.course.service;

import com.example.common.BusinessException;
import com.example.common.ErrorCode;
import com.example.course.entity.Enrollment;
import com.example.course.repo.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EnrollmentService {
    private final EnrollmentRepository repo;

    @Transactional
    public void enroll(String userId,String courseId){
        Enrollment e = repo.findByUserIdAndCourseId(userId,courseId)
                .orElse(Enrollment.builder()
                    .userId(userId).courseId(courseId)
                    .status(Enrollment.Status.NOT_STARTED)
                    .progressPercent(0).favorite(false).build());
        repo.save(e);
    }

    @Transactional
    public void toggleFavorite(String userId,String courseId,boolean favorite){
        Enrollment e = repo.findByUserIdAndCourseId(userId,courseId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));
        e.setFavorite(favorite);
        repo.save(e);
    }
}
