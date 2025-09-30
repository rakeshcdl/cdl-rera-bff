package com.cdl.escrow.dashboard.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MainTrustSummaryDto implements Serializable {

    private Double availableBalance;
    private Double totalDeposits;
    private Double totalPayments;
    private Double totalFeesCollected;
    private Double depositVsLastPeriodPercent;
    private Double paymentVsLastPeriodPercent;
    private Double feesVsLastPeriodPercent;
    private List<FundBreakdownDto> depositBreakdown;

    private List<ExpenseBreakdownDto> expenseBreakdown;

    private List<FeesBreakDownDto> feesBreakdown;
}
