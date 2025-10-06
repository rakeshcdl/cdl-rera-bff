package com.cdl.escrow.report.dto;

import java.time.LocalDate;

public record MonthlyTasRequest(
        String developer,           // dropdown
        String project,             // dropdown; "ALL" allowed
        LocalDate asOnDate,         // optional
        LocalDate fromDate,         // range from
        LocalDate toDate,           // range to
        Integer page,               // 0-based
        Integer size                // default 50
) {}
