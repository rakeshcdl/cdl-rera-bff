package com.cdl.escrow.report.dto;

import java.time.LocalDate;

public record TasBatchStatusRequest(
        LocalDate fromDate,
        LocalDate toDate,
        Integer page,
        Integer size
) {}
