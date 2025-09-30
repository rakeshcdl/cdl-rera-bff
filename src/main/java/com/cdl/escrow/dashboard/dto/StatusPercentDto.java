package com.cdl.escrow.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class StatusPercentDto implements Serializable {

    private String status;    // e.g. "Sold", "Unsold", "Freeze"
    private Integer percent;  // 0..100
}
