package com.cdl.escrow.dashboard.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UnitStatusDto implements Serializable {

    private Long totalCount;               // 20,678
    private List<StatusPercentDto> items;  // sold/unsold/freeze/resold/cancelled
}
