package com.cdl.escrow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SplitDataDTO implements Serializable {

    private Long id;

    private Double amount;

    private Double retentionAmount;

    private ApplicationSettingDTO bucketType;

    private ApplicationSettingDTO bucketSubType;

    private ApplicationSettingDTO depositMode;

    private String checkNumber;

    private String tasRefNumber;

    private CapitalPartnerUnitDTO unit;

    private Boolean isDeleted;


}
