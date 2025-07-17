package com.example.course.vo;

import com.example.course.entity.Enrollment;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseVO {
    private String id;
    private String title;
    private String cover;
    private String description;
    private Integer durationMinutes;
    private Enrollment.Status myStatus;
    private Integer progressPercent;
    private Boolean favorite;
}
