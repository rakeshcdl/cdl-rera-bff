package com.cdl.escrow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CapitalPartnerUnitBookingDTO implements Serializable {
    private Long id ;

    private Double cpubAmountPaid;

    private Double cpubAreaSize;

    private Double cpubForFeitAmount;

    private Double cpubDldAmount;

    private Double cpubRefundAmount;

    private String cpubRemarks;

    private Double cpubTransferredAmount;

    private Set<CapitalPartnerUnitDTO> capitalPartnerUnitDTOS ;

    private Boolean deleted ;

    private boolean enabled ;
}
