package com.cdl.escrow.report.dto;

import java.time.LocalDate;

public record ChargesReportRequest(
        String projectId,   // "ALL" allowed
        LocalDate fromDate,
        LocalDate toDate,
        Integer page,       // 1-based; null => 1
        Integer size        // null => 50
) {}
