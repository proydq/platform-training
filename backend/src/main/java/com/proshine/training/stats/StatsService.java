package com.proshine.training.stats;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StatsService {

    public StatsOverviewDTO getOverview() {
        // TODO: replace with SQL aggregation
        log.debug("Returning demo stats overview");
        return StatsOverviewDTO.builder()
                .activeUsers(135)
                .studyHours(420)
                .totalCourses(57)
                .publishedExams(8)
                .build();
    }
}
