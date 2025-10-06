package com.cdl.escrow.report.dto;

import java.time.LocalDate;

public record MonthlyReraRequest(
        String developer,    // dropdown
        String project,      // dropdown; "ALL" allowed
        LocalDate asOnDate,  // calendar
        LocalDate fromDate,  // date range from
        LocalDate toDate,    // date range to
        Integer page,        // 0-based
        Integer size         // default 50
) {}
