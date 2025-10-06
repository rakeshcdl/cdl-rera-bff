package com.cdl.escrow.report.dto;

public record UnitHistoryListRow(
        int serialNo,
        String unitNo,           // Oqood number
        String owner1,
        String owner2,           // optional
        String unitHistoryFlag,  // "Y" or "N"
        String status            // OPEN / CANCELLED / DELETED, etc.
) {}