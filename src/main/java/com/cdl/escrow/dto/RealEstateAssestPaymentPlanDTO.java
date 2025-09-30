package com.cdl.escrow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealEstateAssestPaymentPlanDTO implements Serializable {

    private Long id;

    private Integer reappInstallmentNumber;

    private Double reappInstallmentPercentage;

    private Double reappProjectCompletionPercentage;

    private RealEstateAssestDTO realEstateAssestDTO;

    private Boolean deleted ;

    private boolean enabled ;
}
