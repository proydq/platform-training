package com.proshine.training.stats;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatsOverviewDTO {
    private Integer activeUsers;
    private Integer studyHours;
    private Integer totalCourses;
    private Integer publishedExams;
}
