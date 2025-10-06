package com.cdl.escrow.report.dto;


public record TrustReportRequest(
        String developerName,
        String projectName,
        String unitNo,
        String unitHolderName,
        Integer page,
        Integer size
) {}
