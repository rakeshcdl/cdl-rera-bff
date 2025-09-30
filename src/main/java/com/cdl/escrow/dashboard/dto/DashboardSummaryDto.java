package com.cdl.escrow.dashboard.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DashboardSummaryDto implements Serializable {

    private MainTrustSummaryDto mainTrustSummary;
    private List<AccountBalanceDto> otherAccounts;

    private UnitStatusDto unitStatus;
    private GuaranteeStatusDto guaranteeStatus;
}
