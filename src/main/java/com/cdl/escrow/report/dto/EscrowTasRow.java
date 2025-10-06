package com.cdl.escrow.report.dto;

public record EscrowTasRow(
        int serialNo,

        // 1–4
        String developerConcat,     // "DEV_NO - DEV_NAME"
        String projectConcat,       // "PRJ_NO - PRJ_NAME"
        String depositRequestType,  // per request
        String ownerConcat,         // "OWNER_NO - OWNER_NAME"

        // 5–7
        String unitNo1,             // Tower
        String unitNo2,             // Unit
        String paymentReferenceNo,

        // 8–9
        String offlinePaymentMethods, // e.g. "Cash/Cheque"
        String transferRefNumber,

        // 10–11
        String projectReraNumber,
        String developerReraNumber,

        // 12–18 (beneficiary info + depositor)
        String beneficiaryType,     // constant: "Bank"
        String beneficiaryName,
        String beneficiaryBank,
        String beneficiaryMobile,   // constant: "No Values"
        String beneficiaryEmail,    // constant: "No Values"
        String beneficiaryRoutingCode, // constant: "No Values"
        String depositorOrPayeeName
) {}