package com.cdl.escrow.report.dto;

import com.cdl.escrow.report.reportenum.ReportStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record BalanceConfirmationRequest (
        @NotBlank String developerId,
        @NotBlank String projectId,
        @NotNull ReportStatus status,
        @NotNull LocalDate asOnDate
){
}
