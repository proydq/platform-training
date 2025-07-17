package com.example.course.controller;

import com.example.common.Idempotent;
import com.example.common.RateLimiter;
import com.example.common.ResponseEntity;
import com.example.course.entity.Course;
import com.example.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseRepository courseRepository;

    @GetMapping
    public ResponseEntity<List<Course>> list() {
        return ResponseEntity.ok(courseRepository.findAll());   // 自动兜底 requestId
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @RateLimiter
    @Idempotent
    public ResponseEntity<Course> create(@RequestBody Course course, HttpServletRequest request) {
        Course saved = courseRepository.save(course);
        return ResponseEntity.ok(saved, request.getHeader("X-Request-ID"));
    }
}
