package com.example.course.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {
    @NotBlank
    private String title;
    private String description;
    private String cover;
    private Integer durationMinutes;
}
