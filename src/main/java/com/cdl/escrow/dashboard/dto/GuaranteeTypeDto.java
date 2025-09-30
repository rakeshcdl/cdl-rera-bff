package com.cdl.escrow.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class GuaranteeTypeDto implements Serializable {

    private String guaranteeType; // "Advanced Guarantee", "Retention Guarantee", "Performance Guarantee"
    private Double amount;        // amount in INR (or minor unit if you prefer)
}
