package com.cdl.escrow.dashboard.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GuaranteeStatusDto implements Serializable {

    private Long totalGuaranteesCount;  // optional: count of guarantee items
    private Double totalGuaranteedAmount;
    private List<GuaranteeTypeDto> items;
}
