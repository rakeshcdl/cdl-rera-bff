package com.cdl.escrow.dashboard.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class FundBreakdownDto implements Serializable {

    private Long bucketTypeId;
    private String bucketTypeName;
    private Double amount;
}
