package com.cdl.escrow.report.dto;

import java.time.LocalDate;

public record UnitHistoryRequest(
        String project,      // dropdown text (echoed in header)
        LocalDate fromDate,  // optional (for header only)
        LocalDate toDate,    // optional (for header only)
        Integer page,        // 0-based
        Integer size         // default 50
) {}
