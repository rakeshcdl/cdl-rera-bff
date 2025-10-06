package com.cdl.escrow.report.dto;

import java.time.LocalDate;

public record FinancialDataRow(
        int serialNo,
        String project,
        String unitNo,
        String ownerName,
        double openingBalance,
        double collections,       // till As-on
        double refunds,
        double transfers,
        double paidOutEscrow,
        double paidWithinEscrow,
        double totalCashReceived, // computed or source
        double ownerBalance,      // opening + collections - refunds - transfers - totalCashReceived
        double oqoodAmount,
        double dldAmount,
        LocalDate lastPaymentDate,
        String status,            // OPEN/CANCELLED/TRANSFERRED/…
        String remarks
) {}
