package com.cdl.escrow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuretyBondDTO implements Serializable {
    private Long id ;

    private String suretyBondReferenceNumber;

    private ZonedDateTime suretyBondDate;

    private String suretyBondName;

    private Boolean suretyBondOpenEnded;

    private ZonedDateTime suretyBondExpirationDate;

    private Double suretyBondAmount;

    private ZonedDateTime suretyBondProjectCompletionDate;

    private String suretyBondNoOfAmendment;

    private String suretyBondContractor;

    private Boolean deleted ;

    private Boolean enabled ;

    private String suretyBondNewReadingAmendment;

    private ApplicationSettingDTO suretyBondTypeDTO;

    private RealEstateAssestDTO realEstateAssestDTO;

    private FinancialInstitutionDTO issuerBankDTO;

    private ApplicationSettingDTO suretyBondStatusDTO;

    //private Set<SuretyBondRecoveryDTO> suretyBondRecoveryDTOS;

   // private Set<SuretyBondReleaseDTO> suretyBondReleaseDTOS;

    private TaskStatusDTO taskStatusDTO;
}
