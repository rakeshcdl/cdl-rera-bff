package com.cdl.escrow.report.dto;

import java.time.LocalDate;

public record TasBatchStatusRow(
        int serialNo,
        LocalDate createDate,
        String batchNo,
        String paymentType,
        String subType,
        String buildingNumber,
        LocalDate dateOfPayment,
        double paymentAmount,
        String paymentMode,
        String payeeName,
        String developerNumber,
        String projectNumber,
        String tasReferenceNo,
        String propertyTypeId,
        String unitNumber,
        String service,
        String errorDescription,
        String finacleRefNo,
        String mortgageNumber,
        String maker,
        String checker
) {}
