package com.example.course.service;

import com.example.course.dto.CourseDTO;
import com.example.course.entity.Course;
import com.example.course.entity.Enrollment;
import com.example.course.repo.CourseRepository;
import com.example.course.repo.EnrollmentRepository;
import com.example.course.vo.CourseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseService {
    private final CourseRepository courseRepo;
    private final EnrollmentRepository enrollRepo;

    @Transactional
    public CourseVO create(CourseDTO dto){
        Course c = Course.builder()
                .title(dto.getTitle()).description(dto.getDescription())
                .cover(dto.getCover()).durationMinutes(dto.getDurationMinutes())
                .status(Course.Status.PUBLISHED)
                .createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now())
                .build();
        courseRepo.save(c);
        return toVO(c,null);
    }

    @Transactional(readOnly=true)
    public Page<CourseVO> list(Pageable pageable,String status,String userId){
        Specification<Course> spec = (root,q,cb)->{
            if(StringUtils.hasText(status) && !"all".equals(status)){
                Join<Course,Enrollment> join = root.join("enrollments", JoinType.LEFT);
                return cb.equal(join.get("status"), Enrollment.Status.valueOf(status.toUpperCase()));
            }
            return null;
        };
        Page<Course> page = courseRepo.findAll(spec,pageable);
        return page.map(c->toVO(c,userId));
    }

    private CourseVO toVO(Course c,String userId){
        Enrollment e = null;
        if(userId!=null){
            e = enrollRepo.findByUserIdAndCourseId(userId,c.getId()).orElse(null);
        }
        return CourseVO.builder()
                .id(c.getId()).title(c.getTitle()).cover(c.getCover())
                .description(c.getDescription()).durationMinutes(c.getDurationMinutes())
                .myStatus(e!=null?e.getStatus():null)
                .progressPercent(e!=null?e.getProgressPercent():null)
                .favorite(e!=null?Boolean.TRUE.equals(e.getFavorite()):null)
                .build();
    }
}
