package com.cdl.escrow.dashboard.dto;

import lombok.Data;

@Data
public class FeesBreakDownDto {
    private Long expenseTypeId;
    private String expenseTypeName;
    private Double amount;
}
