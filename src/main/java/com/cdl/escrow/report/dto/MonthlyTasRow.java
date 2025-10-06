package com.cdl.escrow.report.dto;

import java.time.LocalDate;

public record MonthlyTasRow(
        int serialNo,
        LocalDate txnDate,
        String batchNo,
        String tasReferenceNo,
        String developer,
        String project,
        String unitNo,
        String ownerName,
        String paymentType,     // Deposit / Transfer / etc.
        String paymentMode,     // Cash / Transfer / Cheque
        double amount,
        String bankName,
        String finacleRefNo,
        String serviceStatus,   // SUCCESS/FAILED…
        String errorDescription // empty if success
) {}
