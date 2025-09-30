package com.cdl.escrow.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CapitalPartnerPaymentPlanDTO implements Serializable {

    private Long id;

    private Integer cpppInstallmentNumber;

    private ZonedDateTime cpppInstallmentDate;

    private Double cpppBookingAmount;

    private CapitalPartnerDTO capitalPartnerDTO;

    private Boolean deleted ;

    private boolean enabled ;
}
