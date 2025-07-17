package com.example.course.controller;

import com.example.common.ResponseEntity;
import com.example.course.dto.CourseDTO;
import com.example.course.service.CourseService;
import com.example.course.service.EnrollmentService;
import com.example.course.vo.CourseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
@Slf4j
public class CourseController {
    private final CourseService service;
    private final EnrollmentService enrollService;

    @GetMapping
    public ResponseEntity<Page<CourseVO>> list(
            @RequestParam(defaultValue="0") int page,
            @RequestParam(defaultValue="10") int size,
            @RequestParam(defaultValue="all") String status,
            Authentication auth){
        Pageable pb = PageRequest.of(page,size, Sort.by("createdAt").descending());
        String uid = ((UserDetails)auth.getPrincipal()).getUsername();
        return ResponseEntity.ok(service.list(pb,status,uid));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CourseVO> create(@Valid @RequestBody CourseDTO dto){
        return ResponseEntity.ok(service.create(dto));
    }

    @PostMapping("/{courseId}/enroll")
    @PreAuthorize("hasRole('ROLE_STUDENT') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> enroll(@PathVariable String courseId, Authentication auth){
        enrollService.enroll(auth.getName(),courseId);
        return ResponseEntity.ok(null);
    }

    @PutMapping("/{courseId}/favorite")
    @PreAuthorize("hasRole('ROLE_STUDENT') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> favorite(@PathVariable String courseId,
                                      @RequestBody Map<String,Boolean> body,
                                      Authentication auth){
        enrollService.toggleFavorite(auth.getName(),courseId, body.getOrDefault("favorite",true));
        return ResponseEntity.ok(null);
    }
}
