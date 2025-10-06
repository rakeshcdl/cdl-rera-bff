package com.cdl.escrow.report.dto;

import java.time.LocalDate;

public record EscrowTxnRow(
        int serialNo,

        // Developer / Project / IDs
        String developerName,
        String projectName,
        String projectReraId,

        // Core banking / statement data
        String transactionReference,
        String transactionDesc,
        double transactionAmount,
        LocalDate transactionDate,
        String narration,
        String entity,              // ENBD / EI
        String schemeCode,          // first 3 chars of Account Number
        String segment,             // “Escrow” or EMS stored value

        // Customer / CIF / accounts
        String cifNumber,           // developer CIF (core banking)
        String accountNumber,       // main escrow account (EMS)
        String accountName,         // from Account Enquiry API
        String accountCurrencyCode, // currency code (debit/statement currency)

        // RM info (EMS)
        String rmName,
        String secondRmName,
        String teamLeader,

        // Statement classification
        String partTranType,        // Debit / Credit
        double refAmount,           // amount * FX(as of date) to Ref Currency
        String refCurrencyCode,     // from “preferred rates” API

        // Posting / user ids / branch
        String postedStatus,        // payments only; blank otherwise
        String postedUserId,        // maker initials
        String verifiedUserId,      // checker initials
        String accountSolId,        // from Account Enquiry
        String dthInitSolId,        // posted branch code from statement api

        // Monetary splits
        String tranCurrencyCode,    // statement currency
        Double debitAmount,
        Double creditAmount,

        // More classification
        String tranType,
        String tranSubType
) {}
