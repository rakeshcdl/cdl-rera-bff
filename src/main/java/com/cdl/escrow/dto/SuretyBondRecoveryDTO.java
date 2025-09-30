package com.cdl.escrow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuretyBondRecoveryDTO implements Serializable {
    private Long id ;

    private Double suretyBondRecoveryReductionAmount;

    private Double suretyBondRecoveryBalanceAmount;

    private SuretyBondDTO suretyBondDTO;

    private Boolean deleted ;

    private boolean enabled ;
}
