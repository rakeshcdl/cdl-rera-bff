package com.cdl.escrow.report.dto;

import java.time.LocalDate;

public record EscrowTxnRequest(
        String developer,      // dropdown (EMS)
        String project,        // dropdown; "ALL" allowed
        LocalDate fromDate,    // range FROM (currently monthly)
        LocalDate toDate,      // range TO
        Integer page,          // 0-based
        Integer size           // default 50
) {}
