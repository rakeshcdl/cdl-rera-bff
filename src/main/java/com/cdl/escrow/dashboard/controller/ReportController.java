package com.cdl.escrow.dashboard.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
@Slf4j
public class ReportController {

    @GetMapping
    public ResponseEntity<Page<Object>> getAllReports(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all reports , page: {}", pageable.getPageNumber());

        return null;
    }
}
