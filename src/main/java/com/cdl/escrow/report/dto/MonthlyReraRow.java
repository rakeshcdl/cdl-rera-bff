package com.cdl.escrow.report.dto;

import java.time.LocalDate;

public record MonthlyReraRow(
        int serialNo,
        LocalDate date,
        String developer,
        String project,
        String unitNo,
        String ownerName,
        String activity,    // Booking / SPA / Oqood / Transfer / Cancel / Collection
        String paymentMode, // Cash / Transfer / Cheque / -
        double amount,
        String bankName,
        String tasRef,
        String status       // SUCCESS / FAILED / OPEN / CANCELLED etc.
) {}
