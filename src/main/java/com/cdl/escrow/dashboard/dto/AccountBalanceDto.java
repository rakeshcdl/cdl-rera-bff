package com.cdl.escrow.dashboard.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccountBalanceDto implements Serializable {

    private String accountType; // TRUST, RETENTION, WAKALA, CORPORATE
    private Double balance;
    private Double changeVsLastPeriodPercent;
}
