package com.proshine.training.stats;

import com.proshine.training.common.ResponseEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/stats")
@RequiredArgsConstructor
@Slf4j
public class StatsController {
    private final StatsService statsService;

    @GetMapping("/overview")
    public ResponseEntity<StatsOverviewDTO> getOverview() {
        log.debug("Fetching stats overview");
        StatsOverviewDTO dto = statsService.getOverview();
        if (dto != null) {
            return new ResponseEntity<>(200, "success", dto);
        }
        return new ResponseEntity<>(500, "fail", null);
    }
}
