package com.cdl.escrow.report.dto;

import java.time.LocalDate;

public record FinancialDataRequest(
        String project,       // dropdown
        LocalDate asOnDate,   // calendar
        Integer page,         // 0-based
        Integer size          // default 50
) {}
