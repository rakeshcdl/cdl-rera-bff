package com.cdl.escrow.dashboard.controller;


import com.cdl.escrow.dashboard.dto.DashboardSummaryDto;
import com.cdl.escrow.dashboard.dto.FakeDashboardResponseProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
@Slf4j
public class DashboardController {

    private final FakeDashboardResponseProvider fakeProvider;

    @GetMapping
    public ResponseEntity<Page<Object>> getAllDashboard(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all dashboard , page: {}", pageable.getPageNumber());

        return null;
    }

    @GetMapping("/summary")
    public ResponseEntity<DashboardSummaryDto> getSummary(
            @RequestParam(required = false) Long buildPartnerId,
            @RequestParam(required = false) Long realEstateAssestId,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "true") boolean mock
    ) {
        DashboardSummaryDto dto = new DashboardSummaryDto();

        if (mock) {
            // return fake random JSON
            dto = fakeProvider.getRandomSample();
        } else {
            // run real DB queries
           // dto = dashboardService.getSummary(buildPartnerId, realEstateAssestId, startDate, endDate);
        }

        return ResponseEntity.ok(dto);
    }
}
