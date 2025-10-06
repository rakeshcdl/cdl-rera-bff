package com.cdl.escrow.report.dto;

import java.time.LocalDateTime;

public record TrustReportRow(
        int serialNo,
        String buildingName,
        String unitNumber,
        String owner1,
        String owner2,
        String owner3,
        String owner4,
        String owner5,
        String owner6,
        String owner7,
        String owner8,
        String owner9,
        String owner10,
        double grossSalesValue,
        double cashCollectionOutEscrow,
        double cashCollectionWithinEscrow,
        double totalCashReceivedInEscrow,
        double totalCashFromUnitHolder,
        double ownerBalance,
        String unitStatus,
        boolean oqoodPaid,
        boolean spa,
        boolean reservationForm,
        double oqoodAmount,
        double dldAmount,
        String balanceLetterSent,
        LocalDateTime balanceLetterIssued,
        String remarks
) {}
