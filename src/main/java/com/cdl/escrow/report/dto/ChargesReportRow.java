package com.cdl.escrow.report.dto;

import java.time.LocalDate;

public record ChargesReportRow(
        int serialNo,
        LocalDate transactionDate,
        String developerName,
        String projectName,
        String chargeType,
        String frequency,         // Monthly / Real Time / End of day
        String perUnit,           // Per Visit / Per Certificate / Per Request / ""
        String transactionStatus, // Success / Under Process / Rejected
        String rejectReason       // exact reason if Rejected, else ""
) {}
