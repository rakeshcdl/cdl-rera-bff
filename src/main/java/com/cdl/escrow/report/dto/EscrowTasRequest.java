package com.cdl.escrow.report.dto;

import java.time.LocalDate;

public record EscrowTasRequest(
        String developer,          // concatenated "DEV_NO - DEV_NAME"
        String project,            // concatenated "PRJ_NO - PRJ_NAME"
        String depositRequestType, // e.g. "Deposit", "Transfer", "Reversal"
        String owner,              // concatenated "OWNER_NO - OWNER_NAME"
        LocalDate fromDate,
        LocalDate toDate,
        LocalDate checkerApprovalDate, // optional, echoed in header
        Integer page,              // 0-based
        Integer size               // default 50
) {}
