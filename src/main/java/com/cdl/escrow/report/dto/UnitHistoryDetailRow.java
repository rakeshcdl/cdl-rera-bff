package com.cdl.escrow.report.dto;

import java.time.LocalDateTime;

public record UnitHistoryDetailRow(
        int serialNo,
        String unitNumber,
        String buildingName,
        LocalDateTime modifiedDate,
        String unitStatus,
        boolean oqoodPaid,
        double oqoodAmount,
        double grossSalesValue,
        double amountPaidOutEscrow,
        double amountPaidWithinEscrow,
        double totalCashReceivedFromOwner,
        double ownerBalance,
        double forfeitedAmount,
        double refundAmount,
        double transferredAmount,
        double dldAmount,
        double plotArea,
        String unitFloor,
        int noOfBedrooms,
        String ownerName1,
        String ownerName2,
        String ownerName3,
        String ownerName4,
        String ownerName5,
        String ownerName6,
        String ownerName7,
        String ownerName8,
        String ownerName9,
        String ownerName10,
        String remarks
) {}