package com.cdl.escrow.dashboard.dto;

import lombok.Data;

@Data
public class ExpenseBreakdownDto {

    private Long expenseTypeId;
    private String expenseTypeName;
    private Double amount;
}
