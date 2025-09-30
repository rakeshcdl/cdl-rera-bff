package com.cdl.escrow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RealEstateAssestClosureDTO implements Serializable {
    private Long id;

    private Double reacTotalIncomeFund;

    private Double reacTotalPayment;

    private Double reacCheckGuranteeDoc;

    private Boolean deleted ;

    private boolean enabled ;
    private RealEstateAssestDTO realEstateAssestDTO;

    private ApplicationSettingDTO reacDocumentDTO;
}
