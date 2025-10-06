package com.cdl.escrow.report.dto;

import java.time.LocalDate;

public record BuildPartnerReportRequest(
        LocalDate fromDate,   // optional; echoed in header
        LocalDate toDate,     // optional; echoed in header
        String status,        // optional; echoed in header
        Integer page,         // 0-based for Page<>
        Integer size          // default 50
) {}
